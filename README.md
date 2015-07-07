# hdf5-image-medley

This repository contains small example programs of how to read and write HDF5 images in a variety of languages.

## Overview

HDF5 Image Medley is meant to function as a mini-tutorial for how to read and 
write HDF5 image data, plus metadata, in a variety of languages.

Each code example lives in its own directory, and does the following.

  - Reads a fake 8-bit grayscale TIFF image of a wormlike shape in the parent directory.
  - Writes a HDF5 file containing the contents of that image, plus the original file name as metadata.
  - Reads that HDF5 file and extracts the image data and the metadata.
  - Writes the TIFF file, with the same name, in the local directory.

A secondary program can then verify that the image data is unchanged between the 
original and the HDF5-processed version.

The fake image data is in `worm-spine-blurred.tiff`.  It was generated via a
SVG spline drawn by hand (in `worm-spine-shape.svg`), which was then blurred and 
had the contrast adjusted before being saved as an uncompressed 8-bit grayscale 
TIFF.

Each example language should have its own `ABOUT.md` file that details how to
compile and/or run the example.  There may be different platform-specific
instructions in this file, if necessary (and anyone has tried the example on
different platforms, and reported their findings on how to get it to work).
There will also typically be notes on which versions of programs and libraries
have been verified to work.

## References

[HDF5 Image Specification](https://www.hdfgroup.org/HDF5/doc/ADGuide/ImageSpec.html)
[HDF5 Image Tutorial](https://www.hdfgroup.org/HDF5/Tutor/h5image.html)
[HDF5 User's Guide](https://www.hdfgroup.org/HDF5/doc/UG/index.html)