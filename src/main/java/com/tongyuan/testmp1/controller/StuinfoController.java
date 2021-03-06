package com.tongyuan.testmp1.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.testmp1.aop.Permission;
import com.tongyuan.testmp1.dao.ViewMapper;
import com.tongyuan.testmp1.entity.Hr;
import com.tongyuan.testmp1.entity.Stuinfo;
import com.tongyuan.testmp1.entity.Teacher;
import com.tongyuan.testmp1.helper.PageDataResult;
import com.tongyuan.testmp1.helper.PwdHelper;
import com.tongyuan.testmp1.helper.Token;
import com.tongyuan.testmp1.service.StuinfoService;
import com.tongyuan.testmp1.service.ViewService;
import com.tongyuan.testmp1.util.SecurityUtil;
import com.tongyuan.testmp1.viewModel.EvaluationView;
import com.tongyuan.testmp1.viewModel.StuTeacherView;
import com.tongyuan.testmp1.viewModel.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangcy on 2018/4/9
 */
@RequestMapping("/stuinfo")
@Controller
public class StuinfoController extends BaseController{
    @Autowired
    private StuinfoService stuinfoService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private ViewMapper viewMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /*
    admin或学生更新学生基本信息
     */
    @Permission("admin,student")
    @PostMapping("/update")
    @ResponseBody
    public JSONObject update(Stuinfo stuinfo){
        stuinfoService.updateById(stuinfo);
        return setUpdateResponse();
    }

    /*
    学生查看自己的基本信息
     */
    @Permission("student")
    @GetMapping("/select")
    @ResponseBody
    public JSONObject select(HttpServletRequest request){
        Token stuinfo = getStudentToken(request);
        return setQueryResponse(stuinfoService.selectById(stuinfo.getId()));
    }

    /*
    hr分页查看所有部门所有学生+搜索
     */
    @Permission("hr")
    @GetMapping("/selectByHr")
    @ResponseBody
    public JSONObject selectByHr(HttpServletRequest request,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("limit")Integer limit,
                                      @RequestParam(value = "key",required = false)String key){
        Hr hr = getHr(request);
        PageDataResult<StuTeacherView> result = viewService.selectStuTeaPageByDept(hr.getFirst_dept(),hr.getSecond_dept(),page,limit,key);
        return setQueryResponse(result);
    }

    /*
    导师分页查看所有学生
     */
    @Permission("teacher")
    @GetMapping("/selectByTeacher")
    @ResponseBody
    public JSONObject selectByTeacher(HttpServletRequest request,
                                      @RequestParam("page")Integer page,
                                      @RequestParam("limit")Integer limit){
        Teacher teacher = getTeacher(request);
        PageDataResult<StudentView> result = viewService.selectStudentPageByTeacherJobNumber(teacher.getJob_number(),page,limit);
        return setQueryResponse(result);
    }

    /*
    admin分页查看所有学生+搜索
     */
    @Permission("admin")
    @GetMapping("/selectByAdmin")
    @ResponseBody
    public JSONObject selectByAdmin(@RequestParam("page")Integer page,
                                    @RequestParam("limit")Integer limit,
                                    @RequestParam(value = "key",required = false)String key){
        PageDataResult result = stuinfoService.selectVagueStuPage(page,limit,key);
        return setQueryResponse(result);
    }

    /*
    admin新增学生
     */
    @Permission("admin")
    @PostMapping("/add")
    @ResponseBody
    public JSONObject addByAdmin(Stuinfo stuinfo){
        String psw = PwdHelper.getPwd(stuinfo.getId_number());
        stuinfo.setEncrypt_password(SecurityUtil.encryptPassword(psw));
        stuinfoService.insert(stuinfo);
        return  setInsertResponse();
    }

    /*
    admin批量删除学生
     */
    @Permission("admin")
    @GetMapping("/batchDelete")
    @ResponseBody
    public JSONObject batchDelete(String id){
        String[] ids = id.split(",");
        for(String one:ids){
            stuinfoService.deleteById(Integer.valueOf(one));
        }
        return setBatchDeleteResponse();
    }

    /*
    学生修改密码
     */
    @Permission("student")
    @PostMapping("/updatePwd")
    @ResponseBody
    public JSONObject updatePwd(HttpServletRequest request,@RequestParam("password") String pwd){
        Token token = getStudentToken(request);
        Integer id = token.getId();
        Stuinfo stuinfo = stuinfoService.selectById(id);
        stuinfo.setEncrypt_password(SecurityUtil.encryptPassword(pwd));
        stuinfoService.updateById(stuinfo);
        return setUpdateResponse();
    }

    /*
    管理员重置密码
     */
    @Permission("admin")
    @GetMapping("/resetPwd")
    @ResponseBody
    public JSONObject resetPwd(Integer id){
        Stuinfo stuinfo = stuinfoService.selectById(id);
        String idNumber = stuinfo.getId_number();
        String pwd = PwdHelper.getPwd(idNumber);
        stuinfo.setEncrypt_password(SecurityUtil.encryptPassword(pwd));
        stuinfoService.updateById(stuinfo);
        return setUpdateResponse();
    }

    /*
    导师修改学生评级和学生评价
     */
    @Permission("teacher")
    @PostMapping("/updateEvaluation")
    @ResponseBody
    public JSONObject updateEvaluation(@RequestParam("id") Integer id,
                                       @RequestParam("rank") Integer rank,
                                       @RequestParam("evaluation") String evaluation){
        Stuinfo stuinfo = stuinfoService.selectById(id);
        stuinfo.setRank(rank);
        stuinfo.setEvaluation(evaluation);
        stuinfoService.updateById(stuinfo);
        return setUpdateResponse();
    }

    /*
    hr或导师查看学生评级和学生评价
     */
    @Permission("hr,teacher")
    @GetMapping("/selectEvaluationByHr")
    @ResponseBody
    public JSONObject selectEvaluationByHr(@RequestParam("id") Integer id){
        EvaluationView evaluationView = viewMapper.selectEvaluationById(id);
        return setQueryResponse(evaluationView);
    }

    /*
    学生查看学生评级和学生评价
     */
    @Permission("student")
    @GetMapping("/selectEvaluationByStudent")
    @ResponseBody
    public JSONObject selectEvaluationByStu(HttpServletRequest request){
        Token token = getStudentToken(request);
        Integer id = token.getId();
        EvaluationView evaluationView = viewMapper.selectEvaluationById(id);
        return setQueryResponse(evaluationView);
    }
}
