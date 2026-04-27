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

    public static final String PATH = "/Volumes/Extreme SSD/All/Lee/TVBAK/待处理/ccc (2014)/漫画家与助手";
    public static final String REPLACE_ORIGIN = "Style: Default,腾祥嘉丽中圆GB18030,34,&H00FFFFFF,&HF0000000,&H00400A22,&H0058281B,-1,0,0,0,100,100,0,0,1,2,0,2,30,30,10,1";
    public static final String REPLACE_AFTER = "Style: Default,腾祥嘉丽中圆GB18030,32,&H00FFFFFF,&HF0000000,&H00400A22,&H0058281B,-1,0,0,0,100,100,0,0,1,3.5,0.7,2,30,30,10,1";

    public static void main(String[] args) throws IOException {
        replaceTextAss(new File(PATH));
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
//                List<String> strings = FileUtil.readLines(assfile, "UTF-16LE");

                List<String> collect = strings.stream().map(item -> {
                    return item.replaceAll(REPLACE_ORIGIN, REPLACE_AFTER
                                    )

                            .replaceAll("Default JP", "efault JP-UP").replaceAll("\\{\\\\an8\\}", "");

                }).collect(Collectors.toList());

                FileUtil.writeUtf8Lines(collect, assfile);

            }
        }

    }
}