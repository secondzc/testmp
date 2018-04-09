package com.tongyuan.testmp1.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.testmp1.dao.ViewMapper;
import com.tongyuan.testmp1.entity.Hr;
import com.tongyuan.testmp1.helper.PageDataResult;
import com.tongyuan.testmp1.service.ViewService;
import com.tongyuan.testmp1.viewModel.StuTeacherView;
import com.tongyuan.testmp1.viewModel.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangcy on 2018/4/8
 */
@RequestMapping("/hr")
@Controller
public class HrController extends BaseController{

}
