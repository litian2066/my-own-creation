package org.mkv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Renamer {
    public static void main(String[] args) {
        String folderPath = "/Volumes/Extreme SSD/All/Lee/TV/魔卡少女樱 (1998)/ass"; // ← 修改为你的文件夹路径
        boolean dryRun = false; // true为预览模式，false为实际重命名

        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("❌ 指定路径不存在或不是文件夹：" + folderPath);
            return;
        }

        // 获取所有 .ass 文件，按文件名数字排序
        File[] files = folder.listFiles(f -> f.getName().toLowerCase().endsWith(".ass") && !f.getName().startsWith("._"));
        if (files == null || files.length == 0) {
            System.out.println("⚠️ 文件夹中未找到任何 .ass 文件");
            return;
        }

        List<File> sortedFiles = new ArrayList<>();
        for (File f : files) {
            String name = f.getName();
            String numStr = name.replaceAll("[^0-9]", "");
            if (!numStr.isEmpty()) {
                sortedFiles.add(f);
            }
        }

        sortedFiles.sort((a, b) -> {
            String numA = a.getName().replaceAll("[^0-9]", "");
            String numB = b.getName().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(numA), Integer.parseInt(numB));
        });

        if (sortedFiles.size() != 70) {
            System.out.println("⚠️ 期望70个文件，但检测到" + sortedFiles.size() + "个，建议检查文件完整性");
        }

        int episode = 1;
        for (File file : sortedFiles) {
            String newName;
            if (episode <= 35) {
                newName = String.format("S01E%02d.ass", episode);
            } else if (episode <= 46) {
                newName = String.format("S02E%02d.ass", episode - 35);
            } else {
                newName = String.format("S03E%02d.ass", episode - 46);
            }

            File newFile = new File(folder, newName);
            if (newFile.exists()) {
                System.out.println("⚠️ 跳过：目标文件已存在 → " + newName);
                episode++;
                continue;
            }

            if (dryRun) {
                System.out.println("📄 预览: " + file.getName() + " → " + newName);
            } else {
                if (file.renameTo(newFile)) {
                    System.out.println("✅ 成功: " + file.getName() + " → " + newName);
                } else {
                    System.out.println("❌ 失败: " + file.getName() + " → " + newName);
                }
            }
            episode++;
        }

        System.out.println("\n🎉 批量重命名完成！如需实际执行，请将 dryRun 改为 false");
    }
}
