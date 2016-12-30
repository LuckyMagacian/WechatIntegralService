package com.lanxi.integral.report;
/**
 * 积分系统异常表
 * @author 1
 *
 */
public abstract class ErrorMap {
	public static class ReportError{
		private String errorCode;
		private String errorMsg;
		private ReportError(String errorCode,String errorMsg){
			this.errorCode=errorCode;
			this.errorMsg=errorMsg;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}
	/**积分系统异常-操作成功*/
	public static final ReportError ERROR_SUCCESS;
	/**积分系统异常-流水重复*/
	public static final ReportError ERROR_SERIAL_REPEAT;
	/**积分系统异常-客户号不存在*/
	public static final ReportError ERROR_NO_ACCOUNT;
	/**积分系统异常-待扣积分客户积分不足*/
	public static final ReportError ERROR_INTEGRAL_INSUFFICIENT;
	/**积分系统异常-综合积分扣除失败*/
	public static final ReportError ERROR_REDUCE_FAIL;
	/**积分系统异常-没有该编号类型*/
	public static final ReportError ERROR_NOUMBER_TYPE_ERROR;
	/**积分系统异常-被转赠积分客户不存在*/
	public static final ReportError ERROR_RECEIVER_NO_ACCOUNT;
	/**积分系统异常-验签失败*/
	public static final ReportError ERROR_SIGN_FAIL;
	/**积分系统异常-查询流水起始时间大于6个月*/
	public static final ReportError ERROR_SERIAL_TIME_OVER_SIX_MONTH;
	/**积分系统异常-卡号不存在*/
	public static final ReportError ERROR_NO_CARD;
	/**积分系统异常-参数错误*/
	public static final ReportError ERROR_PARAM_ERROR;
	/**积分系统异常-冲正与扣减发起机构不一致*/
	public static final ReportError ERROR_REVERSAL_ORG_STARTER_DIFF;
	/**积分系统异常-操作积分值必须为正整数*/
	public static final ReportError ERROR_PARAM_INTEGRAL_NOT_POSITIVE_INTEGER;
	/**积分系统异常-请求报文错误*/
	public static final ReportError ERROR_PARAM_REPORT_ERROR;
	/**积分系统异常-综合积分增加失败*/
	public static final ReportError ERROR_PARAM_INTEGRAL_ADD_FAIL;
	/**积分系统异常-查询无此冲正交易*/
	public static final ReportError ERROR_PARAM_NO_SERIAL;
	/**积分系统异常-系统报错*/
	public static final ReportError ERROR_PARAM_SYSTEM_ERROR;
	
	static{
		ERROR_SUCCESS=new ReportError("0000","操作成功");
		ERROR_SERIAL_REPEAT=new ReportError("9001","流水重复");
		ERROR_NO_ACCOUNT=new ReportError("9002","客户号不存在");
		ERROR_INTEGRAL_INSUFFICIENT=new ReportError("9003","待扣积分客户积分不足");
		ERROR_REDUCE_FAIL=new ReportError("9004","综合积分扣除失败");
		ERROR_NOUMBER_TYPE_ERROR=new ReportError("9005","没有该编号类型");
		ERROR_RECEIVER_NO_ACCOUNT=new ReportError("9006","被转赠积分客户不存在");
		ERROR_SIGN_FAIL=new ReportError("9007","验签失败");
		ERROR_SERIAL_TIME_OVER_SIX_MONTH=new ReportError("9008","查询流水起始时间大于6个月");
		ERROR_NO_CARD=new ReportError("9009","卡号不存在");
		ERROR_PARAM_ERROR=new ReportError("9010","参数错误");
		ERROR_REVERSAL_ORG_STARTER_DIFF=new ReportError("9011","冲正与扣减发起机构不一致");
		ERROR_PARAM_INTEGRAL_NOT_POSITIVE_INTEGER=new ReportError("9012","操作积分值必须为正整数");
		ERROR_PARAM_REPORT_ERROR=new ReportError("9013","请求报文错误");
		ERROR_PARAM_INTEGRAL_ADD_FAIL=new ReportError("9014","综合积分增加失败");
		ERROR_PARAM_NO_SERIAL=new ReportError("9015","查询无此冲正交易");
		ERROR_PARAM_SYSTEM_ERROR=new ReportError("9999","系统报错");
	}
}
