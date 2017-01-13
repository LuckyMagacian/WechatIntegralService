package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.AccountBinding;

public interface AccountBindingDao {
	public void addAccountBinding(AccountBinding accountBinding);
	public void addAccountBindingDefault(AccountBinding accountBinding);
	public void deleteAccountBinding(AccountBinding accountBinding);
	public void updateAccountBinding(AccountBinding accountBinding);
	public List<AccountBinding> selectAccountBinding(AccountBinding accountBinding);
	//根据openid查询微信号是否绑定积分账户
	int getCountByOpenId(String openId);
	//根据手机号查询该手机号是否绑定积分账户
	int getCountByPhone(String phone);
	//根据微信号取出积分账号和手机号
	AccountBinding getMessage(String openId);
	//查询身份证号是否已经绑定积分账户
	int getCountByIntegralAccount(String integralAccount);
	//通过积分账号查询绑定的手机号
	String getPhoneByIntegralAccount(String integralAccount);
	//根据微信号删除记录
	void cancelBindings(String openId);
	//绑定积分账户入库
	void insert(AccountBinding accountBinding);
}
