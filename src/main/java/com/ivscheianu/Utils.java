package com.ivscheianu;

import static java.util.Objects.requireNonNull;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

@UtilityClass
class Utils {

    private static final ClassLoader CLASS_LOADER = Utils.class.getClassLoader();

    @SneakyThrows
    BufferedImage readImage(final String path) {
        return ImageIO.read(requireNonNull(CLASS_LOADER.getResource(path)));
    }

    @SneakyThrows
    String readString(final String path) {
        final URL url = CLASS_LOADER.getResource(path);
        final File file = new File(requireNonNull(url).getFile());
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    @SneakyThrows
    void writeToFile(final String string, final String path) {
        try (final FileWriter fileWriter = new FileWriter(path)) {
            IOUtils.write(string, fileWriter);
        }
    }
}
