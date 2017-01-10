package com.lanxi.WechatIntegralService.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */
//发送短信验证码
public class SendMessageUtil {

    public static void sendMessage(String content,String phone){
        String date=TimeUtil.getDate();
        String time=TimeUtil.getTime();
        String url=ConfigUtil.get("messageUrl");
        Map<String,String> map =new HashMap<>();
        map.put("mchtId","11");
        map.put("mobile",phone);
        map.put("content",content);
        map.put("tradeDate",date);
        map.put("tradeTime",time);
        String result=HttpUtil.getJsonByPost(url, map, "utf-8");
        System.out.println("发送内容"+result);
    }

}
