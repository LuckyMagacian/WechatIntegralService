package com.lanxi.wechat.entity.automessage;


import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-自动消息类-自动回复图文消息类
 * @author 1
 *
 */
public class NewsAutoMessage extends WechatAutoMessage {
	/**
	 * 自动回复图文消息类-内部类-文章
	 * @author 1
	 *
	 */
	public class Item{
		/**文章标题*/
		private String title;		
		/**文章描述*/
		private String description;	
		/**文章图片*/
		private String picUrl;		
		/**文章链接*/
		private String url;			
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
		public String getPicUrl() {
			return picUrl;
		}
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public DOMElement toElement() {
			DOMElement element=new DOMElement("Item");
			element.addElement("Title").addCDATA(title==null?"":title);
			element.addElement("Description").addCDATA(description==null?"":description);
			element.addElement("PicUrl").addCDATA(picUrl);
			element.addElement("Url").addCDATA(url);
			return element;
		}
		public void fromElement(Element ele){
			setTitle(ele.elementText("Title"));
			setDescription(ele.elementText("Description"));
			setPicUrl(ele.elementText("PicUrl"));
			setUrl(ele.elementText("Url"));
		}
	}
	/**文章数量*/
	private String articleCount;
	/**文章列表*/
	private List<NewsAutoMessage.Item> articles;
	
	public NewsAutoMessage() {
		setMsgType(AUTO_MESSAGE_TYPE_NEWS);
		articles=new ArrayList<>();
	}
	
	public List<NewsAutoMessage.Item> getArticles() {
		return articles;
	}
	
	public String getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

	public void setArticles(List<NewsAutoMessage.Item> articles) {
		this.articles = articles;
	}

	public void addArticle(NewsAutoMessage.Item article){
		articles.add(article);
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("ArticleCount").setText(articleCount);
		DOMElement temp=new DOMElement("Articles");
		for(Item each:articles)
			temp.add(each.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setArticleCount(root.elementText("ArticleCount"));
			Element temp=root.element("Articles");
			for(Object each:temp.elements()){
				Item one=new Item();
				one.fromElement((Element) each);
				articles.add(one);
			}
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
