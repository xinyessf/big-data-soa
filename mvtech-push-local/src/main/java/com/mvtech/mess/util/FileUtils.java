package com.mvtech.mess.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文件处理
 *
 * @author: sunsf
 * @date: 2020/5/20 10:45
 */

public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * 方法描述：写文件
     */
    public static boolean write2File(InputStream in, String dir, String fileName) {
        boolean flag = false;
        if (null == in || null == dir || "".equals(dir)) {
            System.out.println("文件内容或者文件存储路径出错！" + dir);
            return flag;
        }
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
            System.out.println("创建目录{}成功！" + dir);
        }
        File file = new File(dir + File.separator + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("创建文件{}成功！" + fileName);
            } catch (IOException e) {
                System.out.println("创建文件{}失败！" + fileName + e.fillInStackTrace());
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            flag = true;
        } catch (FileNotFoundException e) {
            System.out.println("文件{}不存在！" + dir + File.separator + fileName + e.fillInStackTrace());
        } catch (IOException e) {
            System.out.println("文件写入IO失败！文件为{}" + dir + File.separator + fileName + e.fillInStackTrace());
        } finally {
            try {
                close(out, in);
            } catch (IOException e) {
                System.out.println("关闭文件流失败！{}" + dir + File.separator + fileName + e.fillInStackTrace());
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 方法描述：写文件
     */
    public static void write2File(List<String> strs, File file) {
        FileOutputStream fileOut = null;
        OutputStreamWriter outWrite = null;
        BufferedWriter bufferWrite = null;
        try {
            if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            fileOut = new FileOutputStream(file, false);
            outWrite = new OutputStreamWriter(fileOut, "UTF-8");
            bufferWrite = new BufferedWriter(outWrite);
            for (String str : strs) {
                bufferWrite.write(str + "\r\n");
            }
        } catch (FileNotFoundException e) {
            logger.info("file is not exist {}" + file.getAbsolutePath() + e.fillInStackTrace());
        } catch (UnsupportedEncodingException e) {
            logger.info("write UnsupportedEncodingException " + file.getAbsolutePath() + e.fillInStackTrace());
        } catch (IOException e) {
            logger.info("write IOException " + file.getAbsolutePath() + e.fillInStackTrace());
        } finally {
            try {
                close(bufferWrite, outWrite, fileOut);
            } catch (IOException e) {
                logger.info("close error...");
            }
        }
    }

    /**
     * 方法描述：读文件
     */
    public static String readFile(String path) {
        String result = null;
        if (null == path) {
            System.out.println("文件内容或者文件存储路径出错！{}" + path);
            return result;
        }
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return result;
        }
        FileInputStream fileIn = null;
        BufferedInputStream bufIn = null;
        ByteArrayOutputStream out = null;
        try {
            fileIn = new FileInputStream(file);
            bufIn = new BufferedInputStream(fileIn);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = bufIn.read(b)) != -1) {
                out.write(b, 0, len);
            }
            result = out.toString();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在！{}" + path + e.fillInStackTrace());
        } catch (IOException e) {
            System.out.println("文件读取中IO流错误！{}" + path + e.fillInStackTrace());
        } finally {
            try {
                close(out, bufIn, fileIn);
            } catch (IOException e) {
                System.out.println("文件关闭 中IO流错误！{}" + path + e.fillInStackTrace());
            }
        }
        return result;
    }

    /**
     * 方法描述：读文件
     */
    public static List<String> readFile4List(String path) {
        List<String> results = new ArrayList<>();
        FileInputStream fileIn = null;
        InputStreamReader inStrRe = null;
        BufferedReader bufferRe = null;
        try {
            fileIn = new FileInputStream(path);
            inStrRe = new InputStreamReader(fileIn, "UTF-8");
            bufferRe = new BufferedReader(inStrRe);
            String line;
            while ((line = bufferRe.readLine()) != null) {
                if (null != line && !"".equals(line)) {
                    results.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException..." + e.fillInStackTrace());
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException..." + e.fillInStackTrace());
        } catch (IOException e) {
            System.out.println("IOException..." + e.fillInStackTrace());
        } finally {
            try {
                FileUtils.close(bufferRe, inStrRe, fileIn);
            } catch (Exception e) {
                System.out.println("read file io error...." + e.fillInStackTrace());
            }
        }
        return results;
    }


    public static Map<String, String> readFile4Map(String path) {
        Map<String, String> results = new HashMap<>();
        FileInputStream fileIn = null;
        InputStreamReader inStrRe = null;
        BufferedReader bufferRe = null;
        try {
            fileIn = new FileInputStream(path);
            inStrRe = new InputStreamReader(fileIn, "UTF-8");
            bufferRe = new BufferedReader(inStrRe);
            String line;
            while ((line = bufferRe.readLine()) != null) {
                formatData(line, results);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException..." + e.fillInStackTrace());
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException..." + e.fillInStackTrace());
        } catch (IOException e) {
            System.out.println("IOException..." + e.fillInStackTrace());
        } finally {
            try {
                FileUtils.close(bufferRe, inStrRe, fileIn);
            } catch (Exception e) {
                System.out.println("read file io error...." + e.fillInStackTrace());
            }
        }
        return results;
    }

    public static void formatData(String line, Map<String, String> results) {
        if (line != null && !"".equals(line)) {
            String[] columns = line.split(",");
            long time = 0L;
            if (columns.length == 6) {
                String phone = columns[0];
                String province = columns[1];
                String city = columns[2];
                String fraudType = columns[5];
                if (phone != null & !"".equals(phone)) {
                    if (!phone.startsWith("86")) {
                        phone = 86 + columns[0];
                    }
                } else {
                    return;
                }
                try {
                    time = stringToLong(String.valueOf(columns[3]), "yyyy/MM/dd HH:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                results.put(phone, phone + "," + province + "," + city + "," + (time / 1000) + "," + fraudType);
            }
        }
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 方法描述：删除文件
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.exists()) {
            flag = file.delete();
            System.out.println("delete file {} success...." + filePath);
        } else {
            System.out.println("delete file {} failed...." + filePath);
        }
        return flag;
    }

    /**
     * 方法描述：删除目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 方法描述：文件重命名
     */
    public static boolean renameFile(String oldPath, String newPath) {
        boolean flag = false;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (oldFile.exists()) {
            if (newFile.exists()) {
                System.out.println("重命名失败，重命名文件已存在！");
            } else {
                flag = oldFile.renameTo(newFile);
            }
        } else {
            System.out.println("重命名失败，源文件不存在！");
        }
        return flag;
    }


    /**
     * 方法描述：复制文件
     */
    public static boolean copy(String src, String target, boolean keep) {
        boolean flag = false;
        File srcFile = new File(src);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isFile()) {
            return false;
        }
        // 判断目标文件是否存在
        File targetFile = new File(target);
        if (targetFile.exists()) {
            return false;
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!targetFile.getParentFile().exists()) {
                if (!targetFile.getParentFile().mkdirs()) {
                    System.out.println("mkdirs {} failed...... " + targetFile.getParentFile());
                }
            }
            // 复制文件
            FileChannel in = null;
            FileChannel out = null;
            FileInputStream inStream = null;
            FileOutputStream outStream = null;

            try {
                inStream = new FileInputStream(src);
                outStream = new FileOutputStream(target);
                in = inStream.getChannel();
                out = outStream.getChannel();
                in.transferTo(0, in.size(), out);
                System.out.println("copy file success..." + src + " to " + target);

                flag = true;
            } catch (IOException e) {
                System.out.println("copy  {}  to {} failed..." + src + target + e.fillInStackTrace());
            } finally {
                try {
                    close(inStream, in, outStream, out);
                } catch (IOException e) {
                    System.out.println("copy file io error...." + e.fillInStackTrace());
                }
            }
        }
        if (keep) {
            targetFile.setLastModified(srcFile.lastModified());
        }
        return flag;
    }

    /**
     * 方法描述：关闭流
     */
    public static void close(Closeable... ios) throws IOException {
        for (Closeable io : ios) {
            if (null != io) {
                io.close();
            } else {
                System.out.println("io is null ");
            }
        }
    }

}
