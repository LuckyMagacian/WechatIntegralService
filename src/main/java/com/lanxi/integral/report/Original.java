package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class Original {
	/**xml节点名称*/
	public static final String NAME="original";

	/**报文头*/
	private ReqHead head;
	/**报文信息*/
	private Body body;
	
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
	public static Original fromElement(Element element){
		Original original=null;
		if(element.getName().trim().equals(NAME)){
			original=new Original();
			original=new Original();
			original.setHead(ResHead.fromElement(element.element(ReqHead.NAME)));
//			original.setBody(new Body));
		}
		return original;
	}
}
