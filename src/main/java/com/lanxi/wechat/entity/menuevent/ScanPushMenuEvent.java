package com.lanxi.wechat.entity.menuevent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class ScanPushMenuEvent extends WechatMenuEvent {
	public static class ScanCodeInfo{
		private String scanType;
		private String scanResult;
		
		public String getScanType() {
			return scanType;
		}
		public void setScanType(String scanType) {
			this.scanType = scanType;
		}
		public String getScanResult() {
			return scanResult;
		}
		public void setScanResult(String scanResult) {
			this.scanResult = scanResult;
		}
		public DOMElement toElement() {
			DOMElement element=new DOMElement("ScanCodeInfo");
			element.addElement("ScanType").addCDATA(scanType);
			element.addElement("ScanResult").addCDATA(scanResult);
			return element;
		}
		public void fromElement(Element ele){
			setScanType(ele.elementText("ScanType"));
			setScanResult(ele.elementText("ScanResult"));
		}
	}
	private ScanCodeInfo codeInfo;
	public ScanPushMenuEvent() {
		setMsgType(MENU_EVENT_SCANCODE_PUSH);
		codeInfo=new ScanCodeInfo();
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
	public ScanCodeInfo getCodeInfo() {
		return codeInfo;
	}
	public void setCodeInfo(ScanCodeInfo codeInfo) {
		this.codeInfo = codeInfo;
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
