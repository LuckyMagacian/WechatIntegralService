package com.lanxi.gift.report;

import java.util.List;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.CheckReplaceUtil;

/**
 * 流量兑换业务响应信息
 * @author 1
 *
 */
public class ResMsg implements Msg{
	private String totalAmt;	/**订单总价*/
	private List<Sku> skuList;	/**购买结果*/
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public List<Sku> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}
	@Override
	public String toString() {
		return "ResMsgBean [totalAmt=" + totalAmt + ", skuList=" + skuList + "]";
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("TotalAmt").setText(CheckReplaceUtil.nullAsSpace(totalAmt));
		Element list=element.addElement("SkuList");
		for(Sku each:skuList){
			list.addElement("Amt").setText(CheckReplaceUtil.nullAsSpace(each.getAmt()));
			list.addElement("Code").setText(CheckReplaceUtil.nullAsSpace(each.getCode()));
			list.addElement("EndTime").setText(CheckReplaceUtil.nullAsSpace(each.getEndTime()));
		}
		return element;
	}
//	@Override
	public static Msg fromElement(Element element) {
		Msg msg=null;
		if(element!=null&&element.getName().equals(NAME)){
			msg=new ResMsg();
			ResMsg resMsg=(ResMsg)msg;
			resMsg.setTotalAmt(element.element("TotalAmt").getText());
			Element list=element.element("SkuList");
			for(Object one:list.elements()){
				Element each=(Element)one;
				Sku temp=new Sku();
				temp.setAmt(each.elementText("Amt"));
				temp.setCode(each.elementText("Code"));
				temp.setEndTime(each.elementText("EndTime"));
				resMsg.getSkuList().add(temp);
			}
		}
		return null;
	}
	
}
