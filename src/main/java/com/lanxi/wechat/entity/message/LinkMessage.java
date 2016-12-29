package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-链接消息
 * @author 1
 *
 */
public class LinkMessage extends WechatMessage {
	private String title;		/**链接标题*/
	private String description;	/**链接描述*/
	private String url;			/**url链接*/
	public LinkMessage() {
		setMsgType(MESSAGE_TYPE_LINK);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("Titile").addCDATA(title);
		element.addElement("Description").addCDATA(description);
		element.addElement("Url").addCDATA(url);
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setTitle(root.elementText("Title"));
			setDescription(root.elementText("Description"));
			setUrl(root.elementText("Url"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}

}
