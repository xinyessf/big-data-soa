package com.mvtech.mess.filter;

import com.google.common.base.Strings;
import com.mvtech.mess.constant.IsEnum;
import com.mvtech.mess.model.CtccSms;
import com.mvtech.mess.model.CuccSms;
import com.mvtech.mess.util.*;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date
 */
public class PushDataToKafka {

    private static Logger logger = Logger.getLogger(PushDataToKafka.class);

    /**
     * 推送电信到kafka
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/20 11:07
     */
    static int ctccFailCount = 0;

    public void pushDataToCTCCKafka() {
        //新线程处理
        //new Thread(() -> {
        logger.info(PropUtils.TASK_DAY + "进入推送数据到电信准备:==== ");
        SparkSession sparkSession = null;
        try {
            String querySQL = getQuerySQL(PropUtils.CTCC_QUERY_SQL);
            //PRODUCER = new KafkaProducer(props);
            if (!Strings.isNullOrEmpty(querySQL)) {
                Integer firstPushValue = IsEnum.Yes.getValue();
                Dataset<Row> originalData = sparkSession.sql(querySQL);
                Dataset<Row> repartition = originalData.repartition(15);
                JavaRDD<Row> rowJavaRDD = repartition.javaRDD();
                rowJavaRDD.foreach(row -> {
                    String c_usernum = row.getString(0);
                    String c_relatenum = row.getString(1);
                    String c_time = row.getString(2);
                    String c_content = row.getString(3);
                    String c_type = row.getString(4);
                    String c_keywords = row.getString(5);
                    //avro此处需要优化
                    CtccSms ctccSms = new CtccSms(c_usernum, c_relatenum, c_time, c_content, c_type, c_keywords, firstPushValue);
                    String json = null;
                    try {
                        json = JsonUtils.serialize(ctccSms);
                        if (json != null && json != "") {
                            //2.==写入到队列
                            System.out.println(ctccSms);
                            //PRODUCER.send(new ProducerRecord<String, String>(PropUtils.CTCC_QUEUE, json));
                            //logger.info("==电信写入json");
                            //successCount++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("电信写消息错误 " + ctccSms.toString());
                        ctccFailCount++;
                    }
                });
                logger.info("电信推送到kafka失败数量 " + ctccFailCount);
                logger.info("==推送数据到电信结束:==== ");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            SparkUtils.close(sparkSession);
        }
        //}).start();

    }

    /**
     * 推送联通数据到kafka
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/20 11:07
     */
    int cuccFailCount = 0;

    public void pushDataToCUCCKafka() {
        //新线程处理
        logger.info(PropUtils.TASK_DAY + "进入推送数据到联通准备:==== ");
        // new Thread(() -> {
        SparkSession sparkSession = null;
        try {
            String querySQL = getQuerySQL(PropUtils.CUCC_QUERY_SQL);
            //PRODUCER = new KafkaProducer(props);
            if (!Strings.isNullOrEmpty(querySQL)) {
                sparkSession = SparkUtils.getSession();
                Dataset<Row> originalData = sparkSession.sql(querySQL);
                //分区
                Dataset<Row> repartition = originalData.repartition(15);
                Integer firstPushValue = IsEnum.Yes.getValue();
                JavaRDD<Row> rowJavaRDD = repartition.javaRDD();
                //此处资源
                rowJavaRDD.foreach(row -> {
                    String c_usernum = row.getString(0);
                    String c_relatenum = row.getString(1);
                    String c_time = row.getString(2);
                    String c_content = row.getString(3);
                    Integer c_reason = row.getInt(4);
                    String c_id = row.getString(5);
                    String c_keywords = row.getString(6);
                    //avro此处需要优化
                    CuccSms cuccSms = new CuccSms(c_usernum, c_relatenum, c_time, c_content, c_reason, c_id, c_keywords, firstPushValue);
                    String json = null;
                    try {
                        json = JsonUtils.serialize(cuccSms);
                        if (json != null && json != "") {
                            //2.==写入到队列
                            System.out.println(cuccSms.toString());
                            // PRODUCER.send(new ProducerRecord<String, String>(PropUtils.CUCC_QUEUE, json));                                //logger.info("==联通写入json");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("写联通json错误 " + cuccSms.toString());
                        // cuccFailCount++;
                    }
                });
                logger.info("==推送数据到联通结束:==== ");
                logger.info(PropUtils.TASK_DAY + "移动推送到kafka失败数量 " + cuccFailCount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            SparkUtils.close(sparkSession);
        }
        // }).start();
    }


    /**
     * 推送移动数据到kafka
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/20 11:07
     */
    public void pushDataToCMCCKafka() {
        //新线程处理
        // new Thread(() -> {
        SparkSession sparkSession = null;
            logger.info(PropUtils.TASK_DAY + "进入推送数据到移动准备:==== ");
            String querySQL = getQuerySQL(PropUtils.CMCC_QUERY_SQL);
            if (!Strings.isNullOrEmpty(querySQL)) {
                logger.info("移动sql "+querySQL);
                sparkSession = SparkUtils.getSession();
                Dataset<Row> originalData = sparkSession.sql(querySQL);
                logger.info("开始打印数据===");
                originalData.show();
                logger.info("结束打印数据===");
                Integer firstPushValue = IsEnum.Yes.getValue();
                String queue = PropUtils.CMCC_QUEUE;
                JavaRDD<Row> rowJavaRDD = originalData.javaRDD();
                logger.info("======rowJavaRDD size "+rowJavaRDD.collect().size());
                logger.info("推送abcd start");
                DataProducer1.getInstance().sendData("cmccsmsqueue", "20200702 18:45 wai");
                logger.info("推送abcd end");
                logger.info("======rdd");
                rowJavaRDD.foreach((VoidFunction<Row>) record -> {
                    logger.info("开始RDD遍历");
                    logger.info("======DataProducer1.getInstance()======"+DataProducer1.getInstance());
                    DataProducer1.getInstance().sendData("cmccsmsqueue", "20200702 18:45 nei");
                    logger.info("结束RDD遍历");
                    /*String c_usernum = record.getString(0);
                    logger.info("c_usernum :" +c_usernum);
                    String c_relatenum = record.getString(1);
                    String c_time = record.getString(2);
                    String c_content = record.getString(3);
                    //avro此处需要优化
                    CmccSms cmccSms = new CmccSms(c_usernum, c_relatenum, c_time, c_content, firstPushValue);
                    String json = null;
                    try {
                        json = JsonUtils.serialize(cmccSms);
                        if (json != null && json != "") {
                            logger.info("移动推送数据==="+json);
                            DataProducer.getInstance().sendData(queue, json);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("写移动json" + "错误 " + cmccSms.toString());
                    }*/
                });
                logger.info("==推送数据到移动结束:==== ");
            }
            SparkUtils.close(sparkSession);

        // }).start();
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
        try {
            String flagTrue = IsEnum.Yes.getDescription();
            //电信
            if (flagTrue.equals(PropUtils.IS_OPEN_CTSS)) {
                // pushDataToCTCCKafka();
            }
            //联通
            if (flagTrue.equals(PropUtils.IS_OPEN_CUSS)) {
                // pushDataToCUCCKafka();
            }
            //移动
            if (flagTrue.equals(PropUtils.IS_OPEN_CMSS)) {
                pushDataToCMCCKafka();
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
            if (DataProducer.getInstance() != null) {
            }
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
