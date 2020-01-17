package com.cn.zkfh.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.zkfh.log.SysLog;

@RequestMapping
@Controller
public class AuthAuditController {

    /**
     * 插入审计机构认证信息
     */
    @RequestMapping(value = {"/test/test"},method = {RequestMethod.GET})
    public ModelAndView addAuthAuditBase(){
    	//SysLog.info("11111111111");
    	System.out.println("11112145");
    	//String json = "{'code':200,'data':{'2018':{'name':['测试','其他'],'data':[{'name':'测试','value':1000.0},{'name':'其他','value':5.5821503E10}]},'2017':{'name':['te','其他'],'data':[{'name':'te','value':4444.0},{'name':'其他','value':3.8335082556E10}]},'2016':{'name':['石膏板打','其他'],'data':[{'name':'石膏板打','value':334.0},{'name':'其他','value':2.3280071666E10}]},'2015':{'name':[],'data':[]},'2014':{'name':[],'data':[]}},'message':'操作成功'}";
    	Map map2 = new HashMap();
    	map2.put("a2018", "");
    	map2.put("a2017", "");
    	map2.put("a2016", "");
    	map2.put("a2015", "");
    	map2.put("a2014", "");
    	
    	Map map1 = new HashMap();
    	map1.put("code", "200");
    	map1.put("data", map2);
    	String json = JSONObject.toJSONString(map1);
    	
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("json", json);
    	mv.setViewName("ok");
        return mv;
    }
     
    /**
     * 插入审计机构认证信息
     */
    @RequestMapping(value = {"/test/test1"},method = {RequestMethod.GET})
    @ResponseBody
    public String addAuthAuditBase1(){
    	SysLog.info("11111111111");
    	System.out.println("11112145");
    	
    	String json = "{'code':200,'data':{'2018':{'name':['测试','其他'],'data':[{'name':'测试','value':1000.0},{'name':'其他','value':5.5821503E10}]},'2017':{'name':['te','其他'],'data':[{'name':'te','value':4444.0},{'name':'其他','value':3.8335082556E10}]},'2016':{'name':['石膏板打','其他'],'data':[{'name':'石膏板打','value':334.0},{'name':'其他','value':2.3280071666E10}]},'2015':{'name':[],'data':[]},'2014':{'name':[],'data':[]}},'message':'操作成功'}";
    	
        //return ResultMsg.ResultMsg("999999");
    	return json;
    }
    
    
}
