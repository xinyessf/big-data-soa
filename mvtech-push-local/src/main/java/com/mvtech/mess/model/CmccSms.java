package com.mvtech.mess.model;

import java.io.Serializable;


/**
 * 移动短信实体类
 *
 * @author: sunsf
 * @date: 2020/5/20 16:41
 */

public class CmccSms implements Serializable {

    private String c_usernum;
    private String c_relatenum;
    private String c_time;
    private String c_content;
    private Integer sms_type;

    public CmccSms() {
    }

    public CmccSms(String c_usernum, String c_relatenum, String c_time, String c_content,Integer sms_type) {
        this.c_usernum = c_usernum;
        this.c_relatenum = c_relatenum;
        this.c_time = c_time;
        this.c_content = c_content;
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

    public Integer getSms_type() {
        return sms_type;
    }

    public void setSms_type(Integer sms_type) {
        this.sms_type = sms_type;
    }

    @Override
    public String toString() {
        return "CmccSms{" +
                "c_usernum='" + c_usernum + '\'' +
                ", c_relatenum='" + c_relatenum + '\'' +
                ", c_time='" + c_time + '\'' +
                ", c_content='" + c_content + '\'' +
                '}';
    }
}
