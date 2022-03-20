package com.ivscheianu;

import com.google.gson.GsonBuilder;

import java.awt.image.BufferedImage;
import java.util.List;

final class Main {
    public static void main(final String[] args) {
        final BufferedImage image = Utils.readImage("map.png");
        final List<Parcel> parcels = new MyMapParser().parseImage(image);
        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(parcels);
        Utils.writeToFile(json, "result.json");
    }
}
