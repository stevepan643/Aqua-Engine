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

    /**
     * Reads the content of a file specified by the given file path.
     *
     * @param filepath the path to the file to be read
     * @return the content of the file as a String, or an empty string if
     *         an error occurs
     */
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
