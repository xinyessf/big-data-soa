package com.mvtech.mess.model;

import java.io.Serializable;


/**
 * 移动短信实体类
 *
 * @author: sunsf
 * @date: 2020/5/20 16:41
 */

public class CuccSms implements Serializable {

    private String c_usernum;
    private String c_relatenum;
    private String c_time;
    private String c_content;
    private Integer c_reason;
    private String c_id;
    private String c_keywords;

    private Integer sms_type;

    public CuccSms() {
    }

    public CuccSms(String c_usernum, String c_relatenum, String c_time, String c_content, Integer c_reason, String c_id, String c_keywords,Integer sms_type) {
        this.c_usernum = c_usernum;
        this.c_relatenum = c_relatenum;
        this.c_time = c_time;
        this.c_content = c_content;
        this.c_reason = c_reason;
        this.c_id = c_id;
        this.c_keywords = c_keywords;
        this.sms_type = sms_type;
    }

    public String getC_usernum() {
        return c_usernum;
    }

    public void setC_usernum(String c_usernum) {
        this.c_usernum = c_usernum;
    }

    public String getC_relatenum() {
        return c_relatenum;
    }

    public void setC_relatenum(String c_relatenum) {
        this.c_relatenum = c_relatenum;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public Integer getC_reason() {
        return c_reason;
    }

    public void setC_reason(Integer c_reason) {
        this.c_reason = c_reason;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_keywords() {
        return c_keywords;
    }

    public void setC_keywords(String c_keywords) {
        this.c_keywords = c_keywords;
    }

    public Integer getSms_type() {
        return sms_type;
    }

    public void setSms_type(Integer sms_type) {
        this.sms_type = sms_type;
    }
}
