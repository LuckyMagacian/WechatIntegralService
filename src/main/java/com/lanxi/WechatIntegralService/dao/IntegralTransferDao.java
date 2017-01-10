package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.IntegralTransfer;

public interface IntegralTransferDao {
	public void addIntegralTransfer(IntegralTransfer integralTransfer);
	public void addIntegralTransferDefault(IntegralTransfer integralTransfer);
	public void deleteIntegralTransfer(IntegralTransfer integralTransfer);
	public void updateIntegralTransfer(IntegralTransfer integralTransfer);
	public List<IntegralTransfer> selectIntegralTransfer(IntegralTransfer integralTransfer);
	/**
	 * 赠送记录入库
	 * @param integralTransfer
	 */
	void insert(IntegralTransfer integralTransfer);
}
