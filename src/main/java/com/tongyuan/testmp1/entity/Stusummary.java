package com.tongyuan.testmp1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by zhangcy on 2018/4/6
 */
@Entity
public class Stusummary {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     *学生信息id
     */
    @Column
    private Integer stuid;
    @Column
    private Integer month;
    /**
     * 月度总结
     */
    @Column(length = 4095)
    private String summary;
    /**
     * 问题和困难
     */
    @Column(length = 4095)
    private String question;
    /**
     * 下个月计划
     */
    @Column(length = 4095)
    private String plan;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
