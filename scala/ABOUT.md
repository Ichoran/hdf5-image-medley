# hdf5-image-medley/scala

This sub-project shows how to read and write HDF5 files in Scala.

Primary Author: Rex Kerr

## What you will need

1. Java SE.  You can get it from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html).
OpenJDK versions should be fine also.  Java 6 should be okay, but thus far the project has only been tested with Java 8.

2. Scala Build Tool (sbt).  You can get it from [scala-sbt.org](http://www.scala-sbt.org/download.html).  Tested with 0.13.7.

3. The C version of HDF5 installed properly.  You can get it from [the HDF Group](https://www.hdfgroup.org/HDF5/release/obtain5.html)
and then compile it.  You may be able to download it with a package manager.
(Ubuntu has it as `libhdf5-dev`; Mac has it on homebrew as `homebrew/science/hdf5`.)

4. The [Java wrapper for HDF5](https://www.hdfgroup.org/products/java/release/download.html).
(On Ubuntu, you can get it by installing the `hdfview` package.)

Note that if Java doesn't know where to look for the C libraries, it won't work:
the HDF Group does not and does not plan to support a pure Java
implementation of HDF5.

You do _not_ need Scala downloaded separately because SBT will download it for you.  If you want a proper independent installation of Scala,
you can get it from [scala-lang.org](http://scala-lang.org/download/).

## Where is the code?

The Scala source code specific for this project is in `HDF5ImageExample.scala`.  (It could also be placed
in `src/main/scala` if this were a more complicated project with multiple source files.)

The `build.sbt` file specifies dependencies.  Programs that run on the JVM typically use Maven
to store compiled versions of projects, which allows build tools like SBT to request the
project (and specific version) with a relatively simple string.

The reference implementation of the Java HDF5 libraries, maintained by the HDF Group,
is downloaded in this way.  Scala is highly compatible with Java, so it just uses
the Java libraries directly.  There are [Javadocs](https://www.hdfgroup.org/products/java/hdf-java-html/javadocs/)
and [some example programs](https://www.hdfgroup.org/HDF5/examples/api18-java.html).


## How to compile and run

You first need to copy or add links to all the hdf jars to the `lib` directory (create it if it doesn't exist).
On my system they are the following

```
jhdf4obj.jar
jhdf5.jar
jhdf5obj.jar
jhdf.jar
jhdfobj.jar
jhdfview.jar
```

To compile the project, if SBT is correctly configured, you need only type `sbt compile` at the command-line.

To run the project, you will need to make sure Java can find the HDF5 binaries.  In my case, the following worked on Ubuntu 14.04:

```
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/lib/jni
```

because that is where the `libjhdf5.so` file is located on my system.

On Mac OS X, I copied the `.dylib` files from Resources/lib in the Mac OS HDF-Java distribution into `Library/Java/Extensions` in my home directory.  No combination of `DYLD_LIBRARY_PATH` and other things seemed to work.

Locations may be different on Windows and Mac, or may just work.

To run the example, use `sbt run` (once the library path includes the HDF5 binary library).  It will compile the code if needed.


## Possible improvements

It would be interesting to try the higher level [JHDF5](https://wiki-bsse.ethz.ch/display/JHDF5/Documentation+page)
wrapper maintained at ETH Zurich.  It's not in a maven repository, but you can drop the jar in a `lib` directory for
SBT to use it.  They do not appear to have an Image wrapper.

It would also be interesting to try this using [scala-saddle](https://saddle.github.io/).
The lack of published scaladocs may be an impediment.
