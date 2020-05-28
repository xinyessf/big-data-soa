package com.huanyu.hbase.demo.conn;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
 
import java.io.IOException;
 
/**
* @Description: hbase的javaAPI
* @Param:
* @return:
* @Author: hyc
* @Date: 2019-02-20
*/
 
public class HbaseDemo1 {
    /**
    * @Description: createTable():创建表的方法
    * @Param: 0
    * @return: 0
    */
 
    @Test
    public void createTable() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.73.128:2181");
        //建立连接
        Connection conn = ConnectionFactory.createConnection(conf);
        //获取表的管理类
        Admin admin = conn.getAdmin();
        //定义表
        HTableDescriptor hTableDescriptor=new HTableDescriptor(TableName.valueOf("person"));
        //定义列簇
        HColumnDescriptor hColumnDescriptor =new HColumnDescriptor("info");
        //讲列簇定义到表中
        hTableDescriptor.addFamily(hColumnDescriptor);
        //执行建表操作
        admin.createTable(hTableDescriptor);
        admin.close();
        conn.close();
 
 
    }
 
    /**
    * @Description: 向Hbase中插入数据的方法
    * @Param: null
    * @return: null
    */
 
    @Test
    public void put(){
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.73.128:2181");
 
        try {
            //建立连接
            Connection conn= ConnectionFactory.createConnection(conf);
            //获取表
            Table table=conn.getTable(TableName.valueOf("person"));
            //用行键实例化put
            Put put= new Put("rk001".getBytes());
            //指定列簇名，列名，和值
            put.addColumn("info".getBytes(),"name".getBytes(),"zhangsan".getBytes());
            table.put(put);
            table.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * @Description: get()从Hbase中读取数据的方法
    * @Param: 1
    * @return: 1
    */
    @Test
    public void get() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.73.128:2181");
        //建立连接
        Connection conn =ConnectionFactory.createConnection(conf);
        //获取表
        Table table = conn.getTable(TableName.valueOf("person"));
        //用行建实例化get
        Get get = new Get("rk001".getBytes());
        //增加列簇和列名条件
        get.addColumn("info".getBytes(),"name".getBytes());
        //执行，返回结果
        Result result = table.get(get);
        //取出结果
        String valString= Bytes.toString(result.getValue("info".getBytes(),"name".getBytes()));
        System.out.println(valString);
        //关闭连接
        table.close();
        conn.close();
    }
    /**
    * @Description: scan()查询一个表的所有信息
    * @Param: 1
    * @return: 1
    */
 
    @Test
    public  void scan() throws IOException {
        Configuration conf=HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.73.128:2181");
 
        //建立连接
        Connection conn=ConnectionFactory.createConnection(conf);
 
        //获取表
        Table table=conn.getTable(TableName.valueOf("person"));
 
 
        //初始化Scan实例
        Scan scan=new Scan();
 
        //增加过滤条件
        scan.addColumn("info".getBytes(), "name".getBytes());
        //返回结果
        ResultScanner rss=table.getScanner(scan);
        //迭代并取出结果
        for(Result rs:rss){
            String valStr=Bytes.toString(rs.getValue("info".getBytes(), "name".getBytes()));
            System.out.println(valStr);
        }
 
 
        //关闭连接
        table.close();
        conn.close();
 
    }
 
    /**
    * @Description: delete()删除表中的信息
    * @Param: 1
    * @return: 1
    */
 
    @Test
    public  void delete() throws IOException {
        Configuration conf=HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.73.128:2181");
 
        //建立连接
        Connection conn=ConnectionFactory.createConnection(conf);
 
        //获取表
        Table table=conn.getTable(TableName.valueOf("person"));
 
 
        // 用行键来实例化Delete实例
        Delete del = new Delete("rk0001".getBytes());
        // 执行删除
        table.delete(del);
 
 
        //关闭连接
        table.close();
        conn.close();
    }
}
 