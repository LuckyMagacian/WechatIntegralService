package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.AccountBinding;

public interface AccountBindingDao {
	public void addAccountBinding(AccountBinding accountBinding);
	public void addAccountBindingDefault(AccountBinding accountBinding);
	public void deleteAccountBinding(AccountBinding accountBinding);
	public void updateAccountBinding(AccountBinding accountBinding);
	public List<AccountBinding> selectAccountBinding(AccountBinding accountBinding);
}
