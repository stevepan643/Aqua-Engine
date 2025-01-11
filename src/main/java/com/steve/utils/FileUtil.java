package com.steve.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This Class used to read file by filepath.
 * 
 * @author Steve Pan
 * @version 1.0
 */
public class FileUtil {
    public static String read(String filepath) {

        try {
            return Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Filed to Read File");
        return "";
    }
}
