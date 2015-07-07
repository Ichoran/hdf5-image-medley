package hdf5medley

import java.io._
import java.awt.{image => jai}
import javax.swing.tree.DefaultMutableTreeNode
import ncsa.hdf.`object`.{Datatype, FileFormat, Group, ScalarDS}
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
  
  // Write an array of bytes into a new HDR5 data file
  def putHDR5(im: Image): Try[Unit] = Try {
    val f = new File((new File(".")).getCanonicalFile, hdr5name)
    val dims = Array[Long](im.width, (im.data.length + im.width - 1)/im.width, 1)
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
    val i = getTiff(source) match {
      case Success(x) => x
      case Failure(t) => t.printStackTrace; sys.exit(1)
    }
    putHDR5(i) match {
      case Failure(t) => t.printStackTrace; sys.exit(1)
      case _ => 
    }
  }
}

