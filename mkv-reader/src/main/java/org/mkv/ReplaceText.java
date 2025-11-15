package org.mkv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceText {

    private final static String text = "/Applications/MKVToolNix-88.0.app/Contents/MacOS/mkvmerge --ui-language en_US --priority lower --output '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/after/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [01][Ma10p_1080p][x265_flac].mkv' --language 0:und --display-dimensions 0:1920x1080 --language 1:ja '(' '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [01][Ma10p_1080p][x265_flac].mkv' ')' --language 0:zh --track-name '0:hyakuhuyu.简中' '(' '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [01][Ma10p_1080p][x265_flac].sc.zh.ass' ')' --language 0:zh --track-name '0:hyakuhuyu.繁中' --default-track-flag 0:no '(' '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [01][Ma10p_1080p][x265_flac].tc.zh.ass' ')' --attachment-name '华康手札体W5-A.ttf' --attachment-mime-type font/ttf --attach-file '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [Fonts]/华康手札体W5-A.ttf' --attachment-name ARDCQingYuanB5-BD.otf --attachment-mime-type font/otf --attach-file '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [Fonts]/ARDCQingYuanB5-BD.otf' --attachment-name FOT-HummingProN-D.otf --attachment-mime-type font/otf --attach-file '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [Fonts]/FOT-HummingProN-D.otf' --attachment-name FZFWQingYinTiJWD.TTF --attachment-mime-type font/ttf --attach-file '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [Fonts]/FZFWQingYinTiJWD.TTF' --attachment-name FZLanTYK_Cu.TTF --attachment-mime-type font/ttf --attach-file '/Users/litian/Downloads/Download/Xunlei/BanG Dream! Morfonication 10-bit 1080p HEVC BDRip [OVAs]/[hyakuhuyu&VCB-Studio] BanG Dream! Morfonication [Fonts]/FZLanTYK_Cu.TTF' --track-order 0:0,0:1,1:0,2:0";
    private final static int start =2;
    private final static int end = 2;

    public static void main(String[] args) throws IOException {
        StringReplacer();
//        replaceTextAss(new File("/Users/litian/Downloads/Download/Quark/机动战士高达 SEED HD重制版 [POPGO&MAI] [Ma10p_2160p]/字幕备份_副本"));
    }


    public static void StringReplacer() throws IOException {

        String outputFile = "/Users/litian/Documents/Colorful/my-own-creation/github/my-own-creation/mkv-reader/src/main/java/org/mkv/execute_script.sh";
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
