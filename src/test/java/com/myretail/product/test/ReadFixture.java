package com.myretail.product.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFixture {

    public static String readFixture(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("src/test/resources/fixtures/" + path));
            return new String(encoded, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
