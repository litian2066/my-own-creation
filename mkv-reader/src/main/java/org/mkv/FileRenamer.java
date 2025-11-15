package org.mkv;

import java.io.File;

public class FileRenamer {
    public static void main(String[] args) {
        String folderPath = "/Users/litian/Downloads/Download/Quark/我的英雄学院"; // 替换为实际文件夹路径
        renameFilesInFolder(new File(folderPath));
    }

    public static void renameFilesInFolder(File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("文件夹不存在或不是目录");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                renameFilesInFolder(file); // 递归处理子文件夹
            } else {
                renameFile(file); // 处理文件重命名
            }
        }
    }

    private static void renameFile(File file) {
        String oldName = file.getName();
        int dashIndex = oldName.lastIndexOf("-");
        
        if (dashIndex > 0) {
            String newName = oldName.substring(0, dashIndex).trim();
            String extension = "";
            
            int dotIndex = oldName.lastIndexOf(".");
            if (dotIndex > dashIndex) {
                extension = oldName.substring(dotIndex);
                newName += extension;
            }

            File newFile = new File(file.getParent(), newName);
            if (file.renameTo(newFile)) {
                System.out.println("重命名成功: " + oldName + " -> " + newName);
            } else {
                System.out.println("重命名失败: " + oldName);
            }
        }
    }
}
