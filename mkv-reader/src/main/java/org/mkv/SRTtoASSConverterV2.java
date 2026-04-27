package org.mkv;

import cn.hutool.core.io.file.FileNameUtil;

import java.io.*;

public class SRTtoASSConverterV2 {
    
    public static void main(String[] args) throws IOException {

        String srtFilePath = "/Volumes/Extreme SSD/Lee/TV/知否知否应是绿肥红瘦 DVD版 (2018)/Season 1";
        String assFilePath =srtFilePath +  "/ass2";
        doParseAss(new File(srtFilePath), assFilePath);

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
        writer.write("PlayResX: 1280\n");
        writer.write("PlayResY: 720\n");
        writer.write("ScaledBorderAndShadow: yes\n");
        writer.write("\n");
        
        writer.write("[V4+ Styles]\n");
        writer.write("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding\n");
        writer.write("Style: Default,腾祥嘉丽中圆GB18030,47,&H00FFFFFF,&HF0000000,&H00193768,&H800A2643,-1,0,0,0,100,100,2,0,1,1.5,0.3,2,10,10,15,1\n");
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
