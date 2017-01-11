package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.ValidCode;

public interface ValidCodeDao {
	public void addValidCode(ValidCode validCode);
	public void addValidCodeDefault(ValidCode validCode);
	public void deleteValidCode(ValidCode validCode);
	public void updateValidCode(ValidCode validCode);
	public List<ValidCode> selectValidCode(ValidCode validCode);
	/**
	 * 短信验证码入库
	 * @param validCode
	 */
	void insert(ValidCode validCode);
	/**
	 * 取出验证码
	 */
	String getCodeByPhone(String phone);
	/**
	 * 修改验证码状态
	 *
	 */
	void updateStatusByPhone(String phone);
	/**
	 * 得到验证码的状态
	 */
	String getStatusByPhone(String phone);
	/**
	 * 修改过期验证码状态
	 */
	void updateStatusByTime(String time);
	/**
	 * 删除过期验证码
	 *
	 */
	void deleteCode();
	/**
	 * 查询数据库中是否有该手机号
	 */
	int getCountByPhone(String phone);
	/**
	 * 修改验证码
	 */
	void updateCode(ValidCode validCode);
}
