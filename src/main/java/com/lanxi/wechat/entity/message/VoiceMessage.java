package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-语音消息
 * @author 1
 *
 */
public class VoiceMessage extends WechatMessage{
	private String mediaId;	/**语音素材编号*/
	private String format;	/**语音格式*/
	private String recognition;/**语音识别结果*///该属性只有开通语音识别后才会出现
	public VoiceMessage() {
		setMsgType(MESSAGE_TYPE_VOICE);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("MediaId").addCDATA(mediaId);
		element.addElement("Format").addCDATA(format);
		element.addElement("Recognition").addCDATA(recognition==null?"":recognition);
		return element;
	}

	@Override
	public void  fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setMediaId(root.elementText("MediaId"));
			setFormat(root.elementText("Format"));
			setRecognition(root.elementText("Recognition"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
