package com.huanyu.mapreduce.page.topn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 统计访问前几的网站
 * @author: sunsf
 * @date: 2020/5/10 12:51
 */


public class JobSubmitter {

	public static void main(String[] args) throws Exception {

		/**
		 * 通过加载classpath下的*-site.xml文件解析参数
		 */
		Configuration conf = new Configuration();
		conf.addResource("xx-oo.xml");
		
		/**
		 * 通过代码设置参数
		 */
		//conf.setInt("top.n", 3);
		//conf.setInt("top.n", Integer.parseInt(args[0]));
		
		/**
		 * 通过属性配置文件获取参数
		 */
		/*Properties props = new Properties();
		props.load(JobSubmitter.class.getClassLoader().getResourceAsStream("topn.properties"));
		conf.setInt("top.n", Integer.parseInt(props.getProperty("top.n")));*/
		
		Job job = Job.getInstance(conf);

		job.setJarByClass(JobSubmitter.class);

		job.setMapperClass(PageTopnMapper.class);
		job.setReducerClass(PageTopnReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.setInputPaths(job, new Path("F:\\wordcount\\url\\input"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\wordcount\\url\\output6"));

		job.waitForCompletion(true);

	}



}
