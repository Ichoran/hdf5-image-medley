import h5py
import tifffile
import os
import numpy as np

h5file = 'Example.hdf5'
filename = '..' + os.sep + 'worm-spine-blurred.tiff'

image = tifffile.imread(filename)

with h5py.File(h5file, 'w') as fid:
	dataset = fid.create_dataset('/example', data=image)
	dataset.attrs['original_filename'] = filename

	del([image, filename]) #just to be sure this variable do not exist when I read and write the data again
	
	#optional attributes to make the dataset regonized as image by HDFView
	dataset.attrs["CLASS"] = np.string_("IMAGE")
	dataset.attrs["IMAGE_SUBCLASS"] = np.string_("IMAGE_GRAYSCALE")
	dataset.attrs["IMAGE_WHITE_IS_ZERO"] = np.array(0, dtype="uint8")
	dataset.attrs["DISPLAY_ORIGIN"] = np.string_("UL") # not rotated
	dataset.attrs["IMAGE_VERSION"] = np.string_("1.2")

with h5py.File(h5file, 'r') as fid:
	image = fid['/example'][:]
	filename = fid['/example'].attrs['original_filename'].rpartition(os.sep)[-1]

	tifffile.imsave(filename, image)