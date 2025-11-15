package org.mkv;

import cn.hutool.core.io.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DeleteAss {


    public static void main(String[] args) throws IOException {
        String path = "/Users/litian/Documents/Colorful/my-own-creation/tiny-media-manager/daram/一拳超人";
        deleteAss(new File(path));
//        replaceTextAss(new File("/Users/litian/Downloads/Download/Quark/机动战士高达 SEED HD重制版 [POPGO&MAI] [Ma10p_2160p]/字幕备份_副本"));
    }


    public static void deleteAss(File assfile) throws IOException {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                deleteAss(file);
            }
        } else {

            if ((assfile.getName().endsWith(".ass") &&
                    (assfile.getName().endsWith("cht.zh.ass")
                    || assfile.getName().endsWith("jptc.zh.ass")
                    || assfile.getName().endsWith("JPTC.zh.ass")
                    || assfile.getName().endsWith(".JPTC.ass")
                            || assfile.getName().endsWith("(1).zh.ass")
                    )) || assfile.getName().startsWith("._")) {
                System.out.println(assfile.getName());
                assfile.delete();
            }
        }

    }

}
