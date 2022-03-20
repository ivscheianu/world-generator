package com.ivscheianu;

import static java.util.Map.entry;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

final class MyMapParser extends AbstractImageParser<ParcelType> {

    private static final int PARCEL_SIZE = 10; //10 pixels
    private static final int NUMBER_OF_COMPONENTS = 4; //RGBA

    private static final int FULL_COLOR = 255;
    private static final int HALF_COLOR = 128;
    private static final int NO_COLOR = 0;

    private static final Color BEACH_COLOR = new Color(FULL_COLOR, FULL_COLOR, NO_COLOR);
    private static final Color PLAIN_COLOR = new Color(NO_COLOR, HALF_COLOR, NO_COLOR);
    private static final Color RESERVED_COLOR = new Color(FULL_COLOR, NO_COLOR, FULL_COLOR);
    private static final Color RIVER_COLOR = new Color(NO_COLOR, HALF_COLOR, FULL_COLOR);
    private static final Color SNOW_COLOR = new Color(FULL_COLOR, FULL_COLOR, FULL_COLOR);
    private static final Color FOREST_COLOR = new Color(FULL_COLOR, HALF_COLOR, NO_COLOR);

    private static final Set<Color> RIVER = Collections.singleton(RIVER_COLOR);
    private static final Set<Color> RESERVED = Collections.singleton(RESERVED_COLOR);
    private static final Set<Color> RESERVED_RIVER = Set.of(RESERVED_COLOR, RIVER_COLOR);

    private static final Set<Color> BEACH = Collections.singleton(BEACH_COLOR);
    private static final Set<Color> BEACH_RIVER = Set.of(BEACH_COLOR, RIVER_COLOR);
    private static final Set<Color> BEACH_PLAIN = Set.of(BEACH_COLOR, PLAIN_COLOR);
    private static final Set<Color> BEACH_PLAIN_RIVER = Set.of(BEACH_COLOR, PLAIN_COLOR, RIVER_COLOR);

    private static final Set<Color> PLAIN = Collections.singleton(PLAIN_COLOR);
    private static final Set<Color> PLAIN_RIVER = Set.of(PLAIN_COLOR, RIVER_COLOR);
    private static final Set<Color> PLAIN_FOREST = Set.of(PLAIN_COLOR, FOREST_COLOR);
    private static final Set<Color> PLAIN_FOREST_RIVER = Set.of(PLAIN_COLOR, FOREST_COLOR, RIVER_COLOR);

    private static final Set<Color> FOREST = Collections.singleton(FOREST_COLOR);
    private static final Set<Color> FOREST_RIVER = Set.of(FOREST_COLOR, RIVER_COLOR);
    private static final Set<Color> FOREST_SNOW = Set.of(FOREST_COLOR, SNOW_COLOR);
    private static final Set<Color> FOREST_SNOW_RIVER = Set.of(FOREST_COLOR, SNOW_COLOR, RIVER_COLOR);

    private static final Set<Color> SNOW = Collections.singleton(SNOW_COLOR);
    private static final Set<Color> SNOW_RIVER = Set.of(SNOW_COLOR, RIVER_COLOR);

    @Override
    protected Map<Set<Color>, ParcelType> getMappings() {
        return Map.ofEntries(
            entry(RESERVED, ParcelType.RESERVED),
            entry(RESERVED_RIVER, ParcelType.RESERVED_RIVER),
            entry(BEACH, ParcelType.BEACH),
            entry(BEACH_RIVER, ParcelType.BEACH_RIVER),
            entry(BEACH_PLAIN, ParcelType.BEACH_PLAIN),
            entry(BEACH_PLAIN_RIVER, ParcelType.BEACH_PLAIN_RIVER),
            entry(PLAIN, ParcelType.PLAIN),
            entry(PLAIN_RIVER, ParcelType.PLAIN_RIVER),
            entry(PLAIN_FOREST, ParcelType.PLAIN_FOREST),
            entry(PLAIN_FOREST_RIVER, ParcelType.PLAIN_FOREST_RIVER),
            entry(FOREST, ParcelType.FOREST),
            entry(FOREST_RIVER, ParcelType.FOREST_RIVER),
            entry(FOREST_SNOW, ParcelType.FOREST_SNOW),
            entry(FOREST_SNOW_RIVER, ParcelType.FOREST_SNOW_RIVER),
            entry(SNOW, ParcelType.SNOW),
            entry(SNOW_RIVER, ParcelType.SNOW_RIVER),
            entry(RIVER, ParcelType.RIVER)
        );
    }

    @Override
    protected boolean isInvalidFormat(final BufferedImage image) {
        //supports RGBA format
        final ColorModel colorModel = image.getColorModel();
        return colorModel.getNumComponents() != NUMBER_OF_COMPONENTS ||
               colorModel.getColorSpace().getType() != ColorSpace.TYPE_RGB;
    }

    @Override
    protected int getParcelSize() {
        return PARCEL_SIZE;
    }
}
