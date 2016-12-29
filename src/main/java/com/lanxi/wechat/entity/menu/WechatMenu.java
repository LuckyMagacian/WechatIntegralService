package com.lanxi.wechat.entity.menu;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
/**
 * 微信菜单类
 * @author 1
 *
 */
public class WechatMenu {
	private List<WechatButton> button;	
	
	public WechatMenu() {
		button=new ArrayList<>();
	}
	public List<WechatButton> getButton() {
		return button;
	}
	public void setButton(List<WechatButton> button) {
		this.button = button;
	}
	public void addButton(WechatButton button){
		this.button.add(button);
	}
	public String toJson(){
		return JSONObject.toJSONString(this).replace("subButton","sub_button");
	}
}
