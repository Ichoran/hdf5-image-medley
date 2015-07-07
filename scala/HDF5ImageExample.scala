package hdf5medley

import java.io._
import java.awt.{image => jai}
import javax.swing.tree.DefaultMutableTreeNode
import ncsa.hdf.`object`.{Dataset, Datatype, FileFormat, Group, ScalarDS}
import ncsa.hdf.`object`.h5.H5File

import scala.util._

case class Image(meta: String, data: Array[Byte], width: Int)

object HDF5ImageExample {
  val source = (new File("../worm-spine-blurred.tiff")).getCanonicalFile
  val hdr5name = "example.h5"
  
  // Read a TIFF image into an array of bytes (somewhat efficiently--could probably be better with a custom reader)
  def getTiff(f: File): Try[Image] = Try {
    println(f.getPath)
    val im = javax.imageio.ImageIO.read(f)
    val data = im.getData.getDataElements(0, 0, im.getWidth, im.getHeight, null)
    Image(f.getName, data.asInstanceOf[Array[Byte]], im.getWidth)
  }
    
  // Write an array of bytes into a new TIFF image (somewhat efficiently--could be a lot better with a custom writer)
  def putTiff(im: Image): Try[Unit] = Try {
    val f = new File((new File(".")).getCanonicalFile, im.meta)
    val bi = new jai.BufferedImage(im.width, (im.data.length + im.width - 1)/im.width, jai.BufferedImage.TYPE_BYTE_GRAY)
    val buf = bi.getRaster.getDataBuffer.asInstanceOf[jai.DataBufferByte]
    System.arraycopy(im.data, 0, buf.getData, 0, im.data.length)
    javax.imageio.ImageIO.write(bi, "tiff", f)
    ()
  }
  
  // Read a HDF5 file into an array of bytes
  def getHDF5(name: String): Try[Image] = Try {
    val f = new File((new File(".")).getCanonicalFile, hdr5name)
    val hf = new H5File(f.getPath, FileFormat.READ)
    hf.open
    val group = hf.getRootNode.asInstanceOf[DefaultMutableTreeNode].getUserObject.asInstanceOf[Group]
    var meta: String = null
    var data: Array[Byte] = null
    var width: Int = 0
    val ji = group.getMemberList.iterator
    while (ji.hasNext) {
      ji.next match {
        case _: Group => throw new Exception("Unexpected group")
        case ds: Dataset =>
          ds.init   // Reads data
          val dims = ds.getDims
          ds.getData match {
            case as: Array[String] =>
              meta = as(0)
            case bs: Array[Byte] =>
              if (dims.length != 2) throw new Exception("Mis-sized image?")
              data = bs
              width = dims(1).toInt
              val height = dims(0).toInt
              data = if (bs.length != width*height) java.util.Arrays.copyOf(bs, width*height) else bs
          }
      }
    }
    if (meta == null) throw new Exception("No metadata found")
    if (data == null) throw new Exception("No data found")
    Image(meta, data, width)
  }
  
  // Write an array of bytes into a new HDF5 data file
  def putHDF5(im: Image): Try[Unit] = Try {
    val f = new File((new File(".")).getCanonicalFile, hdr5name)
    val dims = Array[Long]((im.data.length + im.width - 1)/im.width, im.width)
    val hf = new H5File(f.getPath, FileFormat.CREATE)
    hf.open
    val group = hf.getRootNode.asInstanceOf[DefaultMutableTreeNode].getUserObject.asInstanceOf[Group]
    val itype = hf.createDatatype(Datatype.CLASS_INTEGER, 1, Datatype.NATIVE, Datatype.SIGN_NONE)
    hf.createImage(
      f.getName, group, itype, dims,
      null, null, 0, 1,
      ScalarDS.INTERLACE_PIXEL, im.data
    )
    val mtype = hf.createDatatype(Datatype.CLASS_STRING, im.meta.getBytes.length + 1, Datatype.NATIVE, Datatype.NATIVE)
    hf.createScalarDS(
      "metadata", group, mtype, Array[Long](1),
      null, null, 0,
      Array(im.meta)
    )
    hf.close
    ()
  }
  
  def main(args: Array[String]) {
    val t0 = System.nanoTime
    val test = 
      for {
        im <- getTiff(source)
        _ <- putHDF5(im)
        im2 <- getHDF5(hdr5name)
        _ <- putTiff(im2)
      } yield System.nanoTime 
    
    test match {
      case Failure(err) =>
        println("Something went wrong!  Stack trace:")
        err.printStackTrace
      case Success(t1) =>
        println(f"All went fine.  Elapsed time ${(t1-t0)*1e-9}%.2f seconds.")
    }
  }
}

