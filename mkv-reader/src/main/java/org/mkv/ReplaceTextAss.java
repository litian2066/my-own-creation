package org.mkv;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReplaceTextAss {

    public static void main(String[] args) throws IOException {
//        List<String> objects = new ArrayList<>();
//        appendTextAss(new File("/Volumes/Extreme SSD/李天/HDR/更好的人/test2/eng.ass"), objects);
//        appendTextAssV2(new File("/Volumes/Extreme SSD/李天/HDR/更好的人/test2/chs.ass"), objects);
        replaceTextAss(new File("/Users/litian/Downloads/Download/Quark/为美好的世界献上祝福!(含外传 爆炎) [DHR&Sakurato&MAI] [Ma10p_2160p]/外传 为美好的世界献上爆焰！[Sakurato&KitaujiSub&MingY&MAI] [Ma10p_2160p]/字幕备份"));

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
                List<String> strings = FileUtil.readLines(assfile, "UTF-8");

                List<String> collect = strings.stream().map(item -> {
                    System.out.println(item);
                    return item
                            .replaceAll("Style: CN,LXGW WenKai,86,&H00FFFFFF,&H000000FF,&H00464646,&H14202020,0,0,0,0,100,100,2,0,1,2.3,0,2,20,20,43,1",
                                    "Style: CN,LXGW WenKai,80,&H00FFFFFF,&H000000FF,&H00464646,&H14202020,0,0,0,0,100,100,2,0,1,2.3,0,2,20,20,43,1")
                            .replaceAll("Style: JP,FOT-Klee Pro DB,53,&H00FFFFFF,&HF0000000,&H00464646,&H64FFFFFF,-1,0,0,0,100,100,2,0,1,2.3,0,2,20,20,0,128",
                                    "Style: JP,FOT-Klee Pro DB,45,&H00FFFFFF,&HF0000000,&H00464646,&H64FFFFFF,-1,0,0,0,100,100,2,0,1,2.3,0,2,20,20,5,128");

                }).collect(Collectors.toList());

                FileUtil.writeUtf8Lines(collect, assfile);

            }
        }

    }
}