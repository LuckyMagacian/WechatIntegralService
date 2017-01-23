package com.lanxi.WechatIntegralService.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */
//发送短信验证码
public class SendMessageUtil {
    private static Logger logger=Logger.getLogger(SendMessageUtil.class);
    public static void main(String[] args) {
        SendMessageUtil sendMessageUtil=new SendMessageUtil();
        sendMessageUtil.sendMessage("6666","18368093686");
    }
    public static String sendMessage(String content,String phone){
        String result=null;
        try {
            String date=TimeUtil.getDate();
            String time=TimeUtil.getTime();
            String url=ConfigUtil.get("messageUrl");
            String key=ConfigUtil.get("smsKey");
            Map<String,String> map =new HashMap<String,String>();
            map.put("mchtId","10");
            map.put("orderId","10"+TimeUtil.getDateTime()+"0000");
            map.put("mobile",phone);
            map.put("content","【蓝喜微管家】"+content);
            map.put("tradeDate",date);
            map.put("tradeTime",time);
            map.put("tdId","2");
            String signStr= SignUtil.mapSortToStringNoSign(map)+key;
            map.put("sign", SignUtil.md5LowerCase(signStr, "utf-8"));
            result=HttpUtil.getJsonByPost(url, map, "utf-8");
            System.out.println("发送内容"+result);
        } catch (Exception e) {
            logger.error("发送短信失败"+e.getMessage(),e);
        }
        return result;
    }

}
