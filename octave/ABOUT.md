# hdf5-image-medley/octave

Primary author: Rex Kerr

## Octave support for HDF5 is not promising

Octave contains built-in support for HDF5 [reading](http://octave.sourceforge.net/octave/function/load.html)
and [writing](http://octave.sourceforge.net/octave/function/save.html) via a
`-hdf5` flag.  Unfortunately, support is far from complete.

There is a partial implementation of a [library for lower-level support](https://github.com/antst/hdf5oct),
but it is incomplete.

Thus, without going through external functions (e.g. in C), Octave cannot
support HDF5.

### Conclusion: Octave cannot be used to read/write HDF5 image files at this time
