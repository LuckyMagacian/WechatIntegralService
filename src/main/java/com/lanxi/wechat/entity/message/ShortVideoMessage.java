package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-小视频消息
 * @author 1
 *
 */
public class ShortVideoMessage extends WechatMessage {
	/**媒体素材编号*/
	private String mediaId;		
	/**媒体素材缩略图*/
	private String thumbMediaId;
	public ShortVideoMessage() {
		setMsgType(MESSAGE_TYPE_SHORT_VIDEO);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("MediaId").addCDATA(mediaId);
		element.addElement("ThumbMediaId").addCDATA(thumbMediaId);
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setMediaId(root.elementText("MediaId"));
			setThumbMediaId(root.elementText("ThumbMediaId"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
