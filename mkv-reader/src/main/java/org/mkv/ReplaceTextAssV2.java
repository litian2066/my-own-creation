package org.mkv;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplaceTextAssV2 {

    public static void main(String[] args) throws IOException {
        replaceTextAss(new File("/Users/litian/Downloads/Download/Quark/刃牙 [MAI] [Ma10p_2160p]/1.刃牙 死囚篇 [MAI] [Ma10p_2160p]"));

    }


    public static void appendTextAss(File assfile, List<String> list) throws IOException {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                appendTextAss(file, list);
            }
        } else {
            System.out.println(assfile.getName());
            if (assfile.getName().endsWith(".ass") && !assfile.getName().startsWith("._")) {
                List<String> strings = FileUtil.readLines(assfile, Charset.defaultCharset());
                for (int i = 0; i < strings.size(); i++) {
                    String item = strings.get(i);
                    list.add(item);
                }

            }
        }

    }




    public static void appendTextAssV2(File assfile, List<String> afterPat) throws IOException {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                appendTextAssV2(file, afterPat);
            }
        } else {
            if (assfile.getName().endsWith(".ass") && !assfile.getName().startsWith("._")) {
                List<String> strings = FileUtil.readLines(assfile, Charset.defaultCharset());

                List<String> collect = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    String item = strings.get(i);
                    collect.add(item + "\\N{\\rEng}" + afterPat.get(i));
                }
                collect.forEach(System.out::println);
                FileUtil.writeUtf8Lines(collect, assfile);
            }
        }

    }

    public static String removeBrackets(String str) {
        // 先去除[]及其内容
//        String temp = str.replaceAll("\\[[^\\]]*\\]", "");
//        // 再去除]后面的第一个空格（如果有）
//        System.out.println(temp);
//        return temp.replaceAll("\\]\\s", "]");
        // 第一步：将]后的多个空格规范化为单个空格
        String step1 = str.replaceAll("\\]\\s+", "] ");
        // 第二步：去除[]及其内容
        return step1.replaceAll("\\[[^\\]]*\\]", "");
    }

    public static void replaceTextAss(File assfile) throws IOException {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                replaceTextAss(file);
            }
        } else {
            System.out.println(assfile.getName());
            if (assfile.getName().endsWith(".ass")) {
                List<String> strings = FileUtil.readLines(assfile, Charset.defaultCharset());

                List<String> collect = strings.stream().map(item -> {
                    System.out.println(item);
                    return item
                            .replaceAll("Style: Default,Arial,20,&H00FFFFFF,&H000000FF,&H00000000,&H00000000,0,0,0,0,100,100,0,0,1,2,2,2,10,10,10,1",
                            "Style: Default,SourceHanSansSC-Heavy,77.6,&H00FFFFFF,&H000000FF,&H00404040,&H00000000,0,0,0,0,100,100,0,0,1,3,0,2,72,72,54,1")

                            .replaceAll("ScaledBorderAndShadow: yes",
                                    "PlayResX: 1920\n" +
                                            "PlayResY: 1080\n" +
                                            "ScaledBorderAndShadow: yes");
                }).collect(Collectors.toList());

                FileUtil.writeUtf8Lines(collect, assfile);

            }
        }

    }
}