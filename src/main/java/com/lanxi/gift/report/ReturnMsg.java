package com.lanxi.gift.report;
/**
 * 返回信息bean
 * @author 1
 *
 */
public class ReturnMsg {
	private String ResCode;		/**返回码 */
	private String ResMsg;		/**返回消息 */
	private Object object;		/**携带信息*/
	public String getResCode() {
		return ResCode;
	}
	public void setResCode(String resCode) {
		ResCode = resCode;
	}
	public String getResMsg() {
		return ResMsg;
	}
	public void setResMsg(String resMsg) {
		ResMsg = resMsg;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	@Override
	public String toString() {
		return "ReturnMsg [ResCode=" + ResCode + ", ResMsg=" + ResMsg + "]";
	}
	
}
