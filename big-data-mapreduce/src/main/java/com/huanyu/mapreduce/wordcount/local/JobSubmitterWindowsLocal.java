package com.huanyu.mapreduce.wordcount.local;

import com.huanyu.mapreduce.wordcount.WordcountMapper;
import com.huanyu.mapreduce.wordcount.WordcountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 本地测试mapduce,需要本地环境变量配置hadoop/bin
 * @author: sunsf
 * @date: 2020/5/10 0:05
 */

public class JobSubmitterWindowsLocal {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		//conf.set("fs.defaultFS", "file:///");
		//conf.set("mapreduce.framework.name", "local");

		Job job = Job.getInstance(conf);
		
		job.setJarByClass(JobSubmitterWindowsLocal.class);
		
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("f:/wordcount/input"));
		FileOutputFormat.setOutputPath(job, new Path("f:/wordcount/output1"));
		
		job.setNumReduceTasks(3);
		
		boolean res = job.waitForCompletion(true);
		System.exit(res?0:1);
		
	}
	



}
