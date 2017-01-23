package com.lanxi.WechatIntegralService.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.service.IntegralManagerServiceImpl;
import com.lanxi.WechatIntegralService.util.AppException;

@Controller
public class IntegralController {
    @Resource
    private IntegralManagerServiceImpl integralService;

    @RequestMapping("/intoJf.do")
//	@RequestMapping(value = "/intoJf.do" , produces = {"application/json;charset=UTF-8"})
    public void intoJf(HttpServletRequest req, HttpServletResponse rep) {
        try {
            Map<String, Object> map = integralService.intoJf(req, rep);
            if (map.get("retCode").equals("0000"))
                rep.sendRedirect("points_weixin/index.html?token=" + map.get("token"));
            else if (map.get("retCode").equals("3402"))
                rep.sendRedirect("points_weixin/binding.html?token=" + map.get("token"));
            else
                rep.sendRedirect("points_weixin/404.html");
        } catch (Exception e) {
            throw new AppException("进入主页异常！", e);
        }
    }
    // 	 @RequestMapping("/userInfo")
    @RequestMapping(value = "/userInfo.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUserInfo(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.getUserInfo(rep, req));
    }

    // 	 @RequestMapping("/userInfo")
    @RequestMapping(value = "/getInfoByToken.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getInfoByToken(HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.getInfoByToken(req));
    }
    @RequestMapping(value = "/cancelBinding.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String cancelBinding(HttpServletRequest req){
        return JSONObject.toJSONString(integralService.cancelBindings(req));
    }
    //     @RequestMapping("/phone")
    @RequestMapping(value = "/phone.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String phone(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.phone(rep, req));
    }

    //     @RequestMapping("/updatePhone")
    @RequestMapping(value = "/updatePhone.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updatephone(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.updatephone(rep, req));
    }

    //     @RequestMapping("/updatePhoneOper")
    @RequestMapping(value = "/updatePhoneOper.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updatePhoneOper(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.updatePhoneOper(rep, req));
    }

    //     @RequestMapping("/bindingsJf")
    @RequestMapping(value = "/bindingsJf.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String bindingsJf(HttpServletRequest req, HttpServletResponse rep) {
        return JSONObject.toJSONString(integralService.bindingsJf(req, rep));
    }

    //     @RequestMapping("/bindingsJfOper")
    @RequestMapping(value = "/bindingsJfOper.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String bindingsJfOper(HttpServletRequest req, HttpServletResponse rep) {
        return JSONObject.toJSONString(integralService.bindingsJfOper(req, rep));
    }

    //     @RequestMapping("/integralQuery")
    @RequestMapping(value = "/integralQuery.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String integralQuery(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.integralQuery(rep, req));
    }

    //     @RequestMapping("/deliveryIntegral")
    @RequestMapping(value = "/deliveryIntegral.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deliveryIntegral(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.deliveryIntegral(rep, req));
    }

    //     @RequestMapping("/deliveryIntegralOper")
    @RequestMapping(value = "/deliveryIntegralOper.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deliveryIntegralOper(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.deliveryIntegralOper(rep, req));
    }

    //     @RequestMapping("/sendMessage")
    @RequestMapping(value = "/sendMessage.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String sendMessage(HttpServletResponse rep, HttpServletRequest req) {
        return JSONObject.toJSONString(integralService.sendMessage(rep, req));
    }
}
