import tables
import tifffile
import os
import numpy as np

h5file = 'Example.hdf5'
filename = '..' + os.sep + 'worm-spine-blurred.tiff'

image = tifffile.imread(filename)

with tables.File(h5file, 'w') as fid:
	dataset = fid.create_array('/', 'example', obj=image)
	dataset._v_attrs['original_filename'] = filename

	del([image, filename]) #just to be sure this variable do not exist when I read and write the data again
	
	#optional attributes to make the dataset regonized as image by HDFView
	dataset._v_attrs["CLASS"] = np.string_("IMAGE")
	dataset._v_attrs["IMAGE_SUBCLASS"] = np.string_("IMAGE_GRAYSCALE")
	dataset._v_attrs["IMAGE_WHITE_IS_ZERO"] = np.array(0, dtype="uint8")
	dataset._v_attrs["DISPLAY_ORIGIN"] = np.string_("UL") # not rotated
	dataset._v_attrs["IMAGE_VERSION"] = np.string_("1.2")

with tables.File(h5file, 'r') as fid:
	image = fid.root.example[:]
	filename = fid.root.example._v_attrs['original_filename'].rpartition(os.sep)[-1]

	tifffile.imsave(filename, image)