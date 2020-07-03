package com.mvtech.mess.util;

import com.mvtech.mess.constant.IsEnum;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 读取配置文件工具类
 *
 * @author sunsf
 */
public class PropUtils {

    private static Logger logger = Logger.getLogger(PropUtils.class);

    private static Properties PROP_K_V;

    private static long UPDATE_TIME;

    private static final Lock REEN_LOCK = new ReentrantLock();
    //private static String path = "E:\\weizhi\\module-jsfz-soa\\mess-push-day\\src\\main\\resources\\mess.properties";
    private static String path = "/home/mx_projects/mvtech_admin/iaf/pro_files/mvtech_mess/mess.properties";
    //kafka队列
    public static String CMCC_QUEUE = null;
    public static String CUCC_QUEUE = null;
    public static String CTCC_QUEUE = null;
    //3大运营商hive sql
    public static String CMCC_QUERY_SQL = null;
    public static String CUCC_QUERY_SQL = null;
    public static String CTCC_QUERY_SQL = null;
    //
    public static String IS_MANUAL_TASK = null;
    public static String TASK_TIME = null;
    //SQL 查询的区间
    public static String TASK_DAY = null;
    //kafka ip
    public static String KAFKA_LIST = null;

    // 是否开启 跑数据
    public static String IS_OPEN_CMSS = null;
    public static String IS_OPEN_CUSS = null;
    public static String IS_OPEN_CTSS = null;

    // kafka用户名 密码
    public static String KAFKA_SECURITY_USERNAME = null;

    public static String KAFKA_SECURITY_PASSWORD = null;


    /**
     * 初始化配置文件
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/21 17:27
     */
    public static void init() {
        //读取配置
        initProp();
        //是否手动执行
        IS_MANUAL_TASK = String.valueOf(PropUtils.getValueByKey("is.manual.task"));
        //执行的日期 json串
        //E:\weizhi\module-jsfz-soa\mess-push-day\src\main\resources\task.json
        TASK_TIME = PropUtils.getValueByKey("task.time");

        getTaskTime();
        //3大运营商队列名
        /**移动*/
        CMCC_QUEUE = PropUtils.getValueByKey("mess.to.cmcc");
        /**联通*/
        CUCC_QUEUE = PropUtils.getValueByKey("mess.to.cucc");
        /**电信*/
        CTCC_QUEUE = PropUtils.getValueByKey("mess.to.ctcc");

        //3大运营商按照小时查询sql
        /**移动*/
        CMCC_QUERY_SQL = PropUtils.getValueByKey("cmcc.query.sql");

        CUCC_QUERY_SQL = PropUtils.getValueByKey("cucc.query.sql");

        CTCC_QUERY_SQL = PropUtils.getValueByKey("ctcc.query.sql");

        //kafka队列
        KAFKA_LIST = PropUtils.getValueByKey("kafka.broker.list");

        KAFKA_SECURITY_USERNAME = PropUtils.getValueByKey("kafka.security.username");

        KAFKA_SECURITY_PASSWORD = PropUtils.getValueByKey("kafka.security.password");

        // 运营商是否运行
        IS_OPEN_CMSS = PropUtils.getValueByKey("is.open.cmcc");
        IS_OPEN_CUSS = PropUtils.getValueByKey("is.open.cucc");
        IS_OPEN_CTSS = PropUtils.getValueByKey("is.open.ctcc");
    }

    /**
     * 处理要执行的日期
     *
     * @author: sunsf
     * @date: 2020/5/21 16:58
     */
    public static void getTaskTime() {
        //配置文件增加三个值
        //is.manual.task是否手动指定任务执行时间：0不指定，1指定
        //manual.task.day手动指定hive表分区日期
        //manual.task.hour手动指定hive表分区小时，从-1开始
        if (IsEnum.No.getDescription().equals(IS_MANUAL_TASK)) {
            //0不指定，默认跑昨天的数据从0时开始跑
            //本次任务执行查询hive表的分区信息
            List<String> taskString = FileUtils.readFile4List(PropUtils.TASK_TIME);
            for (String task : taskString) {
                JSONObject taskJson = new JSONObject(task);
                TASK_DAY = String.valueOf(taskJson.get("taskDay"));
            }
        } else {
            TASK_DAY = String.valueOf(PropUtils.getValueByKey("manual.task.day"));
        }
    }


    /**
     * 方法描述：获取key的值
     */
    public static String getValueByKey(String key) {
        return get(key, null);
    }

    private static String get(String key, String def) {
        return PROP_K_V.getProperty(key, def) == null ? "" : PROP_K_V.getProperty(key, def);
    }

    /**
     * 方法描述：加载配置文件
     */
    public static void initProp() {
        FileInputStream in = null;
        try {
            REEN_LOCK.lock();
            logger.info("path is " + path);
            File file = new File(path);
            if (file.exists()) {
                if (UPDATE_TIME == file.lastModified() && PROP_K_V != null) {
                    return;
                }
                UPDATE_TIME = file.lastModified();
                PROP_K_V = new Properties();
                in = new FileInputStream(path);
                Reader inStream = new InputStreamReader(in, "UTF-8");
                PROP_K_V.load(inStream);
            } else {
                logger.info("配置文件constant.properties不存在，程序获取不到有效参数！");
            }
        } catch (FileNotFoundException e) {
            logger.info("no file , please check constant.properties " + path);
            logger.info("FileNotFoundException " + e.fillInStackTrace());
        } catch (IOException e) {
            logger.info("load file , failed " + path);
            logger.info("IOException " + e.fillInStackTrace());
        } finally {
            REEN_LOCK.unlock();
            if (in != null) {
                try {
                    FileUtils.close(in);
                } catch (IOException e) {
                    REEN_LOCK.unlock();
                    logger.info("IOException" + e.fillInStackTrace());
                }
            }

        }
    }

    public static void main(String[] args) {
        init();
    }
}
