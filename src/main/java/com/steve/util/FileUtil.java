/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.steve.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

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

    public static List<String> readLine(String filepath) {
        String s = read(filepath);
        Pattern pattern = Pattern.compile("\n");
        String[] lines = pattern.split(s);
        return List.of(lines);
    }
}
