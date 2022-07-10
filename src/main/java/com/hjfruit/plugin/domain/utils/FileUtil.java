package com.hjfruit.plugin.domain.utils;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author xianping
 * 2022/7/1019:21
 */
public class FileUtil {

    private FileUtil() {
    }

    public static void del(File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mkdir(String path) {
        final File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String readString(File file) {
        String encoding = "UTF-8";
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(fileContent);
            return new String(fileContent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}
