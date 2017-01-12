package com.lanxi.integral.report;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分查询-响应消息体
 * @author 1
 *
 */
public class QueryResBody extends Body{
	/**
	 * 积分明细条目
	 * @author 1
	 *
	 */
	public static class PointsItem{
		/**积分过期时间*/
		private String deadline;
		/**积分值*/
		private String points;
		public String getDeadline() {
			return deadline;
		}
		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}
		public String getPoints() {
			return points;
		}
		public void setPoints(String points) {
			this.points = points;
		}
		public Element toElement(){
			DOMElement element=new DOMElement(NAME);
			element.addElement("deadline").setText(deadline);
			element.addElement("points").setText(points);
			return element;
		}
		public PointsItem fromElement(Element element){
			setDeadline(element.elementText("deadline"));
			setPoints(element.elementText("points"));
			return this;
		}
	}
	/**客户名称*/
	private String custName;
	/**客户积分值*/
	private String totalPoints;
	/**积分列表*/
	private List<PointsItem> detailList;
	public QueryResBody(){
		detailList= new ArrayList<>();
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	public List<PointsItem> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<PointsItem> detailList) {
		this.detailList = detailList;
	}
	public void addPointsItem(PointsItem item){
		detailList.add(item);
	}
	
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("custName").setText(getCustName());
		element.addElement("totalPoints").setText(getTotalPoints());
		DOMElement detail=new DOMElement("detailList");
		for(PointsItem each:detailList)
			detail.add(each.toElement());
		element.add(detail);
		return element;
	}
	public Body fromElement(Element element){
		setCustName(element.elementText("custName"));
		setTotalPoints(element.elementText("totalPoints"));
		for(Object each:element.element("detailList").elements()){
			Element one=(Element) each;
			PointsItem item=new PointsItem();
			detailList.add(item.fromElement(one));
		}
		return this;
	}
}
