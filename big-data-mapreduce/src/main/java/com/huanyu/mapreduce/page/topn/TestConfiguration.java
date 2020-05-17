package com.huanyu.mapreduce.page.topn;

import org.apache.hadoop.conf.Configuration;

public class TestConfiguration {
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.addResource("xx-oo.xml");
		
		System.out.println(conf.get("top.n"));
		System.out.println(conf.get("mygirlfriend"));
	}

}
