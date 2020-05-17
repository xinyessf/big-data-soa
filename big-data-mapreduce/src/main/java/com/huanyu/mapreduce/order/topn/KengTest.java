package com.huanyu.mapreduce.order.topn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class KengTest {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//ArrayList<OrderBean> beans = new ArrayList<>();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:/keng.dat", true));
		
		OrderBean bean = new OrderBean();
		
		bean.set("1", "u", "a", 1.0f, 2);
		//beans.add(bean);
		oos.writeObject(bean);
		
		
		
		
		bean.set("2", "t", "b", 2.0f, 3);
		//beans.add(bean);
		oos.writeObject(bean);
		
		
		bean.set("3", "r", "c", 2.0f, 3);
		//beans.add(bean);
		oos.writeObject(bean);
		
		
		//System.out.println(beans);
		oos.close();
		
		
	}

}
