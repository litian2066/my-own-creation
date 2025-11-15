package org.mkv;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamerSeason {
    // 配置分季数组，例如[5,10,40]表示：
    // 第1季:1-5
    // 第2季:6-15 (6=5+1,15=5+10)
    // 第3季:16-55 (16=5+10+1,55=5+10+40)
    private static final int[] SEASON_CONFIG = {25, 12, 22, 28};

    public static void main(String[] args) {
        String folderPath = "/Volumes/Extreme SSD/Lee/BD/进击的巨人"; // 替换为你的文件夹路径
        File folder = new File(folderPath);
        
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("文件夹不存在或不是有效目录");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("文件夹为空");
            return;
        }

        // 处理每个文件
        for (File file : files) {
            if (file.isFile()) {
                int number = extractNumber(file.getName());
                if (number > 0) {
                    String newName = generateSeasonName(number);
                    System.out.println(newName);
                    renameFile(file, newName);
                }
            }
        }
    }

    // 从文件名中提取数字
    private static int extractNumber(String fileName) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }

    // 根据数字生成分季名称
    private static String generateSeasonName(int number) {
        int season = 1;
        int episode = number;
        int accumulated = 0;

        for (int i = 0; i < SEASON_CONFIG.length; i++) {
            if (number > accumulated && number <= accumulated + SEASON_CONFIG[i]) {
                season = i + 1;
                episode = number - accumulated;
                break;
            }
            accumulated += SEASON_CONFIG[i];
        }

        // 如果数字超过配置总和，则继续在最后一季递增
        if (number > accumulated) {
            season = SEASON_CONFIG.length;
            episode = number - accumulated;
        }

        return String.format("S%02dE%02d", season, episode);
    }

    // 重命名文件
    private static void renameFile(File file, String newName) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = fileName.substring(dotIndex);
        }

        File newFile = new File(file.getParent(), newName + extension);
        if (file.renameTo(newFile)) {
            System.out.println("重命名成功: " + fileName + " -> " + newName + extension);
        } else {
            System.out.println("重命名失败: " + fileName);
        }
    }
}
