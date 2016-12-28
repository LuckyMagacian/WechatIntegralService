package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class ImageMessage extends WechatMessage {
	private String mediaId;
	private String picUrl;
	public ImageMessage() {
		setMsgType(MESSAGE_TYPE_IMAGE);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("MediaId").setText(mediaId);
		element.addElement("PicUrl").addCDATA(picUrl);
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setMediaId(root.elementText("MediaId"));
			setPicUrl(root.elementText("PicUrl"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
