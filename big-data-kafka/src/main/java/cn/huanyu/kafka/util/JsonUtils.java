package cn.huanyu.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * json工具类
 * @author: sunsf
 * @create: 2018-04-24 17:20
 **/
public class JsonUtils
{

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * @Description: 对象转json串
     * @param:  obj需要转换的对象
     * @return: 转成的字符串
     * @author: sunsf
     * @date:   2020/1/9 11:01
     */
    public static String serialize(Object obj)throws Exception {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
         return mapper.writeValueAsString(obj);

    }



}
