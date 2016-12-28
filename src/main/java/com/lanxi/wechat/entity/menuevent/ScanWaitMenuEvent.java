package com.lanxi.wechat.entity.menuevent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.wechat.entity.menuevent.ScanPushMenuEvent.ScanCodeInfo;

public class ScanWaitMenuEvent extends WechatMenuEvent {
	private ScanCodeInfo codeInfo;
	public ScanWaitMenuEvent() {
		setMsgType(MENU_EVENT_SCANCODE_WAITMSG);
		codeInfo=new ScanCodeInfo();
	}
	
	public ScanCodeInfo getCodeInfo() {
		return codeInfo;
	}
	public void setCodeInfo(ScanCodeInfo codeInfo) {
		this.codeInfo = codeInfo;
	}
	public String getScanType() {
		return codeInfo.getScanType();
	}
	public void setScanType(String scanType) {
		this.codeInfo.setScanType(scanType); 
	}
	public String getScanResult() {
		return codeInfo.getScanResult();
	}
	public void setScanResult(String scanResult) {
		this.codeInfo.setScanResult(scanResult);
	}
	@Override
	public DOMElement toElement() {
		DOMElement ele=super.toElement();
		ele.add(codeInfo.toElement());
		return ele;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			codeInfo.fromElement(root.element("ScanCodeInfo"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
}
