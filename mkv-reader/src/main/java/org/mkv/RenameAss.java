package org.mkv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RenameAss {

    public static void main(String[] args) throws IOException {
        String path = "/Volumes/Extreme SSD/All/Lee/TV/全缉毒狂潮 (2019)";
        addZhCNTextAss2(new File(path));
//        addZhCNTextAss3(new File(path));
    }


    public static void addZhCNTextAss2(File assfile) {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                addZhCNTextAss2(file);
            }
        } else {
            if (!assfile.getName().startsWith("._") && (assfile.getName().endsWith(".ass")
                    || assfile.getName().endsWith(".mkv")
                    || assfile.getName().endsWith(".mp4")
                    || assfile.getName().endsWith(".srt")

            )) {
                // 重命名
                String prefix = FileNameUtil.getPrefix(assfile);
                int endIndex = prefix.lastIndexOf("-") - 1;
                prefix = prefix.substring(0, endIndex);
                int eIndex = prefix.indexOf("E") + 1;
                String prefixString = prefix.substring(0, eIndex);

                String episode = prefix.substring(eIndex, endIndex);

                prefix = prefixString + "[" + episode  + "]." +  FileNameUtil.getSuffix(assfile);
                System.out.println(prefix);
                FileUtil.rename(assfile, prefix, true);

            }
        }

    }



    public static void addZhCNTextAss3(File assfile) {
        if (assfile.isDirectory()) {
            File[] files = assfile.listFiles();
            for (File file : files) {
                addZhCNTextAss3(file);
            }
        } else {
            if (!assfile.getName().startsWith("._") && (assfile.getName().endsWith(".ass"))) {
                // 重命名
                String prefix = FileNameUtil.getPrefix(assfile);
                int startIndex = prefix.lastIndexOf("[") - 1;
                int endIndex = prefix.lastIndexOf("]");
                prefix = prefix.substring(0, startIndex) + prefix.substring(endIndex);



                prefix = prefix + "." + FileNameUtil.getSuffix(assfile);
                System.out.println(prefix);
                FileUtil.rename(assfile, prefix, true);

            }
        }

    }


}
