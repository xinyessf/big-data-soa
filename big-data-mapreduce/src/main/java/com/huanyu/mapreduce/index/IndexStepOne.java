package com.huanyu.mapreduce.index;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 *
 * @author: sunsf
 * @date: 2020/5/10 17:01
 */

public class IndexStepOne {

	public static class IndexStepOneMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		// 产生 <hello-文件名，1> 
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// 从输入切片信息中获取当前正在处理的一行数据所属的文件
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			String fileName = inputSplit.getPath().getName();

			String[] words = value.toString().split(" ");
			for (String w : words) {
				// 将"单词-文件名"作为key，1作为value，输出
				context.write(new Text(w + "-" + fileName), new IntWritable(1));
			}

		}

	}

	public static class IndexStepOneReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context)
				throws IOException, InterruptedException {

			int count = 0;
			for (IntWritable value : values) {
				count += value.get();
			}

			context.write(key, new IntWritable(count));

		}

	}
	
	
	
	public static void main(String[] args) throws Exception{
		
		Configuration conf = new Configuration(); // 默认只加载core-default.xml core-site.xml
		System.out.println(conf.get("hdfs.p"));
		System.out.println(conf.get("yy.p"));
		
		Job job = Job.getInstance(conf);

		job.setJarByClass(IndexStepOne.class);

		job.setMapperClass(IndexStepOneMapper.class);
		job.setReducerClass(IndexStepOneReducer.class);

		job.setNumReduceTasks(3);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.setInputPaths(job, new Path("F:\\wordcount\\index\\input"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\wordcount\\index\\"+System.currentTimeMillis()));

		job.waitForCompletion(true);
		
	}
	

}
