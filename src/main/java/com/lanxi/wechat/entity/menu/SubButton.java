package com.lanxi.wechat.entity.menu;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class SubButton implements WechatButton{
	private String name;
	private List<BaseButton> subButton;
	public SubButton() {
		subButton=new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BaseButton> getSubButton() {
		return subButton;
	}

	public void setSubButton(List<BaseButton> buttons) {
		this.subButton = buttons;
	}
	

	public void addButton(BaseButton button){
		subButton.add(button);
	}
	public String toJson(){
		return JSONObject.toJSONString(this).replaceFirst("subButton","sub_button");
	}
}
