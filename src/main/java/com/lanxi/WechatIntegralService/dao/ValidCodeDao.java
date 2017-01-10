package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.ValidCode;

public interface ValidCodeDao {
	public void addValidCode(ValidCode validCode);
	public void addValidCodeDefault(ValidCode validCode);
	public void deleteValidCode(ValidCode validCode);
	public void updateValidCode(ValidCode validCode);
	public List<ValidCode> selectValidCode(ValidCode validCode);
}
