package com.lanxi.integral.service;
import static com.lanxi.WechatIntegralService.util.CheckReplaceUtil.nullAsSpace;
import static com.lanxi.WechatIntegralService.util.RandomUtil.getRandomNumber;
import static com.lanxi.WechatIntegralService.util.TimeUtil.getDate;
import static com.lanxi.WechatIntegralService.util.TimeUtil.getDateTime;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.httpsclient.core.HttpsClient;
import com.lanxi.integral.report.AddReqBody;
import com.lanxi.integral.report.AddResBody;
import com.lanxi.integral.report.Body;
import com.lanxi.integral.report.HistoryReqBody;
import com.lanxi.integral.report.HistoryResBody;
import com.lanxi.integral.report.JFBaoWen;
import com.lanxi.integral.report.JFPoints;
import com.lanxi.integral.report.ModifyPhoneReqBody;
import com.lanxi.integral.report.ModifyPhoneResBody;
import com.lanxi.integral.report.Original;
import com.lanxi.integral.report.QueryReqBody;
import com.lanxi.integral.report.QueryResBody;
import com.lanxi.integral.report.ReduceReqBody;
import com.lanxi.integral.report.ReduceResBody;
import com.lanxi.integral.report.ReqHead;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.report.ReversalReqBody;
import com.lanxi.integral.report.TransferReqBody;
public class IntegralService {
	private static JFBaoWen baoWen=new JFBaoWen();
	private static Original original=new Original();
	private static JFPoints points=new JFPoints();
	static{
		points.setOriginal(original);
		baoWen.setPoints(points);
	}
	private IntegralService(){
		
	}
	/**
	 * 构建通用请求消息头并返回
	 * @return
	 */
	private static ReqHead makeReqHead(){
		ReqHead head=new ReqHead();
		head.setBusinessId(getDateTime()+getRandomNumber(4));
		head.setReqDate(getDate());
		head.setOrgId(ConfigUtil.get("orgID"));
		return head;
	}
	/**
	 * 向积分系统发起请求
	 * @param body 	报文体
	 * @param url   请求链接
	 * @return      积分系统返回的报文
	 * @throws Exception 
	 */
	private static String postReq(Body body,String url) throws Exception{
		ReqHead head=makeReqHead();
		original.setHead(head);
		original.setBody(body);
		Document doc=baoWen.toDocument();
		doc.setXMLEncoding("GBK");
		return HttpsClient.sendData(doc.asXML(), url, "GBK", 10000);
	}
	/**
	 * 构建通用响应信息对象并返回
	 * @param t			body的子类对象
	 * @param xmlStr 	积分平台的响应xml字符串
	 * @return			返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 */
	private static <T extends Body> ReturnMessage makeMessage(T t,String xmlStr){
		try{
		Document result=DocumentHelper.parseText(xmlStr);
		ReturnMessage message=new ReturnMessage();
		message.setRetCode(result.selectSingleNode("//retCode").getText());
		message.setRetMsg(result.selectSingleNode("//retMsg").getText());
		if("0000".equals(message.getRetCode()))
			message.setObj(t.fromElement((Element)result.selectSingleNode("//body")));
		return message;
		}catch (Exception e) {
			throw new AppException("构建返回对象异常",e);
		}
	}
	/**
	 * 积分查询
	 * @param account 积分帐号
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 */
	public static ReturnMessage queryIntegral(String account){
		try{
			QueryReqBody body=new QueryReqBody();
			body.setIdNo(account);
			body.setIdType(QueryReqBody.CUST_ID_TYPE_ACCOUNT);
			String res=postReq(body, ConfigUtil.get("queryUrl"));
			return makeMessage(new QueryResBody(),res);
		}catch (Exception e) {
			throw new AppException("查询积分异常",e);
		}
	}
	/**
	 * 积分历史(明细)查询
	 * @param account 积分帐号
	 * @param startDate 查询起始日期,不得早于6个月
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage historyIntegral(String account,String startDate) throws Exception{
		startDate=nullAsSpace(startDate);
		HistoryReqBody body=new HistoryReqBody();
		body.setIdNo(account);
		body.setReqDate(startDate);
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		String res=postReq(body, ConfigUtil.get("historyUrl"));
		return makeMessage(new HistoryResBody(), res);
	}
	/**
	 * 积分扣除
	 * @param account 积分帐号
	 * @param reducePoints 扣除积分值
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage reduceIntegral(String account,String reducePoints) throws Exception{
		ReduceReqBody body=new ReduceReqBody();
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setIdNo(account);
		body.setReducePoints(reducePoints);
		String res=postReq(body, ConfigUtil.get("reduceUrl"));
		return makeMessage(new ReduceResBody(), res);
	}
	/**
	 * 积分赠送
	 * @param account 赠送方积分帐号
	 * @param receiveAccount 接收方积分帐号
	 * @param transferPoints 赠送积分值
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage transferIntegral(String account,String receiveAccount,String transferPoints) throws Exception{
		TransferReqBody body=new TransferReqBody();
		body.setIdNo(account);
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setToIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setToIdNo(receiveAccount);
		body.setTransferPoints(transferPoints);
		String res=postReq(body, ConfigUtil.get("transferUrl"));
		return makeMessage(new HistoryResBody(), res);
	}
	/**
	 * 积分冲正
	 * @param serialNo 流水号
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage reversalIntegral(String serialNo) throws Exception{
		ReversalReqBody body=new ReversalReqBody();
		body.setSerialNo(serialNo);
		String res=postReq(body, ConfigUtil.get("reversalUrl"));
		return makeMessage(new HistoryResBody(), res);
	}
	/**
	 * 积分增加
	 * @param account 积分帐号
	 * @param addPoint 增加的积分值
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage addIntegral(String account,String addPoint) throws Exception{
		AddReqBody body=new AddReqBody();
		body.setIdNo(account);
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setAddPoints(addPoint);
		String res=postReq(body, ConfigUtil.get("addUrl"));
		return makeMessage(new AddResBody(), res);
	}
	/**
	 * 绑定手机号修改
	 * @param account 积分帐号
	 * @param newPhone 新手机号
	 * @return 		  返回信息{retCode,retMessage,obj(仅当retCode为0000时存在)}
	 * @throws Exception 
	 */
	public static ReturnMessage modifyPhone(String account,String newPhone) throws Exception{
		ModifyPhoneReqBody body=new ModifyPhoneReqBody();
		body.setIdNo(account);
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setPhoneNo(newPhone);
		String res=postReq(body, ConfigUtil.get("modifyPhoneUrl"));
		return makeMessage(new ModifyPhoneResBody(), res);
	}
}
