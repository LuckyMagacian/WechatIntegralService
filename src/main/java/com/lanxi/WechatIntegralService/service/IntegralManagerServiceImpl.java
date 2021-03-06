package com.lanxi.WechatIntegralService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanxi.integral.report.HistoryResBody;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.IntegralTransfer;
import com.lanxi.WechatIntegralService.entity.ValidCode;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
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
public class IntegralManagerServiceImpl {

    private static Logger logger = Logger.getLogger(IntegralManagerServiceImpl.class);
    @Resource
    BindingService bindingService;
    @Resource
    DaoService dao;

    /**
     * 进入积分账户页面
     *
     * @param req
     * @param rep
     * @return
     */
    public Map<String, Object> intoJf(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            logger.info("token==" + tokenStr);
            //检测是否第一次进入
            if (tokenStr == null || tokenStr.equals("")) {
                String code = req.getParameter("code");
                logger.info("code==" + code);
                //通过code获得token
                WebAccessToken token = TokenManager.generatorWebAccessTokenMetadata(code);
                String openId = token.getOpenId();
                //将openid存入token
                EasyToken easyToken = new EasyToken();
                easyToken.setInfo(openId);
                easyToken.setValidTo(System.currentTimeMillis() + Long.parseLong(ConfigUtil.get("easyTokenExpiryTime")) * 1000);
                map.put("token", easyToken.toToken());
                logger.info("token===" + easyToken.toToken());
                //检查是否绑定积分账户
                int count = bindingService.getCountByOpenId(openId);
                logger.info("count===" + count);
                if (count > 0) {
                    //根据openid得到取出用户详情
                    WebUserInfo webUserInfo = new WebUserInfo();
                    webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
                    String headimgUrl = webUserInfo.getHeadImgUrl();
                    //通过openid取出积分账户
                    String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
                    //通过积分账户取得姓名，积分值
                    ReturnMessage message = IntegralService.queryIntegral(integralAccount);
                    if (!message.getRetCode().equals("0000")) {
                        logger.error("取积分值和姓名失败");
                        map.put("retCode","3404");
                        map.put("retMsg", message.getRetMsg());
                        return map;
                    }
                    QueryResBody queryResBody = (QueryResBody) message.getObj();
                    String name = queryResBody.getCustName();
                    String integralValue = queryResBody.getTotalPoints();
                    map.put("headimgUrl", headimgUrl);
                    map.put("name", name);
                    map.put("integralValue", integralValue);
                    map.put("retCode", "0000");
                    map.put("retMsg", "进入积分账户成功");
                    logger.info("头像==" + headimgUrl + "姓名==" + name + "积分账户==" + integralAccount);
                    return map;
                } else {
                    map.put("token", easyToken.toToken());
                    map.put("retCode", "3402");
                    map.put("retMsg", "未绑定积分账户");
                    logger.info("未绑定积分账户");
                    return map;
                }
            }
            EasyToken easyToken2 = EasyToken.verifyTokenRenew(tokenStr);
            String openId = easyToken2.getInfo();
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
            String headimgUrl = webUserInfo.getHeadImgUrl();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户取得姓名，积分值
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("token", tokenStr);
                map.put("retCode","3404");
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String name = queryResBody.getCustName();
            String integralValue = queryResBody.getTotalPoints();
            map.put("token", tokenStr);
            map.put("headimgUrl", headimgUrl);
            map.put("name", name);
            map.put("integralValue", integralValue);
            map.put("retCode", "0000");
            map.put("retMsg", "进入积分账户成功");
            logger.info("头像==" + headimgUrl + "姓名==" + name + "积分账户==" + integralAccount);
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "进入积分账户异常");
            throw new AppException("进入积分管理异常", e);
        }
        return map;
    }

    /**
     * 根据token获取用户信息
     */
    public Map<String, Object> getInfoByToken(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        logger.info("token==" + tokenStr);
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", tokenStr);
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            logger.info("微信号" + openId);
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            String infoStr = UserManager.getWebUserInfo(openId);
            logger.info("获取到的用户信息:" + infoStr);
            webUserInfo.fromStr(infoStr);
            String headimgUrl = webUserInfo.getHeadImgUrl();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            logger.info("头像" + headimgUrl + "积分账户" + integralAccount);
            //通过积分账户取得姓名，积分值
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("token", token.toToken());
                map.put("retCode","3404");
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String name = queryResBody.getCustName();
            String integralValue = queryResBody.getTotalPoints();
            map.put("token", token.toToken());
            map.put("headimgUrl", headimgUrl);
            map.put("name", name);
            map.put("integralValue", integralValue);
            map.put("retCode", "0000");
            map.put("retMsg", "根据token获取用户信息成功");
            logger.info("姓名==" + name + "积分值==" + integralValue);
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "根据token获取用户信息失败");
            throw new AppException("根据token获取用户信息失败", e);
        }
        return map;
    }

    /**
     * 进入账户资料页面
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> getUserInfo(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        logger.info("token==" + tokenStr);
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", tokenStr);
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
            String nickname = webUserInfo.getNickName();
            //根据微信号取出身份证号和手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String integralAccount = accountBinding.getIntegralAccount();
            String idCrad = integralAccount.substring(3, integralAccount.length());
            String phone = accountBinding.getBindingPhone();
            map.put("token", token.toToken());
            map.put("idCard", idCrad);
            map.put("phone", phone);
            map.put("nickname", nickname);
            map.put("retCode", "0000");
            map.put("retMsg", "进入账户资料页面成功");
            logger.info("进入账户资料页面成功，身份证==" + idCrad + "微信名==" + nickname + "手机号" + phone);
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "进入账户资料页面失败");
            throw new AppException("用户资料页面出错", e);
        }
        return map;
    }

    /**
     * 取消绑定
     *
     * @param req
     * @return
     */
    public Map<String, Object> cancelBindings(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        String tokenStr = req.getParameter("token");
        logger.info("token==" + tokenStr);
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            map.put("token", token.toToken());
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //根据微信号删除记录
            bindingService.cancelBindings(openId);
            map.put("token", token.toToken());
            map.put("retCode", "0000");
            map.put("retMsg", "取消绑定成功");
            logger.info("取消绑定成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "取消绑定失败");
            logger.error("取消绑定失败");
            throw new AppException("取消绑定出错", e);
        }
        return map;
    }

    /**
     * 更换手机号页面
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> phone(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        String tokenStr = req.getParameter("token");
        logger.info("token==" + tokenStr);
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", tokenStr);
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //根据微信号取出手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            map.put("token", token.toToken());
            map.put("local", "China");
            map.put("phone", bindingPhone);
            map.put("retCode", "0000");
            map.put("retMsg", "进入更换手机号码页面成功");
            logger.info("进入更改手机号码页面成功手机号" + bindingPhone + "微信号" + openId);
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "进入更换手机号码页面失败");
            logger.error("进入更换手机号码页面失败");
            throw new AppException("进入更改手机号码页面出错", e);
        }
        return map;
    }

    /**
     * 更换手机号发送验证码
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> updatephone(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        logger.info("用户发送短信验证码!~");
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
        }
        try {
            String openId = token.getInfo();
            //根据微信号取出原手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            logger.info("老手机号" + bindingPhone);
            String phone = req.getParameter("phone");
            logger.info("新手机号" + phone);
            //判断手机号是否与原手机号相同
            if (phone.equals(bindingPhone)) {
                map.put("token", token.toToken());
                map.put("retCode", "3408");
                map.put("retMsg", "手机号与原手机号相同");
                logger.info("手机号码与原来的相同");
                return map;
            }
            //发送短信验证码
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("token", token.toToken());
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "3409");
                logger.info("发送短信验证码失败");
                return map;
            }
            map.put("token", token.toToken());
            map.put("retCode", "0000");
            map.put("retMsg", "更换手机号码发送验证码操作成功");
            logger.info("发送验证码成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "更改手机号发送验证码失败");
            logger.error("更改手机号发送验证码失败");
            new AppException("更改手机号发送验证码失败", e);
        }
        return map;
    }

    /**
     * 更换手机号校验验证码
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> updatePhoneOper(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //根据微信号取出身份证号和积分账号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String integralAccount = accountBinding.getIntegralAccount();
            logger.info("积分账户：" + integralAccount);
            String phone = req.getParameter("phone");
            logger.info("新手机号：" + phone);
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("token", token.toToken());
                map.put("retCode", "3411");
                map.put("retMsg", "该验证码已失效");
                logger.info("验证码失效");
                return map;
            }
            //取出发送的验证码
            String code = dao.getCodeByPhone(phone);
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
            logger.info("生成的验证码" + code + "填写的验证码" + validateCode);
            //验证验证码是否正确
            if (code.equals(validateCode)) {
                //检测该手机号是否绑定了其他的积分账户
                int count = bindingService.getCountByPhone(phone);
                if (count > 0) {
                    map.put("token", token.toToken());
                    map.put("retCode", "3412");
                    map.put("retMsg", "该手机号已经绑定其他积分账户");
                    logger.info("该手机号已经绑定其他积分账户");
                    return map;
                }
                //修改短信验证码状态为已使用
                dao.updateStatusByPhone(phone);
                //修改综合积分表中手机号
                ReturnMessage message = IntegralService.modifyPhone(integralAccount, phone);
                if (!message.getRetCode().equals("0000")) {
                    logger.error("修改积分系统手机号失败");
                    map.put("token", token.toToken());
                    map.put("retCode","3413");
                    map.put("retMsg", message.getRetMsg());
                    return map;
                }
                //修改绑定表中的手机号
                bindingService.updatePhone(phone, openId);
                logger.info("修改结果" + message);
                map.put("token", token.toToken());
                map.put("retCode", "0000");
                map.put("retMsg", "更改手机号校验验证码成功");
                logger.info("修改手机号码成功");
            } else {
                map.put("token", token.toToken());
                map.put("retCode", "3410");
                map.put("retMsg", "验证码错误");
                logger.info("验证码错误");
            }
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "更改手机号校验验证码失败");
            logger.error("更改手机号校验验证码失败");
            throw new AppException("修改手机号码->校验验证码出错", e);
        }
        return map;
    }

    /**
     * 绑定积分账户
     *
     * @param req
     * @param rep
     * @return
     */
    public Map<String, Object> bindingsJf(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String phone = req.getParameter("phone");
            //验证手机号是否符合标准
            if (null == phone || phone.equals("") || !phone.matches("^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$")) {
                map.put("token", token.toToken());
                map.put("retCode", "3414");
                map.put("retMsg", "手机号不符合标准");
                logger.info("手机号不符合标准");
                return map;
            }
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("token", token.toToken());
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "3409");
                return map;
            }
            map.put("token", token.toToken());
            map.put("retCode", "0000");
            map.put("retMsg", "绑定积分页面发送验证码成功");
            logger.info("绑定积分页面发送验证码成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "绑定积分页面发送验证码失败");
            logger.info("绑定积分页面发送验证码失败");
            throw new AppException("绑定积分页面发送验证码出错", e);
        }
        return map;
    }

    //绑定积分账户操作(校验验证码)
    public Map<String, Object> bindingsJfOper(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            logger.info("token" + tokenStr + "微信号" + openId);
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
            //头像
            String headimgUrl = webUserInfo.getHeadImgUrl();
            String phone = req.getParameter("phone");
            String nickName = req.getParameter("name");
            String idCard = req.getParameter("idcard");
            String integralAccount = "101" + idCard;
            logger.info("手机号" + phone + "姓名" + nickName + "身份证号" + idCard);
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("token", token.toToken());
                map.put("retCode", "3411");
                map.put("retMsg", "该验证码已失效");
                logger.info("该验证码已失效");
                return map;
            }
            //取出发送的验证码
            String code = dao.getCodeByPhone(phone);
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
            logger.info("生成的验证码" + code + "填写的验证码" + validateCode);
            //验证验证码是否正确
            if (code.equals(validateCode)) {
                //检测该手机号是否绑定了其他的积分账户
                int phoneCount = bindingService.getCountByPhone(phone);
                logger.info("phoneCount==" + phoneCount);
                if (phoneCount > 0) {
                    map.put("token", token.toToken());
                    map.put("retCode", "3412");
                    map.put("retMsg", "该手机号已经绑定其他积分账号");
                    logger.info("该手机号已经绑定其他积分账号");
                    return map;
                }
                //该身份证是否已经存在积分账户
                ReturnMessage message2 = IntegralService.queryIntegral(integralAccount);
                if (!message2.getRetCode().equals("0000")) {
                    logger.error("该身份证号没有对应的积分账户");
                    map.put("token", token.toToken());
                    map.put("retCode","3403");
                    map.put("retMsg", "该身份证号没有对应的积分账户");
                    return map;
                }
                //手机号入到积分表
                ReturnMessage message = IntegralService.modifyPhone(integralAccount, phone);
                if (!message.getRetCode().equals("0000")) {
                    logger.error("手机号入到积分系统失败");
                    map.put("token", token.toToken());
                    map.put("retCode", "3413");
                    map.put("retMsg", message.getRetMsg());
                    return map;
                }
                logger.info("手机号入表结果" + message);
                AccountBinding accountBinding = new AccountBinding();
                accountBinding.setBindingTime(TimeUtil.getDate());
                accountBinding.setOpenId(openId);
                accountBinding.setBindingPhone(phone);
                accountBinding.setHeadimgUrl(headimgUrl);
                accountBinding.setIntegralAccount(integralAccount);
                accountBinding.setBindingTime(TimeUtil.getDate());
                accountBinding.setNickName(nickName);
                //绑定账号插入绑定表中
                bindingService.insert(accountBinding);
                //修改短信验证码状态为已使用
                dao.updateStatusByPhone(phone);
                map.put("token", token.toToken());
                map.put("retCode", "0000");
                map.put("retMsg", "绑定积分账户校验验证码成功");
                logger.info("绑定积分账户校验验证码成功");
            } else {
                map.put("token", token.toToken());
                map.put("retCode", "3410");
                map.put("retMsg", "绑定积分账户校验验证码失败");
                logger.info("绑定积分账户校验验证码失败");
            }
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "绑定积分账户校验验证码异常");
            throw new AppException("绑定积分账户校验验证码出错", e);
        }
        return map;
    }

    /**
     * 查询积分明细
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> integralQuery(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map> mapList = new ArrayList<>();
        List<Map> integralList = new ArrayList<>();
        double addPoint = 0;
        double deadlinePoint = 0;
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            //得到openid
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户查询总积分
            ReturnMessage returnMessage = IntegralService.queryIntegral(integralAccount);
            QueryResBody queryResBody = (QueryResBody) returnMessage.getObj();
            String totalPoints = queryResBody.getTotalPoints();
            //得到六个月前的日期
            String startDate = TimeUtil.getBeforeDate(-6);
            logger.info("积分账户==" + integralAccount + "六个月前的日期==" + startDate);

            String selectDate = req.getParameter("selectDate");
            //查询明细
            ReturnMessage message = IntegralService.historyIntegral(integralAccount, startDate);
            if (!message.getRetCode().equals("0000")) {
                logger.error("查询积分明细失败");
                map.put("token", token.toToken());
                map.put("retCode","3415");
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            HistoryResBody historyResBody = (HistoryResBody) message.getObj();
            List<HistoryResBody.Item> list = historyResBody.getSerialList();
            for (HistoryResBody.Item item : list) {
                Map<String, String> typeMap = HistoryResBody.POINTS_TYPE_MAP;
                Map<String, String> itemMap = new HashMap<>();
                //变更时间
                String occurDate = item.getOccurDate();
                //变更分值
                String pointsVal = item.getPointsVal();
                //变更类型
                String type = item.getPointsType();
                String pointType = typeMap.get(type);
                //得到近期增加的积分
                if (type.substring(0, 1).equals("0")) {
                    //得到一个月前的日期
                    int date = Integer.parseInt(TimeUtil.getBeforeDate(-1));
                    int occurDates = Integer.parseInt(occurDate);
                    Double points = Double.parseDouble(pointsVal);
                    if (occurDates > date) {
                        addPoint = addPoint + points;
                    }
                }
                //选中日期记录
                if (selectDate != null && !selectDate.equals("")) {
                    if (selectDate.equals(occurDate)) {
                        itemMap.put("occurDate", occurDate);
                        itemMap.put("pointsVal", pointsVal);
                        itemMap.put("pointType", pointType);
                        mapList.add(itemMap);
                    }
                } else {
                    itemMap.put("occurDate", occurDate);
                    itemMap.put("pointsVal", pointsVal);
                    itemMap.put("pointType", pointType);
                    mapList.add(itemMap);
                }
            }
            //分页显示
            String page = req.getParameter("page");
            logger.info("当前页数" + page);
            int currentPage = Integer.parseInt(page);
            int pageSize = 10;
            //总记录数
            int size = mapList.size();
            //总页数
            int totalPage = size / pageSize;
            int num = size % pageSize;
            if (num > 0) {
                totalPage++;
            }
            if (currentPage > totalPage) {
                logger.error("页数超标");
                map.put("token", token.toToken());
                map.put("retCode", "0000");
                map.put("message", "");
                map.put("retMsg", "页数超标");
                return map;
            }
            //当前页开始位置
            int begin = (currentPage - 1) * pageSize;
            //当前页结束位置
            int end = currentPage * pageSize - 1;
            logger.info("记录数" + size + "当前页开始位置" + begin + "当前页结束位置" + end);
            int sizes=size-1;
            int endLine = end >sizes ? sizes : end;
            if (size != 0) {
                for (int i = begin; i <= endLine; i++) {
                    integralList.add(mapList.get(i));
                }
            }
            //得到即将过期的积分
            ReturnMessage returnMessage1 = IntegralService.queryIntegral(integralAccount);
            if (!returnMessage1.getRetCode().equals("0000")) {
                logger.error("查询积分失败");
                map.put("retCode", message.getRetCode());
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody1 = (QueryResBody) returnMessage1.getObj();
            List<QueryResBody.PointsItem> list1 = queryResBody1.getDetailList();
            for (QueryResBody.PointsItem item : list1) {
                int deadline = Integer.parseInt(item.getDeadline());
                int date = Integer.parseInt(TimeUtil.getBeforeDate(1));
                if (deadline < date) {
                    Double points = Double.parseDouble(item.getPoints());
                    deadlinePoint = deadlinePoint + points;
                }
            }
            logger.info("积分明细" + integralList);
            map.put("token", token.toToken());
            map.put("deadLinePoint", deadlinePoint);
            map.put("addPoint", addPoint);
            map.put("totalPoints", totalPoints);
            map.put("totalPage", totalPage);
            map.put("size", size);
            map.put("message", integralList);
            map.put("retCode", "0000");
            map.put("retMsg", "查询积分明细成功");
            logger.info("查询积分明细成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "查询积分明细失败");
            logger.info("查询积分明细失败");
            throw new AppException("查询积分明细出错", e);
        }
        return map;
    }

    /**
     * 积分转增发送验证码
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> deliveryIntegral(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //积分转增值
            String integral = req.getParameter("integral");
            //接收方身份证号
            String receiverIdCard = req.getParameter("receiverIdCard");
            logger.info("积分转增值" + integral + "接收方身份证号" + receiverIdCard);
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();

            if (integralAccount.equals("101" + receiverIdCard)) {
                map.put("token", token.toToken());
                map.put("retCode", "3416");
                map.put("retMsg", "不能转增给自己");
                logger.info("不能转增给自己");
                return map;
            }
            //通过积分账户取得积分值
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("token", token.toToken());
                map.put("retCode","3417");
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String integralValue = queryResBody.getTotalPoints();
            logger.info("积分账户" + integralAccount + "" + "现有积分值" + integralValue);
            double integral2 =Double.parseDouble(integral.trim());
            double integralValue1 =Double.parseDouble(integralValue.trim());
            if (integral2 > integralValue1) {
                map.put("token", token.toToken());
                map.put("retCode", "3418");
                map.put("retMsg", "您的积分余额不足");
                logger.info("您的积分余额不足");
                return map;
            }
            //接收方积分账户
            String integralAccount2 = "101" + receiverIdCard;
            logger.info("接收方积分账户"+integralAccount2);
            ReturnMessage message1 = IntegralService.queryIntegral(integralAccount2);
            logger.info(message1);
            if (!message1.getRetCode().equals("0000")) {
                map.put("token", token.toToken());
                map.put("retCode", "3419");
                map.put("retMsg", "该身份证号没有匹配的积分账号");
                logger.info("该身份证号没有匹配的积分账号");
                return map;
            }
            //通过积分账户查询绑定的手机号
            String phone = bindingService.getPhoneByIntegralAccount(integralAccount);
            //发送验证码
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("token", token.toToken());
                map.put("retMsg", "发送验证码失败");
                map.put("retCode", "3409");
                logger.info("发送短信验证码失败");
                return map;
            }
            map.put("token", token.toToken());
            map.put("retCode", "0000");
            map.put("retMsg", "积分转增发送验证码成功");
            logger.info("积分转增发送验证码成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "积分转增发送验证码失败");
            logger.info("积分转增发送验证码失败");
            throw new AppException("积分转增发送验证码出错", e);
        }
        return map;
    }

    /**
     * 积分转增校验验证码
     *
     * @param rep
     * @param req
     * @return
     */
    public Map<String, Object> deliveryIntegralOper(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //得到绑定的手机号
            String phone = bindingService.getMessage(openId).getBindingPhone();
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            logger.info("绑定的手机号" + phone + "验证码状态" + status);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("token", token.toToken());
                map.put("retCode", "3411");
                map.put("retMsg", "该验证码已失效");
                logger.info("验证码失效");
                return map;
            }
            //取出发送的验证码
            String code = dao.getCodeByPhone(phone);
            //用户填写的验证码
            String validateCode = req.getParameter("validateCode");
            logger.info("生成的验证码" + code + "填写的验证码" + validateCode);
            //验证验证码是否正确
            if (code.equals(validateCode)) {
                //赠送方账户
                String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
                //接收方账户
                String receiverIdCard = req.getParameter("receiverIdCard");
                String integralAccount2 = "101" + receiverIdCard;
                //赠送的积分值
                String integral = req.getParameter("integral");
                //赠送时间
                String time = TimeUtil.getDateTime();
                //流水号
                String serialId = TimeUtil.getDateTime();
                logger.info("积分转增值" + integral + "接收方账户" + integralAccount2 + "赠送方账户" + integralAccount + "" + "流水号" + serialId);
                //转增操作
                ReturnMessage message = IntegralService.transferIntegral(integralAccount, integralAccount2, integral);
                if (!message.getRetCode().equals("0000")) {
                    logger.error("积分系统转增失败");
                    map.put("token", token.toToken());
                    map.put("retCode","3420");
                    map.put("retMsg", message.getRetMsg());
                    return map;
                }
                //入到本地
                IntegralTransfer integralTransfer = new IntegralTransfer();
                integralTransfer.setGiverOpenId(integralAccount);
                integralTransfer.setReceiverIdCard(receiverIdCard);
                integralTransfer.setIntegral(Integer.parseInt(integral));
                integralTransfer.setTransferTime(time);
                integralTransfer.setSerialId(serialId);
                dao.addIntegralTransfer(integralTransfer);
                //修改短信验证码状态为已使用
                dao.updateStatusByPhone(phone);
                map.put("token", token.toToken());
                map.put("retMsg", "转增校验验证码成功");
                map.put("retCode", "0000");
                logger.info("转增成功");
            } else {
                map.put("token", token.toToken());
                map.put("retMsg", "转增校验验证码失败");
                map.put("retCode", "3410");
                logger.info("转增失败");
            }
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "校验验证码出错");
            throw new AppException("积分转增->校验验证码出错", e);
        }
        return map;
    }


    /**
     * 积分转增发送短信通知
     *
     * @param rep
     * @param req
     * @return
     */
    @SuppressWarnings("finally")
    public Map<String, Object> sendMessage(HttpServletResponse rep, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        //得到openid
        String tokenStr = req.getParameter("token");
        EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
        if (token == null) {
            map.put("token", token.toToken());
            map.put("retCode", "0001");
            map.put("retMsg", "用户需要重新登录");
            logger.info("token过期");
            return map;
        }
        try {
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            logger.info("积分账户" + integralAccount);
            //通过积分账户取得姓名
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("token", token.toToken());
                map.put("retCode","3406");
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String name = queryResBody.getCustName();
            //被赠送方手机号
            String phone = req.getParameter("phone");
            //短信内容
            String content = "您的好友" + name + "向您赠送了积分,请登录微信公众号的“积分账户”查看。";
            logger.info("姓名" + name + "被赠送方手机号" + phone + "短信内容" + content);
            //发送短信
            String result = SendMessageUtil.sendMessage(content, phone);
            ReturnMessage returnMessage = JSONObject.parseObject(result, ReturnMessage.class);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("token", token.toToken());
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "3421");
                return map;
            }
            map.put("token", token.toToken());
            map.put("retMsg", "积分转增发送短信成功");
            map.put("retCode", "0000");
            logger.info("发送短信成功");
        } catch (Exception e) {
            map.put("token", token.toToken());
            map.put("retCode", "9999");
            map.put("retMsg", "积分转增发送短信失败");
            logger.info("发送短信失败");
            throw new AppException("发送短信出错", e);
        } finally {
            return map;
        }
    }

    //发送短信验证码
    public ReturnMessage sendCode(String phone) {
        //生成短信验证码
        String code = RandomUtil.getRandomNumber(6);
        //验证码生成时间
        String startTime = TimeUtil.getDateTime();
        //短信验证码过期时间
        String overTime = TimeUtil.getAfterTime();
        //短信状态
        String validCodeStatus = ValidCode.VALID_CODE_STATUS_NORMAL;
        logger.info("生成的验证码" + code);
        ValidCode validCode = new ValidCode();
        validCode.setCode(code);
        validCode.setStartTime(startTime);
        validCode.setOverTime(overTime);
        validCode.setValidCodeStatus(validCodeStatus);
        validCode.setPhone(phone);
        //查询表中该手机号是否有验证码
        int count = dao.getCountByPhone(phone);
        logger.info("数据库中是否有手机号" + count);
        if (count > 0) {
            //修改验证码
            dao.updateCode(validCode);
        } else {
            //将短信入库
            dao.insert(validCode);
        }
        String codeMessage = "您的验证码为：" + code + "，请尽快输入，不要泄露。";
        //发送短信
        String result = SendMessageUtil.sendMessage(codeMessage, phone);
        ReturnMessage returnMessage = JSONObject.parseObject(result, ReturnMessage.class);
        return returnMessage;
    }
}