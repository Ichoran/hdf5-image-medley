# hdf5-image-medley/python

Primary author: [ver228](https://github.com/ver228)

## About

There exist two ports for the HDF5 libraries in python: [H5py](http://www.h5py.org/) and [pytables](http://www.pytables.org/). H5py focus in simplicity, and to have a feeling similar to numpy. Pytables includes extra-features that makes it a bit harder to use and can reduce the compatibility. Using the array class and its derivates in pytables can bring pretty much the same functionality a h5py. The real power of pytables comes when the data is organized by tables, specially when it is using together with the pandas library.

The files h5pyImageExample.py and pytablesImageExample.py show examples on how to read and write images to hdf5 using each fo the libraries. 

The python distribution [Anaconda] (https://store.continuum.io/cshop/anaconda/) contains the required libraries, or they can be installed using pip ([osx](http://stackoverflow.com/questions/17271319/installing-pip-on-mac-os-x), [windows](http://stackoverflow.com/questions/4750806/how-to-install-pip-on-windows)) to install the libraries tables, h5py, numpy and tifffile.
Ubuntu (verified in 14.10) also contains these libraries in the packages python-tables, python-h5py, python-numpy, and python-tifffile.

## Examples to add

It would be valuable to add and example that shows how to store a set of images in a multidimensional array. 

One important hdf5 functionality is its in-build data compression. Both h5py and pytables contain several extra compression filters but I would recommend to only use gzip to ensure compatibility.

ver228 has created a [simple h5df video viewer](https://github.com/ver228/Multiworm_Tracking/tree/master/examples/simpleHDF5videoviewer) using pyqt5. It is limited to uint8 data stored 3D arrays, and may expand its capability if it seems useful.
