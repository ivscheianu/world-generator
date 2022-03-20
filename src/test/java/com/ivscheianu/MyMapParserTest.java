package com.ivscheianu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Stream;

class MyMapParserTest {

    private static final BufferedImage NULL_IMAGE = null;

    private final AbstractImageParser<ParcelType> myMapParser = new MyMapParser();

    @Test
    void fullMapTest() {
        //given
        final BufferedImage image = readImage("map/full_map.png");

        //when
        final List<Parcel> parcels = myMapParser.parseImage(image);
        final String json = new Gson().toJson(parcels);

        //then
        final String expectedJson = Utils.readString("json/full_map.json");
        assertEquals(expectedJson, json);
    }

    @ParameterizedTest
    @MethodSource("validMapDataProvider")
    void testValidMap(final BufferedImage image, final List<Parcel> expected) {
        //when
        final List<Parcel> result = myMapParser.parseImage(image);

        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("invalidMapDataProvider")
    void testInvalidMap(final BufferedImage image, final String expectedErrorMessage) {
        //when
        final ValidationMessage invalidParcelType = assertThrows(ValidationMessage.class, () -> {
            myMapParser.parseImage(image);
        });

        //then
        assertEquals(expectedErrorMessage, invalidParcelType.getMessage());
    }

    private static BufferedImage readImage(final String path) {
        return Utils.readImage(path);
    }

    private static Stream<Arguments> validMapDataProvider() {
        /*
         * 1st param - image that will be scanned
         * 2nd param - expected result of the parsing
         */

        return Stream.of(
            Arguments.of(
                readImage("map/valid/portion_main_types.png"),
                List.of(
                    new Parcel(0, 0, ParcelType.BEACH.getId()),
                    new Parcel(0, 1, ParcelType.PLAIN.getId()),
                    new Parcel(1, 0, ParcelType.RIVER.getId()),
                    new Parcel(1, 1, ParcelType.RESERVED.getId())
                )
            ),
            Arguments.of(
                readImage("map/valid/all_main_types_horizontal.png"),
                List.of(
                    new Parcel(0, 0, ParcelType.BEACH.getId()),
                    new Parcel(0, 1, ParcelType.PLAIN.getId()),
                    new Parcel(0, 2, ParcelType.RESERVED.getId()),
                    new Parcel(1, 0, ParcelType.RIVER.getId()),
                    new Parcel(1, 1, ParcelType.SNOW.getId()),
                    new Parcel(1, 2, ParcelType.FOREST.getId())
                )
            ),
            Arguments.of(
                readImage("map/valid/all_main_types_vertical.png"),
                List.of(
                    new Parcel(0, 0, ParcelType.BEACH.getId()),
                    new Parcel(0, 1, ParcelType.PLAIN.getId()),
                    new Parcel(1, 0, ParcelType.RESERVED.getId()),
                    new Parcel(1, 1, ParcelType.RIVER.getId()),
                    new Parcel(2, 0, ParcelType.SNOW.getId()),
                    new Parcel(2, 1, ParcelType.FOREST.getId())
                )
            ),
            Arguments.of(
                readImage("map/valid/all_transition_types_horizontal.png"),
                List.of(
                    new Parcel(0, 0, ParcelType.RESERVED_RIVER.getId()),
                    new Parcel(0, 1, ParcelType.BEACH_RIVER.getId()),
                    new Parcel(0, 2, ParcelType.BEACH_PLAIN.getId()),
                    new Parcel(0, 3, ParcelType.BEACH_PLAIN_RIVER.getId()),
                    new Parcel(0, 4, ParcelType.PLAIN_RIVER.getId()),
                    new Parcel(0, 5, ParcelType.PLAIN_FOREST.getId()),
                    new Parcel(1, 0, ParcelType.PLAIN_FOREST_RIVER.getId()),
                    new Parcel(1, 1, ParcelType.FOREST_RIVER.getId()),
                    new Parcel(1, 2, ParcelType.FOREST_SNOW.getId()),
                    new Parcel(1, 3, ParcelType.FOREST_SNOW_RIVER.getId()),
                    new Parcel(1, 4, ParcelType.SNOW_RIVER.getId()),
                    new Parcel(1, 5, ParcelType.SNOW.getId())
                )
            ),
            Arguments.of(
                readImage("map/valid/all_transitions_types_vertical.png"),
                List.of(
                    new Parcel(0, 0, ParcelType.PLAIN_FOREST_RIVER.getId()),
                    new Parcel(0, 1, ParcelType.RESERVED_RIVER.getId()),
                    new Parcel(1, 0, ParcelType.FOREST_RIVER.getId()),
                    new Parcel(1, 1, ParcelType.BEACH_RIVER.getId()),
                    new Parcel(2, 0, ParcelType.FOREST_SNOW.getId()),
                    new Parcel(2, 1, ParcelType.BEACH_PLAIN.getId()),
                    new Parcel(3, 0, ParcelType.FOREST_SNOW_RIVER.getId()),
                    new Parcel(3, 1, ParcelType.BEACH_PLAIN_RIVER.getId()),
                    new Parcel(4, 0, ParcelType.SNOW_RIVER.getId()),
                    new Parcel(4, 1, ParcelType.PLAIN_RIVER.getId()),
                    new Parcel(5, 0, ParcelType.SNOW.getId()),
                    new Parcel(5, 1, ParcelType.PLAIN_FOREST.getId())
                )
            )
        );
    }

    private static Stream<Arguments> invalidMapDataProvider() {
        /*
         * 1st param - image that will be scanned
         * 2nd param - expected mapping error
         */
        return Stream.of(
            Arguments.of(
                readImage("map/invalid/unknown_main_type01.png"),
                "Can't map colors = [java.awt.Color[r=0,g=0,b=0]] to a valid parcelType on row = 0, column = 1"
            ),
            Arguments.of(
                readImage("map/invalid/unknown_main_type11.png"),
                "Can't map colors = [java.awt.Color[r=255,g=0,b=0]] to a valid parcelType on row = 1, column = 1"
            ),
            Arguments.of(
                readImage("map/invalid/invalid_transition.png"),
                "Can't map colors = [java.awt.Color[r=255,g=255,b=255], java.awt.Color[r=255,g=255,b=0]] to a valid parcelType on row = 0, column = 0"
            ),
            Arguments.of(
                readImage("map/invalid/unknown_transition.png"),
                "Can't map colors = [java.awt.Color[r=0,g=0,b=0], java.awt.Color[r=255,g=0,b=0]] to a valid parcelType on row = 1, column = 0"
            ),
            Arguments.of(
                NULL_IMAGE,
                "You can't submit a null image."
            ),
            Arguments.of(
                readImage("map/invalid/cmyk_format.jpeg"),
                "Unsupported image format."
            )
        );
    }
}