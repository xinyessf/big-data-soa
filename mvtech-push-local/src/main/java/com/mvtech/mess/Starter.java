package com.mvtech.mess;

import com.mvtech.mess.filter.PushDataToKafkaLocal;
import com.mvtech.mess.util.PropUtils;
import org.apache.log4j.Logger;

/**
 * 按照天推送数据到kafka
 *
 * @author: sunsf
 * @date: 2020/6/18 22:51
 */

public class Starter {

    private static Logger logger = Logger.getLogger(Starter.class);

    /**
     * 业务流程
     * 1、获取要读取天数的数据
     * 2、3个线程将数据写到kafka的3个队列
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        //初始化数据
        PropUtils.init();
        //推送
        PushDataToKafkaLocal pushDataToKafka = new PushDataToKafkaLocal();
        pushDataToKafka.pushDataToKafka();
    }
}
