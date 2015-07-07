name := "HDF5ImageExample"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies += "org.hdfgroup" % "hdf-java" % "2.6.1"

mainClass in run := Some("hdf5medley.HDF5ImageExample")
