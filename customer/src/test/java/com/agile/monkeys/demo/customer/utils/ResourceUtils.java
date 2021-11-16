package com.agile.monkeys.demo.customer.utils;

import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ResourceUtils {

    private ResourceUtils() {
    }

    public static String loadAsString(String name) {
        try {
            URL url = ResourceUtils.class
                    .getClassLoader()
                    .getResource(name);

            if (url == null) {
                throw new IllegalArgumentException("Resource " + name + " not found");
            }

            return Resources.toString(url, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static File loadAsFile(String name) {
        String filePath = ResourceUtils.class
                .getClassLoader()
                .getResource(name)
                .getFile();

        if (filePath == null) {
            throw new IllegalArgumentException("Resource " + name + " not found");
        }

        return new File(filePath);
    }
}

