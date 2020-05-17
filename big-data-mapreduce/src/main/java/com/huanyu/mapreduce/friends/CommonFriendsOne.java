package com.huanyu.mapreduce.friends;

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


public class CommonFriendsOne {
	
	public static class CommonFriendsOneMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text k = new Text();
		Text v = new Text();
		
		//A:B,C,D,F,E,O
		// 输出：　B->A  C->A  D->A ...
		
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] userAndFriends = value.toString().split(":");
			
			String user = userAndFriends[0];
			String[] friends = userAndFriends[1].split(",");
			v.set(user);
			for (String f : friends) {
				k.set(f);
				context.write(k, v);
			}
			
			
		}
		
		
		
	}
	
	
	
	public static class CommonFriendsOneReducer extends Reducer<Text, Text, Text, Text>{
		
		// 一组数据：  B -->  A  E  F  J .....
		// 一组数据：  C -->  B  F  E  J .....
		@Override
		protected void reduce(Text friend, Iterable<Text> users, Context context)
				throws IOException, InterruptedException {
			
			ArrayList<String> userList = new ArrayList<>();
			
			for(Text user: users){
				userList.add(user.toString());
			}
			
			Collections.sort(userList);
			
			for(int i=0;i<userList.size()-1;i++){
				for(int j=i+1;j<userList.size();j++){
					context.write(new Text(userList.get(i)+"-"+userList.get(j)), friend);
				}
			}
			
		}
		
	}
	
	
public static void main(String[] args) throws Exception {

		
		Configuration conf = new Configuration();  
		
		Job job = Job.getInstance(conf);

		job.setJarByClass(CommonFriendsOne.class);

		job.setMapperClass(CommonFriendsOneMapper.class);
		job.setReducerClass(CommonFriendsOneReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path("F:\\mrdata\\friends\\input"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\mrdata\\friends\\out1"));

		job.waitForCompletion(true);
	}
}
