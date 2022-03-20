package com.ivscheianu;

import static java.util.Objects.isNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractImageParser<T extends HasId<? extends Long>> {

    protected abstract Map<Set<Color>, T> getMappings();

    protected abstract boolean isInvalidFormat(final BufferedImage bufferedImage);

    protected abstract int getParcelSize();

    List<Parcel> parseImage(final BufferedImage image) {
        applyBasicValidations(image);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int numberOfComponents = image.getColorModel().getNumComponents();
        final Raster data = image.getData();
        return getParcels(width, height, numberOfComponents, data);
    }

    private void applyBasicValidations(final BufferedImage image) {
        if (isNull(image)) {
            throw new ValidationMessage("You can't submit a null image.");
        }
        if (isInvalidFormat(image)) {
            throw new ValidationMessage("Unsupported image format.");
        }
    }

    private List<Parcel> getParcels(final int width, final int height, final int numberOfComponents, final Raster data) {
        final int parcelSize = getParcelSize();
        final List<Parcel> parcels = new ArrayList<>(parcelSize * parcelSize);
        final int[] buffer = new int[numberOfComponents * parcelSize * parcelSize];
        for (int rowIndex = 0; rowIndex < height; rowIndex += parcelSize) {
            for (int columnIndex = 0; columnIndex < width; columnIndex += parcelSize) {
                //data.getPixels() will put the info into the last param, the buffer
                data.getPixels(columnIndex, rowIndex, parcelSize, parcelSize, buffer);
                final Parcel parcel = parseParcel(numberOfComponents, buffer, rowIndex, columnIndex);
                parcels.add(parcel);
            }
        }
        return parcels;
    }

    private Parcel parseParcel(final int numberOfComponents, final int[] currentSquare, final int rowIndex, final int columnIndex) {
        final Set<Color> colors = getColorsInsideTheSquare(numberOfComponents, currentSquare);
        final T parcelType = getMappings().get(colors);
        final int parcelSize = getParcelSize();
        if (isNull(parcelType)) {
            final String errorMessage = String.format("Can't map colors = %s to a valid parcelType on row = %d, column = %d",
                                                      colors, rowIndex / parcelSize, columnIndex / parcelSize);
            throw new ValidationMessage(errorMessage);
        }
        return Parcel
            .builder()
            .rowIndex(rowIndex / parcelSize)
            .columnIndex(columnIndex / parcelSize)
            .type(parcelType.getId())
            .build();
    }

    private Set<Color> getColorsInsideTheSquare(final int numberOfComponents, final int[] square) {
        return Stream.iterate(0, index -> index + numberOfComponents)
            .limit(square.length / numberOfComponents) //limit is actually limiting the iterations, not the index
            .map(index -> extractColorFromSquare(square, index))
            .collect(Collectors.toSet());
    }

    private Color extractColorFromSquare(final int[] buffer, final Integer index) {
        //RGBA format
        return new Color(buffer[index], buffer[index + 1], buffer[index + 2], buffer[index + 3]);
    }
}
