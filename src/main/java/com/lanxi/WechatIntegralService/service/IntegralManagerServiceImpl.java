package com.lanxi.WechatIntegralService.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.IntegralTransfer;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.WechatIntegralService.util.SendMessageUtil;
import com.lanxi.WechatIntegralService.util.TimeUtil;
import com.lanxi.integral.report.QueryResBody;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.service.IntegralService;
import com.lanxi.token.EasyToken;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.entity.user.WebUserInfo;
import com.lanxi.wechat.manageer.TokenManager;
import com.lanxi.wechat.manageer.UserManager;
@Service("integralManagerService")
public class IntegralManagerServiceImpl{

	private static  Logger logger = Logger.getLogger(IntegralManagerServiceImpl.class);
    @Resource
    BindingService bindingService;
    @Resource
    DaoService dao;
  //进入积分账户
    @RequestMapping("/intoJf")
    @ResponseBody
    public Map<String, Object> intoJf(HttpServletRequest req, HttpServletResponse rep){
    	try {
	        Map<String, Object> map = new HashMap<String, Object>();
	        String code = req.getParameter("code");
	        //通过code获得token
	        WebAccessToken token = TokenManager.generatorWebAccessTokenMetadata(code);
	        String openId = token.getOpenId();
	        //将openid存入token
	        EasyToken easyToken = new EasyToken();
	        easyToken.setInfo(openId);
	        easyToken.setValidTo(System.currentTimeMillis() + 7200000L);
	        map.put("token", easyToken);
	        //检查是否绑定积分账户
	        int count = bindingService.getCountByOpenId(openId);
	        if (count > 0) {
	            //根据openid得到取出用户详情
	            WebUserInfo webUserInfo = JSONObject.parseObject(UserManager.getWebUserInfo(openId), WebUserInfo.class);
	            String headimgUrl = webUserInfo.getHeadImgUrl();
	            //通过openid取出积分账户
	            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
	            //通过积分账户取得姓名，积分值
	            Object message = IntegralService.queryIntegral(integralAccount).getObj();
	            QueryResBody queryResBody = (QueryResBody) message;
	            String name = queryResBody.getCustName();
	            String integralValue = queryResBody.getTotalPoints();
	            map.put("headimgUrl", headimgUrl);
	            map.put("name", name);
	            map.put("integralValue", integralValue);
	            map.put("retMsg", "进入积分账户成功");
	            logger.info("头像=="+headimgUrl+"姓名=="+name+"积分账户=="+integralAccount);
	            return map;
	        } else {
	            map.put("retMsg", "未绑定积分账户");
	            return map;
	        }
    	} catch (Exception e) {
			throw new AppException("进入积分管理异常",e);
		}
    }

    
  //账户资料页面
    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	req.setCharacterEncoding("utf-8");
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg","用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            logger.info("积分账户=="+integralAccount);
            //通过积分账户取得姓名
            Object message = IntegralService.queryIntegral(integralAccount).getObj();
            QueryResBody queryResBody = (QueryResBody) message;
            String name = queryResBody.getCustName();
            //根据微信号取出身份证号和手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String id = accountBinding.getIntegralAccount();
            String idCrad = id.substring(3, id.length());
            String phone = accountBinding.getBindingPhone();
            map.put("idCard", idCrad);
            map.put("phone", phone);
            map.put("openId", openId);
            map.put("name", name);
            logger.info("身份证=="+idCrad+"姓名=="+name+"微信号=="+openId+"手机号"+phone);
        } catch (Exception e) {
            throw new AppException("用户资料页面出错",e);
        }
        return map;
    }
    
    @RequestMapping("/phone")
    @ResponseBody
    public Map<String, Object> phone(HttpServletResponse rep, HttpServletRequest req){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号取出手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            map.put("local","China");
            map.put("phone",bindingPhone);
            logger.info("手机号"+bindingPhone);
        } catch (Exception e) {
            throw new AppException("修改手机号码页面出错", e);
        }
        return map;
    }  
    
    @RequestMapping("/updatePhone")
    @ResponseBody
    public Map<String, Object> updatephone(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号取出手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            logger.info("老手机号"+bindingPhone);
            String phone = req.getParameter("phone");
            logger.info("新手机号"+phone);
            //判断手机号是否与原手机号相同
            if (phone.equals(bindingPhone)) {
                map.put("retMsg", "手机号与原手机号相同");
                return map;
            }
            //验证手机号是否符合标准
            if (null == phone || phone.equals("") || phone.matches("^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$")){
                map.put("retMsg", "手机号码不符合标准");
                return map;
            }
            //生成短信验证码
            String num = RandomUtil.getValidateCode();
            //发送短信
            SendMessageUtil.sendMessage(num, phone);
            map.put("num", num);
            map.put("phone", phone);
            logger.info("生成的验证码"+num);
        } catch (Exception e) {
            new AppException("修改手机号码出错", e);
        }
        return map;
    }  
    
    @RequestMapping("/updatePhoneOper")
    @ResponseBody
    public Map<String, Object> updatePhoneOper(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号取出身份证号和积分账号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String integralAccount =accountBinding.getIntegralAccount();
            String id = accountBinding.getIntegralAccount();
            String idCard = id.substring(3, id.length());
            logger.info("身份证号"+idCard+"积分账户"+integralAccount);
            String phone = req.getParameter("phone");
            //发送的验证码
            String num = req.getParameter("num");
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
            logger.info("生成的验证码"+num+"填写的验证码"+validateCode);
            //验证验证码是否正确
            if (num.equals(validateCode)) {
                //检测该手机号是否绑定了其他的积分账户
                int count = bindingService.getCountByPhone(phone);
                if (count > 0) {
                    map.put("retMsg", "该手机号已经绑定其他积分账户");
                    return map;
                }
                //修改表中手机号
                ReturnMessage message = IntegralService.modifyPhone(integralAccount, phone);
                //通过积分账户取得姓名
                Object obj = IntegralService.queryIntegral(integralAccount).getObj();
                QueryResBody queryResBody = (QueryResBody)obj;
                String name = queryResBody.getCustName();
                map.put("name", name);
                map.put("openId", openId);
                map.put("phone", phone);
                map.put("idCard", idCard);
                map.put("retMsg", "修改成功");
                logger.info("姓名"+name+"新手机号"+phone+"身份证号"+idCard);
            } else {
            	map.put("retCode", "9796");
                map.put("retMsg", "验证码错误");
            }
        } catch (Exception e) {
            throw new AppException("修改手机号码->校验验证码出错", e);
        }
        return map;
    }
    
    //绑定积分账户页面
    @RequestMapping("/bindings")
    @ResponseBody
    public Map<String, Object> bindings(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
            	map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            map.put("openId", openId);
        } catch (Exception e) {
        	 throw new AppException("绑定积分账户页面", e);
        }
        return map;
    }

    //绑定积分账户操作
    @RequestMapping("/bindingsJf")
    @ResponseBody
    public Map<String, Object> bindingsJf(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String name = req.getParameter("name");
            String idCard = req.getParameter("idCard");
            //得到积分账户
            String integralAccount = "101" + idCard;
            String phone = req.getParameter("phone");
            logger.info("姓名"+name+"身份证"+idCard+"手机号"+phone);
            //验证手机号是否符合标准
            if (null == phone || phone.equals("") ||
                    "^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$".matches(phone)) {
                map.put("retMsg", "手机号不符合标准");
                return map;
            }
            //生成短信验证码
            String num = RandomUtil.getValidateCode();
            //发送短信
            SendMessageUtil.sendMessage(num, phone);
            logger.info("验证码"+num);
            map.put("num", num);
            map.put("integralAccount", integralAccount);
            map.put("phone", phone);
            map.put("name", name);
            map.put("retCode", "0000");
        } catch (Exception e) {
        	throw new AppException("绑定积分账户页面出错", e);
        }
        return map;
    }

    //绑定积分账户操作(校验验证码)
    @RequestMapping("/bindingsJfOper")
    @ResponseBody
    public Map<String, Object> bindingsJfOper(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg","用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            WebUserInfo webUserInfo = JSONObject.parseObject(UserManager.getWebUserInfo(openId), WebUserInfo.class);
            //头像
            String headimgUrl = webUserInfo.getHeadImgUrl();
            String phone = req.getParameter("phone");
            String name = req.getParameter("name");
            String integralAccount = req.getParameter("integralAccount");
            logger.info("新手机号"+phone+"姓名"+name+"积分账户"+integralAccount);
            //发送的短信验证码
            String num = req.getParameter("num");
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
           logger.info("生成的验证码"+num+"填写的验证码"+validateCode);
            //验证验证码是否正确
            if (num.equals(validateCode)) {
                //检测该手机号是否绑定了其他的积分账户
                int phoneCount = bindingService.getCountByPhone(phone);
                if (phoneCount > 0) {
                    map.put("retMsg", "该手机号已经绑定其他积分账号");
                    return map;
                }
                //该身份证是否已经存在积分账户
                int count = bindingService.getCountByIntegralAccount(integralAccount);
                if (count < 1) {
                    map.put("retMsg", "该身份证号没有绑定的积分账号");
                    return map;
                }
                //手机号入到积分表
                ReturnMessage message = IntegralService.modifyPhone(integralAccount, phone);
                //通过积分账户取得积分值
                Object obj = IntegralService.queryIntegral(integralAccount).getObj();
                QueryResBody queryResBody = (QueryResBody)obj;
                String integralValue = queryResBody.getTotalPoints();
                logger.info("积分值"+integralValue);
                map.put("headimgUrl", headimgUrl);
                map.put("name", name);
                map.put("integralValue", integralValue);
                map.put("retMsg", "绑定成功");
            } else {
            	map.put("retCode", "9798");
                map.put("retMsg", "绑定失败");
            }
        } catch (Exception e) {
            throw new AppException("绑定积分账户出错", e);
        }
        return map;
    }

    //积分明细查询
    @RequestMapping("/integralQuery")
    @ResponseBody
    public Map<String, Object> integralQuery(HttpServletResponse rep,HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //得到六个月前的日期
            String startDate = TimeUtil.getBeforeDate();
            //查询明细
            ReturnMessage message = IntegralService.historyIntegral(integralAccount, startDate);
            logger.info("积分明细"+message.getObj());
            map.put("message",message.getObj());
            map.put("retMsg", "查询成功");
        } catch (Exception e) {
        	map.put("retCode", "9799");
            map.put("retMsg", "查询失败");
        }
        return map;
    }

    //积分转增
    @RequestMapping("/deliveryIntegral")
    @ResponseBody
    public Map<String, Object> deliveryIntegral(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //积分转增值
            String integral = req.getParameter("integral");
            //接收方身份证号
            String receiverIdCard = req.getParameter("receiverIdCard");
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户取得积分值
            Object message = IntegralService.queryIntegral(integralAccount).getObj();
            QueryResBody queryResBody = (QueryResBody)message;
            String integralValue = queryResBody.getTotalPoints();
            logger.info("积分转增值"+integral+"接收方身份证号"+receiverIdCard+"积分账户"+integralAccount+""+"现有积分值"+integralValue);
            int integral2 = Integer.parseInt(integral);
            int integralValue1 = Integer.parseInt(integralValue);
            if (integral2 > integralValue1) {
                map.put("retMsg", "您的积分余额不足");
                return map;
            }
            String integralAccount2 = "101" + receiverIdCard;
            //查询接受账户是否存在
            int count = bindingService.getCountByIntegralAccount(integralAccount2);
            if (count < 1) {
                map.put("retMsg", "该身份证号没有匹配的积分账号");
                return map;
            }
            //通过积分账户查询绑定的手机号
            String phone = bindingService.getPhoneByIntegralAccount(integralAccount);
            //生成短信验证码
            String num = RandomUtil.getValidateCode();
            logger.info("绑定的手机号"+phone+"生成的验证码"+num);
            //发送短信
            SendMessageUtil.sendMessage(num, phone);
            map.put("num", num);
            map.put("integral", integral);
            map.put("integralAccount", integralAccount);
            map.put("integralAccount2", integralAccount2);
            map.put("retCode", "0000");
        } catch (Exception e) {
            throw new AppException("积分转增出错", e);
        }
        return map;
    }

    //积分转增操作（校验验证码）
    @RequestMapping("/deliveryIntegralOper")
    @ResponseBody
    public Map<String, Object> deliveryIntegralOper(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            //发送的短信验证码
            String num = req.getParameter("num");
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
            logger.info("生成的验证码"+num+"填写的验证码"+validateCode);
            //验证验证码是否正确
            if (num.equals(validateCode)) {
                //赠送方账户
                String integralAccount = req.getParameter("integralAccount");
                //接收方账户
                String integralAccount2 = req.getParameter("integralAccount2");
                //接收方身份证
                String idCard = integralAccount2.substring(3, integralAccount2.length());
                //赠送的积分值
                String integral = req.getParameter("integral");
                //赠送时间
                String time = TimeUtil.getDateTime();
                //流水号
                String serialId=TimeUtil.getDateTime();
                logger.info("积分转增值"+integral+"接收方账户"+integralAccount2+"赠送方账户"+integralAccount+""+"流水号"+serialId);
                //转增操作
                ReturnMessage message = IntegralService.transferIntegral(integralAccount, integralAccount2, integral);
                //入到本地
                IntegralTransfer integralTransfer = new IntegralTransfer();
                integralTransfer.setGiverOpenId(integralAccount);
                integralTransfer.setReceiverIdCard(idCard);
                integralTransfer.setIntegral(Integer.parseInt(integral));
                integralTransfer.setTransferTime(time);
                integralTransfer.setSerialId(serialId);
                dao.addIntegralTransfer(integralTransfer);
                map.put("retMsg", "转增成功");
                map.put("retCode","0000");
            } else {
                map.put("retMsg", "转增失败");
                map.put("retCode","9795");
            }
        } catch (Exception e) {
            throw new AppException("积分转增->校验验证码出错", e);

        }
        return map;
    }

    //发送短信通知
    @SuppressWarnings("finally")
	@RequestMapping("/sendMessage")
    @ResponseBody
    public Map<String,Object> sendMessage(HttpServletResponse rep, HttpServletRequest req) {
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            //得到openid
        	String  tokenStr =req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retMsg", "用户需要重新登录");
                return map;
            }
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户取得姓名
            Object message = IntegralService.queryIntegral(integralAccount).getObj();
            QueryResBody queryResBody = (QueryResBody) message;
            String name = queryResBody.getCustName();
            //被赠送方手机号
            String phone = req.getParameter("phone");
            //短信内容
            String content = "您的好友" + name + "向您赠送了积分，点此查看xxx（微信积分服务平台连接）";
            logger.info("姓名"+name+"被赠送方手机号"+phone+"短信内容"+content);
            //发送短信
            SendMessageUtil.sendMessage(content, phone);
            map.put("retMsg", "发送短信成功");
            map.put("retCode", "0000");
        } catch (Exception e) {
        	map.put("retCode", "9999");
        	map.put("retMsg", "发送短信失败");
            throw new AppException("发送短信页面出错",e);
        }finally {
        	return map;
		}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}
