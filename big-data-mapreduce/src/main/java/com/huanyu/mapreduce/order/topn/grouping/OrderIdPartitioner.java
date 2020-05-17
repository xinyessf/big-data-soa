package com.huanyu.mapreduce.order.topn.grouping;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderIdPartitioner extends Partitioner<OrderBean, NullWritable>{

	@Override
	public int getPartition(OrderBean key, NullWritable value, int numPartitions) {
		// 按照订单中的orderid来分发数据
		return (key.getOrderId().hashCode() & Integer.MAX_VALUE) % numPartitions;
	}

}
