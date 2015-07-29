# hdf5-image-medley/MATLAB

Primary author: [ver228](https://github.com/ver228)

## About

MATLAB includes full support for the whole hdf5 library. The current library
[h5* functions](http://uk.mathworks.com/help/matlab/hdf5-files.html)
was introduced in the version R2011a.  However, there was support for hdf5
before R2006a using the [hdf5* functions](http://uk.mathworks.com/help/matlab/ref/hdf5read.html).
The old libraries are slow and are going to be removed in future versions,
so they should be avoided if possible.

Note that from R2006b the .mat files are
[internally stored as hdf5](http://uk.mathworks.com/help/matlab/import_export/mat-file-versions.html).
