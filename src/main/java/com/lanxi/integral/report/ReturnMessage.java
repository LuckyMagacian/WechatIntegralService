package com.lanxi.integral.report;
/**
 * 响应信息类
 * @author 1
 *
 */
public class ReturnMessage {
	/**错误代码*/
	private String retCode;
	/**错误信息*/
	private String retMsg;
	/**附带信息*/
	private Object obj;
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
