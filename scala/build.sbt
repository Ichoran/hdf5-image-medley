name := "HDF5ImageExample"

version := "0.1"

scalaVersion := "2.11.7"

// HDF5 support (Java part)
libraryDependencies += "org.hdfgroup" % "hdf-java" % "2.6.1"

// Libraries for proper TIFF support in Java
libraryDependencies += "com.github.jai-imageio" % "jai-imageio-core" % "1.3.0"

mainClass in run := Some("hdf5medley.HDF5ImageExample")
