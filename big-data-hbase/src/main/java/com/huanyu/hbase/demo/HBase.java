package com.huanyu.hbase.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * HBase 1.2.4
 *
 * @author：
 * @date 2017年7月12日 下午2:17:44
 */
public class HBase {

    public static Configuration conf;
    public static Connection connection;
    public static Admin admin;

    public static void main(String[] args) throws IOException {

        conf = HBaseConfiguration.create();
        conf.set("hbase.master", "192.168.73.134:16000");
        conf.set("hbase.zookeeper.quorum", "192.168.73.128");

        conf.set("hbase.zookeeper.property.clientPort", "2181");
        connection = ConnectionFactory.createConnection(conf);
        admin = connection.getAdmin();

        HTableDescriptor table = new HTableDescriptor(TableName.valueOf("table1"));
        table.addFamily(new HColumnDescriptor("group1")); //创建表时至少加入一个列组

        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

}