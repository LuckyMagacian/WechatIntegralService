package com.lanxi.integral.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分明细查询-响应消息体
 * @author 1
 *
 */
public class HistoryResBody extends Body{
	/**积分类别-增加*/
	public static final String POINTS_TYPE_ADD="0000";
	/**积分类别-转赠增加*/
	public static final String POINTS_TYPE_TRANSFER_ADD="0001";
	/**积分类别-积分合并增加*/
	public static final String POINTS_TYPE_MERGE_ADD="0002";
	/**积分类别-积分调整增加*/
	public static final String POINTS_TYPE_REQ_ADD="0003";
	/**积分类别-积分规则增加*/
	public static final String POINTS_TYPE_RULE_CHANGE_ADD="0004";
	/**积分类别-特殊增加*/
	public static final String POINTS_TYPE_SPECIAL_ADD="0005";
	/**积分类别-接口增加*/
	public static final String POINTS_TYPE_API_ADD="0006";
	/**积分类别-扣减*/
	public static final String POINTS_TYPE_REDUCE="1000";
	/**积分类别-转赠扣减*/
	public static final String POINTS_TYPE_TRANSFER_REDUCE="1001";
	/**积分类别-合并扣减*/
	public static final String POINTS_TYPE_MERGE_REDUCE="10002";
	/**积分类别-申请调整扣减*/
	public static final String POINTS_TYPE_REQ_REDUCE="1003";
	/**积分类别-积分兑换扣减*/
	public static final String POINTS_TYPE_EXCHANGE_REDUCE="1004";
	/**积分类别-积分规则扣减*/
	public static final String POINTS_TYPE_RULE_REDUCE="1005";
	/**积分类别-接口扣减*/
	public static final String POINTS_TYPE_API_REDUCE="1006";
	
	public static final Map<String, String> POINTS_TYPE_MAP=new HashMap<>();
	static{
		POINTS_TYPE_MAP.put("0000", "增加");
		POINTS_TYPE_MAP.put("0001", "接收积分转赠增加");
		POINTS_TYPE_MAP.put("0002", "积分合并增加");
		POINTS_TYPE_MAP.put("0003", "积分调整增加");
		POINTS_TYPE_MAP.put("0004", "积分规则增加");
		POINTS_TYPE_MAP.put("0005", "特殊增加");
		POINTS_TYPE_MAP.put("0006", "积分接口增加");
		POINTS_TYPE_MAP.put("1000", "扣减");
		POINTS_TYPE_MAP.put("1001", "积分转赠扣减");
		POINTS_TYPE_MAP.put("1002", "积分合并扣减");
		POINTS_TYPE_MAP.put("1003", "积分调整扣减");
		POINTS_TYPE_MAP.put("1004", "积分兑换扣减");
		POINTS_TYPE_MAP.put("1005", "积分规则扣减");
		POINTS_TYPE_MAP.put("1006", "积分接口扣减");
	}
	public static class Item{
		/**流水号*/
		private String serialNo;
		/**发生日期*/
		private String occurDate;
		/**发生时间*/
		private String occurTime;;
		/**积分类别*/
		private String pointsType;
		/**积分分值*/
		private String pointsVal;
		/**前置机构*/
		private String orgNo;
		/**前置流水*/
		private String frontSerialNo;
		public String getSerialNo() {
			return serialNo;
		}
		public void setSerialNo(String serialNo) {
			this.serialNo = serialNo;
		}
		public String getOccurDate() {
			return occurDate;
		}
		public void setOccurDate(String occurDate) {
			this.occurDate = occurDate;
		}
		public String getOccurTime() {
			return occurTime;
		}
		public void setOccurTime(String occurTime) {
			this.occurTime = occurTime;
		}
		public String getPointsType() {
			return pointsType;
		}
		public void setPointsType(String pointsType) {
			this.pointsType = pointsType;
		}
		public String getPointsVal() {
			return pointsVal;
		}
		public void setPointsVal(String pointsVal) {
			this.pointsVal = pointsVal;
		}
		public String getOrgNo() {
			return orgNo;
		}
		public void setOrgNo(String orgNo) {
			this.orgNo = orgNo;
		}
		public String getFrontSerialNo() {
			return frontSerialNo;
		}
		public void setFrontSerialNo(String frontSerialNo) {
			this.frontSerialNo = frontSerialNo;
		}
		public Element toElement() {
			DOMElement element=new DOMElement(NAME);
			element.addElement("serialNo").setText(serialNo);
			element.addElement("occurDate").setText(occurDate);
			element.addElement("occurTime").setText(occurTime);
			element.addElement("pointsType").setText(pointsType);
			element.addElement("pointsVal").setText(pointsVal);
			element.addElement("orgNo").setText(orgNo);
			element.addElement("frontSerialNo").setText(frontSerialNo);
			return element;
		}
		public Item fromElement(Element element) {
			setFrontSerialNo(element.elementText("serialNo"));
			setOccurDate(element.elementText("occurDate"));
			setOccurTime(element.elementText("occurTime"));
			setOrgNo(element.elementText("orgNo"));
			setPointsType(element.elementText("pointsType"));
			setPointsVal(element.elementText("pointsVal"));
			setFrontSerialNo(element.elementText("frontSerialNo"));
			return this;
		}
	}
	private List<Item> serialList;
	public HistoryResBody() {
		serialList=new ArrayList<>();
	}
	public List<Item> getSerialList() {
		return serialList;
	}
	public void setSerialList(List<Item> serialList) {
		this.serialList = serialList;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		for(Item each:serialList)
			element.add(each.toElement());
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		for(Object each:element.element("serialList").elements()){
			Element one=(Element) each;
			serialList.add(new Item().fromElement(one));
		}
		return this;
	}
}
