package com.hjfruit.plugin.domain.utils;

import java.io.*;

/**
 * @author xianping
 * @date 2022/7/12 13:40
 */
public class FileUtils {

    private static final int EOF = -1;

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private FileUtils() {
    }

    public static void mkdir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void forceDelete(String file) throws IOException {
        forceDelete(new File(file));
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else if (!file.delete()) {
            String message = "File " + file + " unable to be deleted.";
            throw new IOException(message);
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            cleanDirectory(directory);
            if (!directory.delete()) {
                String message = "Directory " + directory + " unable to be deleted.";
                throw new IOException(message);
            }
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        String message;
        if (!directory.exists()) {
            message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        } else if (!directory.isDirectory()) {
            message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        } else {
            IOException exception = null;
            File[] files = directory.listFiles();
            for (File file : files) {
                try {
                    forceDelete(file);
                } catch (IOException e) {
                    exception = e;
                }
            }
            if (null != exception) {
                throw exception;
            }
        }
    }

    public static String fileRead(String fileName) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (FileInputStream in = new FileInputStream(fileName)) {
            byte[] b = new byte[512];
            int count;
            while ((count = in.read(b)) > 0) {
                builder.append(new String(b, 0, count));
            }
            return builder.toString();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static void copyInputStreamToFile(InputStream source, File target) throws IOException {
        try (FileOutputStream output = openOutputStream(target)) {
            final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            int n;
            while (EOF != (n = source.read(bytes))) {
                output.write(bytes, 0, n);
            }
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
        return new FileOutputStream(file);
    }
}
