package com.huanyu.mapreduce.flow;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
/**
 * 序列化测试
 * @author: sunsf
 * @date: 2020/5/10 12:21
 */
public class DataOutputstreamTest {
	
	public static void main(String[] args) throws Exception {
		
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("d:/a.dat"));
		
		dos.write("我爱你".getBytes("utf-8"));
		
		dos.close();
		
		
		DataOutputStream dos2 = new DataOutputStream(new FileOutputStream("d:/b.dat"));
		
		dos2.writeUTF("我爱你");
		
		dos2.close();
	}

}
