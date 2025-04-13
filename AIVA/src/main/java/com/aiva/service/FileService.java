package com.aiva.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for file operations
 */
public class FileService {
    
    /**
     * Reads a file into a string
     * @param filePath Path to the file
     * @return String content of the file
     * @throws IOException if file cannot be read
     */
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        
        return content.toString();
    }
    
    /**
     * Writes a string to a file
     * @param filePath Path to the file
     * @param content Content to write
     * @throws IOException if file cannot be written
     */
    public static void writeStringToFile(String filePath, String content) throws IOException {
        // Create parent directories if they don't exist
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
