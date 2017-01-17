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
                //TODO 固定easyToken密钥
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
                        map.put("retCode", message.getRetCode());
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
                    map.put("retCode", "9999");
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
                map.put("retCode", message.getRetCode());
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
        } catch (Exception e) {
            throw new AppException("进入积分管理异常", e);
        }
        return map;
    }

    /**
     * 根据token获取用户信息
     */
    public Map<String, Object> getInfoByToken(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            logger.info("token==" + tokenStr);
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            String infoStr=UserManager.getWebUserInfo(openId);
            logger.info("获取到的用户信息:"+infoStr);
            webUserInfo.fromStr(infoStr);
            String headimgUrl = webUserInfo.getHeadImgUrl();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户取得姓名，积分值
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("retCode", message.getRetCode());
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
            map.put("retMsg", "根据token获取用户信息成功");
            logger.info("头像==" + headimgUrl + "姓名==" + name + "积分账户==" + integralAccount);
        } catch (Exception e) {
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
        try {
            req.setCharacterEncoding("utf-8");
            //得到openid
            String tokenStr = req.getParameter("token");
            logger.info("token==" + tokenStr);
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
            String nickname = webUserInfo.getNickName();
            //根据微信号取出身份证号和手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String id = accountBinding.getIntegralAccount();
            String idCrad = id.substring(3, id.length());
            String phone = accountBinding.getBindingPhone();
            map.put("idCard", idCrad);
            map.put("phone", phone);
            map.put("nickname", nickname);
            map.put("retCode", "0000");
            map.put("retMsg", "进入账户资料页面成功");
            logger.info("进入账户资料页面成功，身份证==" + idCrad + "微信名==" + nickname + "手机号" + phone);
        } catch (Exception e) {
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
        try {
            String tokenStr = req.getParameter("token");
            logger.info("token==" + tokenStr);
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号删除记录
            bindingService.cancelBindings(openId);
            map.put("retCode", "0000");
            map.put("retMsg", "取消绑定成功");
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "取消绑定失败");
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
        try {
            String tokenStr = req.getParameter("token");
            logger.info("token==" + tokenStr);
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号取出手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            map.put("local", "China");
            map.put("phone", bindingPhone);
            map.put("retCode", "0000");
            map.put("retMsg", "进入更换手机号码页面成功");
            logger.info("进入更改手机号码页面成功手机号" + bindingPhone + "微信号" + openId);
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "进入更换手机号码页面失败");
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
        try {
            logger.info("用户获取短信验证码!~");
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
            }
            String openId = token.getInfo();
            //根据微信号取出原手机号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String bindingPhone = accountBinding.getBindingPhone();
            logger.info("老手机号" + bindingPhone);
            String phone = req.getParameter("phone");
            logger.info("新手机号" + phone);
            //判断手机号是否与原手机号相同
            if (phone.equals(bindingPhone)) {
                map.put("retCode", "9999");
                map.put("retMsg", "手机号与原手机号相同");
                logger.info("手机号码与原来的相同");
                return map;
            }
            //发送短信验证码
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "9999");
                return map;
            }
            map.put("retCode", "0000");
            map.put("retMsg", "更换手机号码发送验证码操作成功");
            logger.info("发送验证码成功");
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "更改手机号发送验证码失败");
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //根据微信号取出身份证号和积分账号
            AccountBinding accountBinding = bindingService.getMessage(openId);
            String integralAccount = accountBinding.getIntegralAccount();
            logger.info("积分账户" + integralAccount);
            String phone = req.getParameter("phone");
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("retCode", "9999");
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
                    map.put("retCode", "9999");
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
                    map.put("retCode", message.getRetCode());
                    map.put("retMsg", message.getRetMsg());
                    return map;
                }
                //修改绑定表中的手机号
                bindingService.updatePhone(phone, openId);
                logger.info("修改结果" + message);
                map.put("retCode", "0000");
                map.put("retMsg", "更改手机号校验验证码成功");
                logger.info("修改手机号码成功");
                logger.info("新手机号" + phone);
            } else {
                map.put("retCode", "9999");
                map.put("retMsg", "验证码错误");
                logger.info("验证码错误");
            }
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "更改手机号校验验证码失败");
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String phone = req.getParameter("phone");
            //验证手机号是否符合标准
            if (null == phone || phone.equals("") || !phone.matches("^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$")) {
                map.put("retCode", "9999");
                map.put("retMsg", "手机号不符合标准");
                logger.info("手机号不符合标准");
                return map;
            }
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "9999");
                return map;
            }
            map.put("retCode", "0000");
            map.put("retMsg", "绑定积分页面发送验证码成功");
            logger.info("发送验证码成功");
        } catch (Exception e) {
            map.put("retCode", "9999");
            map.put("retMsg", "绑定积分页面发送验证码失败");
            logger.info("发送验证码失败");
            throw new AppException("绑定积分页面发送验证码出错", e);
        }
        return map;
    }

    //绑定积分账户操作(校验验证码)
    public Map<String, Object> bindingsJfOper(HttpServletRequest req, HttpServletResponse rep) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            logger.info("token" + tokenStr + "微信号" + openId);
            //根据openid得到取出用户详情
            WebUserInfo webUserInfo = new WebUserInfo();
            webUserInfo.fromStr(UserManager.getWebUserInfo(openId));
            //头像
            String headimgUrl = webUserInfo.getHeadImgUrl();
            String phone = req.getParameter("phone");
            String name = req.getParameter("name");
            String idCard = req.getParameter("idcard");
            String integralAccount = "101" + idCard;
            logger.info("手机号" + phone + "姓名" + name + "积分账户" + integralAccount);
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("retCode", "9999");
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
                    map.put("retCode", "9999");
                    map.put("retMsg", "该手机号已经绑定其他积分账号");
                    logger.info("该手机号已经绑定其他积分账号");
                    return map;
                }
                //该身份证是否已经存在积分账户
                ReturnMessage message2 = IntegralService.queryIntegral(integralAccount);
                if (!message2.getRetCode().equals("0000")) {
                    logger.error("该身份证号没有对应的积分账户");
                    map.put("retCode", message2.getRetCode());
                    map.put("retMsg", "该身份证号没有对应的积分账户");
                    return map;
                }
                //修改短信验证码状态为已使用
                dao.updateStatusByPhone(phone);
                AccountBinding accountBinding = new AccountBinding();
                accountBinding.setOpenId(openId);
                accountBinding.setBindingPhone(phone);
                accountBinding.setHeadimgUrl(headimgUrl);
                accountBinding.setIntegralAccount(integralAccount);

                //手机号入到积分表
                ReturnMessage message = IntegralService.modifyPhone(integralAccount, phone);
                if (!message.getRetCode().equals("0000")) {
                    logger.error("手机号入到积分系统失败");
                    map.put("retCode", message.getRetCode());
                    map.put("retMsg", message.getRetMsg());
                    return map;
                }
                logger.info("手机号入表结果" + message);
                //绑定账号插入表中
                bindingService.insert(accountBinding);
                //通过积分账户取得积分值
                ReturnMessage returnMessage = IntegralService.queryIntegral(integralAccount);
                if (!returnMessage.getRetCode().equals("0000")) {
                    logger.error("取积分值和姓名失败");
                    map.put("retCode", returnMessage.getRetCode());
                    map.put("retMsg", returnMessage.getRetMsg());
                    return map;
                }
                QueryResBody queryResBody = (QueryResBody) returnMessage.getObj();
                String integralValue = queryResBody.getTotalPoints();
                logger.info("积分值" + integralValue);
                map.put("retCode", "0000");
                map.put("retMsg", "绑定积分账户校验验证码成功");
                logger.info("绑定积分账户校验验证码成功");
            } else {
                map.put("retCode", "9999");
                map.put("retMsg", "绑定积分账户校验验证码失败");
                logger.info("绑定积分账户校验验证码失败");
            }
        } catch (Exception e) {
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //得到六个月前的日期
            String startDate = TimeUtil.getBeforeDate();
            logger.info("积分账户==" + integralAccount + "六个月前的日期" + startDate);
            //查询明细
            ReturnMessage message = IntegralService.historyIntegral(integralAccount, startDate);
            if (!message.getRetCode().equals("0000")) {
                logger.error("查询积分明细失败");
                map.put("retCode", message.getRetCode());
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            logger.info("积分明细" + message.getObj());
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
                itemMap.put("occurDate", occurDate);
                itemMap.put("pointsVal", pointsVal);
                itemMap.put("pointType", pointType);
                mapList.add(itemMap);
            }
            map.put("message", mapList);
            map.put("retCode", "0000");
            map.put("retMsg", "查询积分明细成功");
            logger.info("查询积分明细成功");
        } catch (Exception e) {
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //积分转增值
            String integral = req.getParameter("integral");
            //接收方身份证号
            String receiverIdCard = req.getParameter("receiverIdCard");
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            if (integralAccount.equals("101" + receiverIdCard)) {
                map.put("retCode", "9999");
                map.put("retMsg", "不能转增给自己");
                return map;
            }
            //通过积分账户取得积分值
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("retCode", message.getRetCode());
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String integralValue = queryResBody.getTotalPoints();
            logger.info("积分转增值" + integral + "接收方身份证号" + receiverIdCard + "积分账户" + integralAccount + "" + "现有积分值" + integralValue);
            int integral2 = Integer.parseInt(integral);
            int integralValue1 = Integer.parseInt(integralValue);
            if (integral2 > integralValue1) {
                map.put("retCode", "9999");
                map.put("retMsg", "您的积分余额不足");
                logger.info("您的积分余额不足");
                return map;
            }
            String integralAccount2 = "101" + receiverIdCard;
            //查询接受账户是否存在
            int count = bindingService.getCountByIntegralAccount(integralAccount2);
            if (count < 1) {
                map.put("retCode", "9999");
                map.put("retMsg", "该身份证号没有匹配的积分账号");
                logger.info("该身份证号没有匹配的积分账号");
                return map;
            }
            //通过积分账户查询绑定的手机号
            String phone = bindingService.getPhoneByIntegralAccount(integralAccount);
            //发送验证码
            ReturnMessage returnMessage = sendCode(phone);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("retMsg", "发送验证码失败");
                map.put("retCode", "9999");
                return map;
            }
            map.put("retCode", "0000");
            map.put("retMsg", "积分转增发送验证码成功");
            logger.info("积分转增发送验证码成功");
        } catch (Exception e) {
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            String phone = req.getParameter("phone");
            //得到验证码状态
            String status = dao.getStatusByPhone(phone);
            if (status.equals(ValidCode.VALID_CODE_STATUS_OVERTIME) || status.equals(ValidCode.VALID_CODE_STATUS_USED)) {
                map.put("retCode", "9999");
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
                logger.info("积分转增值" + integral + "接收方" + integralAccount2 + "赠送方账户" + integralAccount + "" + "流水号" + serialId);
                //修改短信验证码状态为已使用
                dao.updateStatusByPhone(phone);
                //转增操作
                ReturnMessage message = IntegralService.transferIntegral(integralAccount, integralAccount2, integral);
                if (!message.getRetCode().equals("0000")) {
                    logger.error("积分系统转增失败");
                    map.put("retCode", message.getRetCode());
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
                map.put("retMsg", "转增校验验证码成功");
                map.put("retCode", "0000");
                logger.info("转增成功");
            } else {
                map.put("retMsg", "转增校验验证码失败");
                map.put("retCode", "9999");
                logger.info("转增失败");
            }
        } catch (Exception e) {
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
        try {
            //得到openid
            String tokenStr = req.getParameter("token");
            EasyToken token = EasyToken.verifyTokenRenew(tokenStr);
            if (token == null) {
                map.put("retCode", "9797");
                map.put("retMsg", "用户需要重新登录");
                logger.info("token过期");
                return map;
            }
            String openId = token.getInfo();
            //通过openid取出积分账户
            String integralAccount = bindingService.getMessage(openId).getIntegralAccount();
            //通过积分账户取得姓名
            ReturnMessage message = IntegralService.queryIntegral(integralAccount);
            if (!message.getRetCode().equals("0000")) {
                logger.error("取积分值和姓名失败");
                map.put("retCode", message.getRetCode());
                map.put("retMsg", message.getRetMsg());
                return map;
            }
            QueryResBody queryResBody = (QueryResBody) message.getObj();
            String name = queryResBody.getCustName();
            //被赠送方手机号
            String phone = req.getParameter("phone");
            //短信内容
            String content = "您的好友" + name + "向您赠送了积分，点此查看xxx（微信积分服务平台连接）";
            logger.info("姓名" + name + "被赠送方手机号" + phone + "短信内容" + content);
            //发送短信
            String result = SendMessageUtil.sendMessage(content, phone);
            ReturnMessage returnMessage = JSONObject.parseObject(result, ReturnMessage.class);
            if (!returnMessage.getRetCode().equals("0000")) {
                map.put("retMsg", "发送短信失败");
                map.put("retCode", "9999");
                return map;
            }
            map.put("retMsg", "积分转增发送短信成功");
            map.put("retCode", "0000");
            logger.info("发送短信成功");
        } catch (Exception e) {
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
        String codeMessage = "【蓝喜微管家】您的验证码为：" + code + "，请尽快输入，不要泄露。";
        //发送短信
        String result = SendMessageUtil.sendMessage(codeMessage, phone);
        ReturnMessage returnMessage = JSONObject.parseObject(result, ReturnMessage.class);
        return returnMessage;
    }
}