package cn.huanyu.kafka.demo;

import java.io.*;

public class TestFile {
    public TestFile() {
    }

    public static boolean write(String cont, File dist) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dist));
            writer.write(cont);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) throws Exception {
        String content = ",18338698381,2020-05-20 12:13:10,【充值提醒】尊敬的客户l感谢您的充值l请关注您的余额变化";
        BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\wordcount\\cmcc\\2.txt"));
        for (int i = 0; i < 15000000; i++) {
            String ab = i + content + "\r\n";
            writer.write(ab);
        }
        writer.flush();
        writer.close();

    }


}
