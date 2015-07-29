h5file = 'h5example.hdf5';

filename = fullfile('..', 'worm-spine-blurred.tiff');

%read tiff and write hdf5
image = imread(filename);
hdf5write(h5file, '/example', image)
h5writeatt(h5file, '/example', 'original_filename', filename)

clearvars image filename

%read hdf5 and write tiff
image = hdf5read(h5file, '/example');
filename = h5readatt(h5file, '/example', 'original_filename');
filename = filename(2:end);
imwrite(image, filename)

