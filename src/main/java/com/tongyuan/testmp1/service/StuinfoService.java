package com.tongyuan.testmp1.service;

import com.baomidou.mybatisplus.service.IService;
import com.tongyuan.testmp1.entity.Stuinfo;
import com.tongyuan.testmp1.helper.PageDataResult;

import java.util.List;

/**
 * Created by zhangcy on 2018/4/6
 */
public interface StuinfoService extends IService<Stuinfo> {
    /**
     * 根据导师工号查找实习生
     * @param jobNum
     * @return
     */
    List<Stuinfo> selectByTeacherNum(String jobNum);

    /**
     * 根据部门查找实习生
     * @param firstDept
     * @param secondDept
     * @return
     */
    List<Stuinfo> selectByDept(String firstDept,String secondDept);

    /**
     * 根据工号查id
     * @param job_number
     * @return
     */
    Integer selectIdByJobNumber(String job_number);

    /*
    hr分页查找所有学生+搜索
     */
    PageDataResult<Stuinfo> selectVagueStuPage(Integer page,Integer limit,String key);
}
