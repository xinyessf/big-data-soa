package com.mvtech.mess.filter;

import com.google.common.base.Strings;
import com.mvtech.mess.constant.IsEnum;
import com.mvtech.mess.model.CmccSms;
import com.mvtech.mess.util.*;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.JSONObject;
import scala.Tuple2;
import scala.reflect.ClassTag;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * @author
 * @version 1.0
 * @date
 */
public class PushDataToKafkaLocal implements Serializable {

    private static Logger logger = Logger.getLogger(PushDataToKafkaLocal.class);

    /**
     * 测试广播的形式
     *
     * @param sparkSession
     * @param broadcast
     */
    public void pushDataToCMCCKafka(SparkSession sparkSession, Broadcast<DataProducer2> broadcast) {
        logger.info(PropUtils.TASK_DAY + "进入推送数据到移动准备:==== ");
        try {
            String querySQL = getQuerySQL(PropUtils.CMCC_QUERY_SQL);
            if (!Strings.isNullOrEmpty(querySQL)) {
                logger.info("移动sql " + querySQL);
                Dataset<Row> originalData = sparkSession.sql(querySQL);
                logger.info("开始打印数据===");
                //originalData.show();
                logger.info("结束打印数据===");
                Integer firstPushValue = IsEnum.Yes.getValue();
                JavaRDD<Row> rowJavaRDD = originalData.javaRDD();
                JavaRDD<Row> distinctRDD = rowJavaRDD.distinct();
                distinctRDD.foreach((VoidFunction<Row>) record -> {
                    String c_usernum = record.getString(0);
                    String c_relatenum = record.getString(1);
                    String c_time = record.getString(2);
                    String c_content = record.getString(3);
                    CmccSms cmccSms = new CmccSms(c_usernum, c_relatenum, c_time, c_content, firstPushValue);
                    String json = null;
                    try {
                        json = JsonUtils.serialize(cmccSms);
                        if (json != null && json != "") {
                            System.out.println(json);
                            //broadcast.getValue().sendData("cmccsmsqueue", json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("写移动json" + "错误 " + cmccSms.toString());
                    }
                });
                logger.info("==推送数据到移动结束:==== ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试本地单机批量的方式,基本上应该不行的,不可行
     * 将查询数据放入到缓存
     *
     * @param sparkSession
     * @param broadcast
     */
    public void pushDataToCMCCKafka1(SparkSession sparkSession, Broadcast<DataProducer2> broadcast) {
        //新线程处理
        logger.info(PropUtils.TASK_DAY + "进入推送数据到移动准备:==== ");
        String querySQL = getQuerySQL(PropUtils.CMCC_QUERY_SQL);
        if (!Strings.isNullOrEmpty(querySQL)) {
            logger.info("移动sql " + querySQL);
            sparkSession = SparkUtils.getSession();
            Dataset<Row> originalData = sparkSession.sql(querySQL);
            JavaRDD<Row> rowJavaRDD = originalData.javaRDD();
            JavaPairRDD<Row, Long> rowLongJavaPairRDD = rowJavaRDD.zipWithIndex();
            JavaPairRDD<Row, Long> cache = rowLongJavaPairRDD.cache();
            long size = cache.count();

            for (int i = 0; i <= size / 100000; i++) {
                //序列化
                int j = i;
                JavaPairRDD<Row, Long> data = cache.filter(new Function<Tuple2<Row, Long>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Row, Long> rowLongTuple2) throws Exception {
                        return rowLongTuple2._2 <= 100000 * (j + 1) && rowLongTuple2._2 >= 100000 * j;
                    }
                });
                List<Tuple2<Row, Long>> list = data.collect();
                for (Tuple2<Row, Long> rowLongTuple2 : list) {
                    Row row = rowLongTuple2._1;
                    long index = rowLongTuple2._2;
                    System.out.println("index :" + index);
                    System.out.println("短信" + row.getString(0));
                }
            }
            logger.info("==推送数据到移动结束:==== ");
        }
        SparkUtils.close(sparkSession);
    }


    /**
     * hive表中查询数据
     *
     * @param querySQL 查询sql语句
     * @author: sunsf
     * @date: 2020/5/22 11:56
     */
    private String getQuerySQL(String querySQL) {
        String day = PropUtils.TASK_DAY;
        if (!Strings.isNullOrEmpty(day)) {
            querySQL = querySQL.replaceAll("@day", day);
            logger.info("查询sql :" + querySQL);
            return querySQL;
        }
        return null;
    }

    public void pushDataToKafka() {
        SparkSession sparkSession = null;
        try {
            String flagTrue = IsEnum.Yes.getDescription();
            sparkSession = SparkUtils.getSession();
            // ClassTag<DataProducer2> tag = scala.reflect.ClassTag$.MODULE$.apply(DataProducer2.class);
            //Broadcast<DataProducer2> broadcast = sparkSession.sparkContext().broadcast(DataProducer2.getInstance(), tag);
            Broadcast<DataProducer2> broadcast = null;
            //电信
            if (flagTrue.equals(PropUtils.IS_OPEN_CTSS)) {
                // pushDataToCTCCKafka();
            }
            //联通
            if (flagTrue.equals(PropUtils.IS_OPEN_CUSS)) {
                // pushDataToCMCCKafka(sparkSession, broadcast);
            }
            //移动
            if (flagTrue.equals(PropUtils.IS_OPEN_CMSS)) {
                pushDataToCMCCKafka(sparkSession, broadcast);
            }
        } catch (Exception e) {
            logger.error("taskDay " + PropUtils.TASK_DAY);
            e.getMessage();
            e.printStackTrace();
        } finally {
            //记录本次任务的分区信息到文件中
            try {
                if (IsEnum.No.getDescription().equals(PropUtils.IS_MANUAL_TASK)) {
                    writeTimeToTaskJson();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SparkUtils.close(sparkSession);

        }
    }

    /**
     * 将下次要执行的日期写入
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/21 17:00
     */
    private void writeTimeToTaskJson() {
        //记录本次任务的分区信息到文件中
        Map<String, String> map = new HashMap<>(2);
        //    public static String addDay(String oldDateStr, Integer addDay, String dateFormat) {
        String taskDayAddDay = DateUtil.addDay(PropUtils.TASK_DAY, 1, DateUtil.YYYYMMDD);
        map.put("taskDay", taskDayAddDay);
        JSONObject nextTaskJson = new JSONObject(map);
        List<String> list = new ArrayList<>(1);
        list.add(nextTaskJson.toString());
        File file = new File(PropUtils.TASK_TIME);
        FileUtils.deleteFile(PropUtils.TASK_TIME);
        FileUtils.write2File(list, file);
    }


}
