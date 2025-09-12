package org.mkv;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileLineProcessor {
    public static void main(String[] args) {
        String inputPath = "/Users/litian/Documents/Colorful/my-own-creation/tiny-media-manager/cinema/帕丁顿熊/example.ass";
        String outputPath = "/Users/litian/Documents/Colorful/my-own-creation/tiny-media-manager/cinema/帕丁顿熊/example_out.ass";
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath), StandardCharsets.UTF_8)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\\N{\\rEng}");
                writer.newLine();
            }
            System.out.println("文件处理完成，输出至: " + outputPath);
            
        } catch (IOException e) {
            System.err.println("处理文件时出错: " + e.getMessage());
        }
    }
}
