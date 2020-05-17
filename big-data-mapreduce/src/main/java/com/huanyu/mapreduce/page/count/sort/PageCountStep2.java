package com.huanyu.mapreduce.page.count.sort;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 对output的进行的排序处理
 * @author: sunsf
 * @date: 2020/5/10 17:07
 */


public class PageCountStep2 {
	
	
	public static class PageCountStep2Mapper extends Mapper<LongWritable, Text, PageCount, NullWritable>{
		
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			PageCount pageCount = new PageCount();
			pageCount.set(split[0], Integer.parseInt(split[1]));
			context.write(pageCount, NullWritable.get());
		}
		
	}
	
	
	public static class PageCountStep2Reducer extends Reducer<PageCount, NullWritable, PageCount, NullWritable>{
		
		
		@Override
		protected void reduce(PageCount key, Iterable<NullWritable> values,
				Context context)
				throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
		
		
	}
	
	
public static void main(String[] args) throws Exception {

	Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf);

		job.setJarByClass(PageCountStep2.class);

		job.setMapperClass(PageCountStep2Mapper.class);
		job.setReducerClass(PageCountStep2Reducer.class);

		job.setMapOutputKeyClass(PageCount.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(PageCount.class);
		job.setOutputValueClass(NullWritable.class);

		FileInputFormat.setInputPaths(job, new Path("F:\\wordcount\\url\\output"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\wordcount\\url\\output2"));

		job.setNumReduceTasks(1);
		
		job.waitForCompletion(true);
		
	}
	
	

}
