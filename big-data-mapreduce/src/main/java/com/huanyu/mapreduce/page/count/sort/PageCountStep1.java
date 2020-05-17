package com.huanyu.mapreduce.page.count.sort;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 网站访问进行统计
 *
 * @author: sunsf
 * @date: 2020/5/10 17:08
 */

public class PageCountStep1 {
    /**
     * map
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/10 17:09
     */
    public static class PageCountStep1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(" ");
            context.write(new Text(split[1]), new IntWritable(1));
        }

    }

    /**
     * reduce
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/10 17:09
     */
    public static class PageCountStep1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values,
                              Context context) throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable v : values) {
                count += v.get();
            }
            context.write(key, new IntWritable(count));

        }
    }

    public static void main(String[] args) throws Exception {
        /**
         * 通过加载classpath下的*-site.xml文件解析参数
         */
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        job.setJarByClass(PageCountStep1.class);

        job.setMapperClass(PageCountStep1Mapper.class);
        job.setReducerClass(PageCountStep1Reducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path("F:\\wordcount\\url\\input"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\wordcount\\url\\output"));

        job.setNumReduceTasks(3);

        job.waitForCompletion(true);

    }


}
