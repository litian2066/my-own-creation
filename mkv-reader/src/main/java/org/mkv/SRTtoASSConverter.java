package org.mkv;

import cn.hutool.core.io.file.FileNameUtil;

import java.io.*;
import java.util.regex.Pattern;

public class SRTtoASSConverter {
    
    public static void main(String[] args) throws IOException {


        String srtFilePath = "/Volumes/Extreme SSD/Lee/TV/知否知否应是绿肥红瘦 DVD版 (2018)/Season 1";
        String assFilePath = srtFilePath + "/ass";
        doParseAss(new File(srtFilePath), assFilePath);
//        try {
//            convertSRTtoASS(srtFilePath, assFilePath);
//            System.out.println("转换完成！输出文件: " + assFilePath);
//        } catch (IOException e) {
//            System.err.println("转换过程中发生错误: " + e.getMessage());
//        }
    }

    private static void doParseAss(File srtFilePath, String assFilePath) throws IOException {
        if (srtFilePath.isDirectory()) {
            File[] files = srtFilePath.listFiles();
            for (int i = 0; i < files.length; i++) {
                doParseAss(files[i], assFilePath);
            }
        } else {
            String name = srtFilePath.getName();
            if (!name.startsWith("._") && name.endsWith(".srt")) {
                File file = new File(assFilePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String assFilePah = assFilePath + "/" + FileNameUtil.getPrefix(srtFilePath) + ".ass";
                convertSRTtoASS(srtFilePath.getAbsolutePath(), assFilePah);
            }
        }
    }
    
    public static void convertSRTtoASS(String srtFilePath, String assFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(srtFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(assFilePath))) {
            
            // 写入ASS文件头信息
            writeASSHeader(writer);
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    // 尝试解析序号行
                    int subtitleIndex = Integer.parseInt(line.trim());
                    
                    // 读取时间轴行
                    String timeLine = reader.readLine();
                    if (timeLine == null) break;
                    
                    String[] timeParts = timeLine.split(" --> ");
                    if (timeParts.length != 2) continue;
                    
                    String startTime = srtToAssTime(timeParts[0].trim());
                    String endTime = srtToAssTime(timeParts[1].trim());
                    
                    // 读取字幕文本
                    StringBuilder textBuilder = new StringBuilder();
                    String textLine;
                    while ((textLine = reader.readLine()) != null && !textLine.trim().isEmpty()) {
                        if (textBuilder.length() > 0) {
                            textBuilder.append("\\N");
                        }
                        textBuilder.append(textLine.trim());
                    }
                    
                    String subtitleText = textBuilder.toString();
                    if (!subtitleText.isEmpty()) {
                        writer.write("Dialogue: 0," + startTime + "," + endTime + ",Default,,0,0,0,," + subtitleText + "\n");
                    }
                    
                } catch (NumberFormatException e) {
                    // 如果不是序号行，跳过
                    continue;
                }
            }
        }
    }
    
    private static void writeASSHeader(BufferedWriter writer) throws IOException {
        writer.write("[Script Info]\n");
        writer.write("Title: Converted from SRT\n");
        writer.write("ScriptType: v4.00+\n");
        writer.write("WrapStyle: 0\n");
        writer.write("PlayResX: 1920\n");
        writer.write("PlayResY: 1080\n");
        writer.write("ScaledBorderAndShadow: yes\n");
        writer.write("\n");
        
        writer.write("[V4+ Styles]\n");
        writer.write("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding\n");
       //  writer.write("Style: Default,方正锐正黑_GBK ExtraBold,75,&H00FFFFFF,&H000000FF,&H00000000,&H00000000,0,0,0,0,100,100,0,0,1,1.7,0,2,10,10,30,1\n");
        writer.write("Style: Default,方正FW轻吟体 简 D,80,&H00FFFFFF,&HFF000000,&H20000000,&H00666666,0,0,0,0,100,100,2,0,1,2.1,0,2,10,10,30,134\n");
//        writer.write("Style: Default,思源黑体 CN Heavy,75,&H00FFFFFF,&H000019FF,&H00000000,&H00000000,0,0,0,0,100.0,100.0,0.0,0.0,1,3.0,0.0,2,10,10,20,1\n");
        writer.write("\n");
        
        writer.write("[Events]\n");
        writer.write("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text\n");
    }
    
    private static String srtToAssTime(String srtTime) {
        // 处理SRT时间格式: 00:01:30,500 --> ASS格式: 0:01:30.50
        String[] parts = srtTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        
        String[] secondsParts = parts[2].split(",");
        int seconds = Integer.parseInt(secondsParts[0]);
        int milliseconds = Integer.parseInt(secondsParts[1]) / 10; // ASS使用百分秒
        
        return String.format("%01d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds);
    }
}
