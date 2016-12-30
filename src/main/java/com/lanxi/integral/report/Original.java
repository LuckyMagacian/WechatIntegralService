package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class Original {
	/**xml节点名称*/
	public static final String NAME="original";

	/**报文头*/
	private ReqHead head;
	/**报文信息*/
	private Body body;
	public Original(){
		
	}
	public Original(Class<?extends ReqHead> headClass,Class<? extends Body> bodyClass){
		try {
			head=headClass.newInstance();
			body=bodyClass.newInstance();
		} catch (Exception e) {
			throw new AppException("实例化报文异常",e);
		}
		
	}
	public ReqHead getHead() {
		return head;
	}

	public void setHead(ReqHead head) {
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.add(head.toElement());
		element.add(body.toElement());
		return element;
	}
//	public  Original fromElement(Element element){
//		if(element.getName().trim().equals(NAME)){
//			setHead(ResHead.fromElement(element.element(ReqHead.NAME)));
////			original.setBody(new Body));
//		}
//		return original;
//	}
}
