package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.ElectronicCoupon;

public interface ElectronicCouponDao {
	public void addElectronicCoupon(ElectronicCoupon electronicCoupon);
	public void addElectronicCouponDefault(ElectronicCoupon electronicCoupon);
	public void deleteElectronicCoupon(ElectronicCoupon electronicCoupon);
	public void updateElectronicCoupon(ElectronicCoupon electronicCoupon);
	public List<ElectronicCoupon> selectElectronicCoupon(ElectronicCoupon electronicCoupon);
}
