package org.mkv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceText {

    private final static int start = 1;
    private final static int end = 26;
    private final static String text = "/Applications/MKVToolNix.app/Contents/MacOS/mkvmerge --ui-language en_US --priority lower --output '/Volumes/Extreme SSD/All/Lee/TVBAK/待处理/ccc (2014)/Specials/after/漫画家与助手 - S00E[01].mkv' --no-subtitles --language 0:und --display-dimensions 0:3840x2160 --color-range 0:1 --language 1:ja '(' '/Volumes/Extreme SSD/All/Lee/TVBAK/待处理/ccc (2014)/Specials/漫画家与助手 - S00E[01].mkv' ')' --language 0:zh --track-name '0:简体中文' '(' '/Volumes/Extreme SSD/All/Lee/TVBAK/待处理/ccc (2014)/漫画家与助手/S00E[01].zh.ass' ')' --attachment-name '腾祥嘉丽中圆GB18030.ttf' --attachment-mime-type font/ttf --attach-file '/Volumes/Extreme SSD/All/字体常用/腾祥嘉丽中圆GB18030.ttf' --attachment-name WenQuanYiMicroHei.ttf --attachment-mime-type font/ttf --attach-file '/Volumes/Extreme SSD/All/字体常用/WenQuanYiMicroHei.ttf' --attachment-name 'DFMaruMoji-W3 & DFPMaruMoji-W3 & DFGMaruMoji-W3.ttc' --attachment-mime-type font/collection --attach-file '/Users/litian/Downloads/超级字体整合包 XZ/完整包/DynaFont（华康）/日文/ttf/Std/デザイン書体（创意字体）/DFMaruMoji-W3 & DFPMaruMoji-W3 & DFGMaruMoji-W3.ttc' --attachment-name 'DFMaruMojiG-W3 & DFPMaruMojiG-W3 & DFGMaruMojiG-W3.ttc' --attachment-mime-type font/collection --attach-file '/Users/litian/Downloads/超级字体整合包 XZ/完整包/DynaFont（华康）/日文/ttf/ビブロス外字/DFMaruMojiG-W3 & DFPMaruMojiG-W3 & DFGMaruMojiG-W3.ttc' --attachment-name DFMaruMojiStd-W3.otf --attachment-mime-type font/otf --attach-file '/Users/litian/Downloads/超级字体整合包 XZ/完整包/DynaFont（华康）/日文/otf/Std/デザイン書体（创意字体）/DFMaruMojiStd-W3.otf' --attachment-name '华康少女文字W5 & 华康少女文字W5(P).ttc' --attachment-mime-type font/collection --attach-file '/Users/litian/Downloads/超级字体整合包 XZ/完整包/DynaFont（华康）/简体/ttf/华康少女文字W5 & 华康少女文字W5(P).ttc' --attachment-name '微軟正黑體-1.ttf' --attachment-mime-type font/ttf --attach-file '/Users/litian/Documents/download/chrome/微軟正黑體-1.ttf' --attachment-name 'DFHSGothic-W5 & DFPHSGothic-W5 & DFGHSGothic-W5.ttc' --attachment-mime-type font/collection --attach-file '/Users/litian/Downloads/超级字体整合包 XZ/完整包/DynaFont（华康）/日文/ttf/Std/ゴシック体（黑体）/DFHSGothic-W5 & DFPHSGothic-W5 & DFGHSGothic-W5.ttc' --attachment-name '方正隶变_GBK.ttf' --attachment-mime-type font/ttf --attach-file '/Users/litian/Documents/download/chrome/砂糖外挂字幕字体包/方正隶变_GBK.ttf' --track-order 0:0,0:1,1:0";

    public static void main(String[] args) throws IOException {
        StringReplacer();
    }


    public static void StringReplacer() throws IOException {

        String outputFile = "/Users/litian/Documents/mac/codes/my-own-creation/mkv-reader/src/main/java/org/mkv/execute_script.sh";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false))) {
            for (int i = start; i <= end; i++) {
                Pattern pattern = Pattern.compile("\\[\\d+\\]");
                Matcher matcher = pattern.matcher(text);
                String output = matcher.replaceAll(String.format("[%02d]", i));
                System.out.println(output);
                writer.write(output + "\n");
            }
            System.out.println("结果已写入: " + outputFile);
        } catch (IOException e) {
            System.err.println("写入文件时出错: " + e.getMessage());
        }
    }


}
