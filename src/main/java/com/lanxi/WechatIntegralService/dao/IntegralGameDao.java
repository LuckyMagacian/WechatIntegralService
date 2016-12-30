package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.IntegralGame;

public interface IntegralGameDao {
	public void addIntegralGame(IntegralGame integralGame);
	public void addIntegralGameDefault(IntegralGame integralGame);
	public void deleteIntegralGame(IntegralGame integralGame);
	public void updateIntegralGame(IntegralGame integralGame);
	public List<IntegralGame> selectIntegralGame(IntegralGame integralGame);
}
