package com.tongyuan.testmp1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by zhangcy on 2018/4/6
 */
@Entity
public class Stuplan {
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
     * 培养目标
     */
    @Column
    private String target;
    /*
    开始日期
     */
    @Column
    private Date start_time;
    /*
    结束日期
     */
    @Column
    private Date end_time;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
