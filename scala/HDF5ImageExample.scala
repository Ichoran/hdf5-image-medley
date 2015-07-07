package hdf5medley

import java.io._
import ncsa.hdf.object.FileFormat

case class Image(meta: String, data: Array[Byte], width: Int)

object HDF5ImageExample {
  val source = new File("../worm-spine-blurred.tiff")
  
  // Read a TIFF image into an array of bytes
  def getTiff(f: File): Option[Image] =
    Try{
      val im = javax.imageio.ImageIO.read(f)
      val data = im.getData.getDataElements(0, 0, im.getWidth, im.getHeight, null)
      Image(f.getName, data.asInstanceOf[Array[Byte]], im.getWidth)
    }.toOption
    
  def putTiff(im: Image): Option[Throwable] = ???
  
  def main(args: Array[String]) {
  }
}

