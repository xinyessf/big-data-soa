package com.huanyu.mapreduce.order.topn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

public class OrderTopn {
	
	public static class OrderTopnMapper extends Mapper<LongWritable, Text, Text, OrderBean>{
		OrderBean orderBean = new OrderBean();
		Text k = new Text();
		
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] fields = value.toString().split(",");
			
			orderBean.set(fields[0], fields[1], fields[2], Float.parseFloat(fields[3]), Integer.parseInt(fields[4]));
			k.set(fields[0]);
			
			// 从这里交给maptask的kv对象，会被maptask序列化后存储，所以不用担心覆盖的问题
			context.write(k, orderBean);
			
		}
		
	}
	
	
	public static class OrderTopnReducer extends Reducer<Text, OrderBean, OrderBean, NullWritable>{
		
		@Override
		protected void reduce(Text key, Iterable<OrderBean> values,
				Context context)
				throws IOException, InterruptedException {
			
			// 获取topn的参数
			int topn = context.getConfiguration().getInt("order.top.n",3);
			
			ArrayList<OrderBean> beanList = new ArrayList<>();
			
			// reduce task提供的values迭代器，每次迭代返回给我们的都是同一个对象，只是set了不同的值
			for (OrderBean orderBean : values) {
				
				// 构造一个新的对象，来存储本次迭代出来的值
				OrderBean newBean = new OrderBean();
				newBean.set(orderBean.getOrderId(), orderBean.getUserId(), orderBean.getPdtName(), orderBean.getPrice(), orderBean.getNumber());
				
				beanList.add(newBean);
			}
			
			
			// 对beanList中的orderBean对象排序（按总金额大小倒序排序,如果总金额相同，则比商品名称）
			Collections.sort(beanList);
			
			for (int i=0;i<topn;i++) {
				context.write(beanList.get(i), NullWritable.get());
			}
			
		}
	}
	
	
	public static void main(String[] args) throws Exception {

		
		Configuration conf = new Configuration(); // 默认只加载core-default.xml core-site.xml
		conf.setInt("order.top.n", 2);
		
		Job job = Job.getInstance(conf);

		job.setJarByClass(OrderTopn.class);

		job.setMapperClass(OrderTopnMapper.class);
		job.setReducerClass(OrderTopnReducer.class);
		
		job.setNumReduceTasks(2);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(OrderBean.class);
		
		job.setOutputKeyClass(OrderBean.class);
		job.setOutputValueClass(NullWritable.class);

		FileInputFormat.setInputPaths(job, new Path("F:\\mrdata\\order\\input"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\mrdata\\order\\out1"));

		job.waitForCompletion(true);
	}
}
