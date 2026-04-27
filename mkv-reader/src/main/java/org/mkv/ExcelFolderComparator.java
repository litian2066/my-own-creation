package org.mkv;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelFolderComparator {
    public static void main(String[] args) {
        // 配置路径 (修改为实际路径)
        String excelPath = "/Users/litian/Documents/download/chrome/4K动漫收集.xlsx";
        String folderPath = "/Volumes/No.4（4K Anime Collection）/BAN";

        // 1. 读取本地文件夹名称
        List<File> folderNames = new ArrayList<>();
        File rootDir = new File(folderPath);
        if (rootDir.exists() && rootDir.isDirectory()) {
            for (File file : rootDir.listFiles()) {
                if (file.isDirectory()) {
                    String processedName = removeBrackets(file.getName()).trim();
                    folderNames.add(file);
                }
            }
        }

        // 2. 读取Excel并比较
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0); // 第一个工作表
            DataFormatter formatter = new DataFormatter();
            
            System.out.println("匹配结果:");
            System.out.println("----------------------------------");

            for (Row row : sheet) {
                Cell cell = row.getCell(0); // 第一列
                if (cell == null) continue;
                
                String cellValue = formatter.formatCellValue(cell);
                String processedCell = removeBrackets(cellValue).trim();
                folderNames.stream()
                        .filter(item -> item.getName().contains(processedCell))
                        .findAny().ifPresent(item -> {
                            System.out.printf("✅ 存在: %s (原始: %s)%n", processedCell, item.getName());
                            FileUtil.del(item);
                        });


//                else {
//                    System.out.printf("❌ 缺失: %s (原始: %s)%n", processedCell, cellValue);
//                }
            }
        } catch (Exception e) {
            System.err.println("处理错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 移除括号及括号内容 (支持中英文括号)
    private static String removeBrackets(String input) {
        String text = "$[^)]*$";
        return input.replaceAll(text, "")  // 英文括号
                   .replaceAll("（[^）]*）", "");    // 中文括号
    }
}
