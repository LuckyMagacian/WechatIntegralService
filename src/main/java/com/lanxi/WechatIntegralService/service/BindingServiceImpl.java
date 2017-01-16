package com.lanxi.WechatIntegralService.service;

import com.lanxi.WechatIntegralService.dao.AccountBindingDao;
import com.lanxi.WechatIntegralService.entity.AccountBinding;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/1/3.
 */
@Service("bindingService")
public class BindingServiceImpl implements BindingService {
    @Resource
    AccountBindingDao accountBindingDao;

    //根据openid查询微信号是否绑定积分账户
    @Override
    public int getCountByOpenId(String openId) {
        return accountBindingDao.getCountByOpenId(openId);
    }

    //根据手机号查询该手机号是否绑定积分账户
    @Override
    public int getCountByPhone(String phone) {
        return accountBindingDao.getCountByPhone(phone);
    }
    //根据微信号取出积分账号和手机号
    @Override
    public AccountBinding getMessage(String openId) {
        return accountBindingDao.getMessage(openId);
    }
    //查询身份证号是否已经绑定积分账户
    @Override
    public int getCountByIntegralAccount(String integralAccount) {
        return accountBindingDao.getCountByIntegralAccount( integralAccount);
    }

    @Override
    public String getPhoneByIntegralAccount(String integralAccount) {
        return accountBindingDao.getPhoneByIntegralAccount(integralAccount);
    }

    @Override
    public void cancelBindings(String openId) {
        accountBindingDao.cancelBindings(openId);
    }

    @Override
    public void insert(AccountBinding accountBinding) {
        accountBindingDao.insert(accountBinding);
    }

    @Override
    public void updatePhone(@Param("phone") String phone, @Param("openId") String openId) {
        accountBindingDao.updatePhone(phone,openId);
    }
}
