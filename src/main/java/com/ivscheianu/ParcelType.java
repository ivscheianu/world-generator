package com.ivscheianu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum ParcelType implements HasId<Long> {

    RESERVED(0, "Reserved"),
    RIVER(1, "River"),
    RESERVED_RIVER(2, "Reserved with river"),
    BEACH(3, "Beach"),
    BEACH_RIVER(4, "Beach with river"),
    BEACH_PLAIN(5, "Beach to plain transition"),
    BEACH_PLAIN_RIVER(6, "Beach to plain transition with river"),
    PLAIN(7, "Plain"),
    PLAIN_RIVER(8, "Plain with river"),
    PLAIN_FOREST(9, "Plain to forest transition"),
    PLAIN_FOREST_RIVER(10, "Plain to forest with river"),
    FOREST(11, "Forest"),
    FOREST_RIVER(12, "Forest with river"),
    FOREST_SNOW(13, "Forest to snow transition"),
    FOREST_SNOW_RIVER(14, "Forest to snow transition with river"),
    SNOW(15, "River"),
    SNOW_RIVER(16, "Snow with river");

    private final long id;
    private final String name;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
