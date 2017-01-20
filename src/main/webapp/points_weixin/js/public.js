/* 配置文件 */
msgResetTime = 10; //秒

$(function() {
	mui.init();
});

/* 跳转 */
function hrefTo(linkStr) {
	if(linkStr==''){
		showInfo('功能开发中');
	}else{
		location.href = linkStr + '.html';
	}
	
}
/* 显示弹窗 */
function showDialog(str) {
	$('#dialog-' + str).removeClass('hidden');
}
/* 关闭弹窗 */
function closeDialog(str) {
	$('#dialog-' + str).addClass('hidden');
}

function getParam() {
	var params = location.search.substr(1); //  获取参数 并且去掉？
	var ArrParam = params.split('&');
	var paramsArr = new Object();
	if(ArrParam == '') {
		return '';
	} else {
		$.each(ArrParam, function(i) {
			var param = this.split('=');
			paramsArr[param[0]] = param[1];
		});
		console.log('[PARAMS]:'+JSON.stringify(paramsArr));
		return paramsArr;
	}
}

/**
 *  ajax请求封装
 * url:请求后台地址,dataJson:post请求数据
 * beforeFunc:请求前触发函数,不带参数
 * successFunc:成功时触发函数,带一个参数jsonStr
 * */
function ajaxPost(url, dataJson, successFunc) {
	if(dataJson.token=='' || dataJson.token==undefined){
		dataJson.token = getCookie('token');
	}
	console.log('[ajax]' + url + ',params:' + JSON.stringify(dataJson));
	$.ajax({
		type: "post",
		url: url,
		data: dataJson,
		dataType: "json",
		beforeSend: function() {
			showLoading();
		},
		success: function(jsonStr) {
			removeMsg('loadingToast');
			if(jsonStr.retCode == '0000') {
				if(jsonStr.token != undefined && jsonStr.token != '' && jsonStr.token != null) {
					setCookie('token', jsonStr.token);
				}
				if(typeof successFunc == 'function') {
					console.log('[AJAX res]:'+JSON.stringify(jsonStr));
					successFunc(jsonStr);
				}
			} else {
				switch(jsonStr.retCode) {
					case '0001':
						falseAlert('登录超时', '未登录或登录超时,请从公众号菜单栏重新进入')
						break;
					case '9999':
						falseAlert("系统错误", jsonStr.retMsg);
						break;
					default:
						falseAlert("错误:" + jsonStr.retCode, jsonStr.retMsg);
						break;
				}
			}
		},
		error: function(e) {
			removeMsg('loadingToast');
			falseAlert('请求错误', '发送请求失败');
			console.log('[ERROR]:'+JSON.stringify(e));
		}
	});
}
/* cookie设置 */
function getCookie(cookieName) {
	var cookieRe = $.cookie(cookieName);
	return(cookieRe == undefined || cookieRe == null) ? '' : cookieRe;
}

function setCookie(cookieName, val) {
	$.cookie(cookieName, val);
	return cookieName;
}

/* 显示loading界面 */
function showLoading() {
	$('body').append('<div class="loadingToast">' +
		'<div class="weui-mask_transparent"></div>' +
		'<div class="weui-toast">' +
		'<i class="weui-loading weui-icon_toast"></i>' +
		'<p class="weui-toast__content">数据加载中</p></div></div>');
}

/* 显示info界面 */
function showInfo(msg) {
	$('body').append('<div class="infoToast">' +
		'<div class="weui-mask_transparent"></div>' +
		'<div class="weui-toast">' +
		'<i class="weui-icon-info-circle weui-icon_toast"></i>' +
		'<p class="weui-toast__content">' + msg + '</p></div></div>');
	setTimeout(function() {
		removeMsg('infoToast');
	}, 1800);
}

/* 显示成功界面 */
function showSuccess(msg) {
	$('body').append('<div class="successToast">' +
		'<div class="weui-mask_transparent"></div>' +
		'<div class="weui-toast">' +
		'<i class="weui-icon-success-no-circle weui-icon_toast"></i>' +
		'<p class="weui-toast__content">' + msg + '</p></div></div>');
	setTimeout(function() {
		removeMsg('successToast');
	}, 1800);
}

/* 跳转失败页面 */
function toFalsePage() {

}

/* 失败提示 */
function falseAlert(msgTitle, msg) {
	$('body').append('<div class="falseAlert"><div class="weui-mask"></div>' +
		'<div class="weui-dialog"><div class="weui-dialog__hd"><strong class="weui-dialog__title">' + msgTitle +
		'</strong></div><div class="weui-dialog__bd">' + msg +
		'</div><div class="weui-dialog__ft"><a href="javascript:removeMsg(\'falseAlert\');" class="weui-dialog__btn weui-dialog__btn_primary">确认</a></div></div></div>');
}
/* 信息提示 */
function infoAlert(msgTitle, msg) {
	$('body').append('<div class="infoAlert"><div class="weui-mask"></div>' +
		'<div class="weui-dialog"><div class="weui-dialog__hd"><strong class="weui-dialog__title">' + msgTitle +
		'</strong></div><div class="weui-dialog__bd">' + msg +
		'</div><div class="weui-dialog__ft"><a href="javascript:removeMsg(\'infoAlert\');" class="weui-dialog__btn weui-dialog__btn_primary">确认</a></div></div></div>');
}
/* 去除弹出信息 */
function removeMsg(id) {
	$("." + id).fadeOut('1000', function() {
		$("." + id).remove();
	});

}

/** 正整数验证 */
function vailPositiveIntegral(val) {
	var patten = /^[1-9]\d*$/;
	return patten.test(val);
}
/** 正实数验证*/
function valiRealNum(val) {
	var patten = /^([1-9]\d*|0)(\.\d+)?$/;
	return patten.test(val);
}
/** 姓名验证*/
function valiName(val) {
	var patten = /^[\u4E00-\u9FFF]{1,6}$/; //正则，匹配汉字
	return patten.test(val);
}
/** 手机号码验证 */
function valiPhone(val) {
	var patten = /^(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$/; //匹配手机号
	return patten.test(val);
}
/** 6位短信验证码验证 */
function valiMsgNum(val) {
	var patten = /^\d{6}$/;
	return patten.test(val);
}

/* 错误输入框样式 */
function warnInfo(id) {
	var p = $("#" + id).parent();
	p.next().children('i').removeClass('hidden');
	p.parent().addClass('weui-cell_warn');
	$("#" + id).focus(function() {
		p.next().children('i').addClass('hidden');
		p.parent().removeClass('weui-cell_warn');
		$("#" + id).select();
	});
}
/** 倒计时 */
function countDown(time, id, func) {
	idStr = "#" + id;
	if(time > 0) {
		$(idStr).text("重新获取(" + time + "s)");
		setTimeout(function() {
			countDown(time - 1, id,func);
		}, 1000);
	} else {
		if(typeof(func) == 'function')
			func();
		else if(typeof(func) == 'string')
			$(idStr).text(func);
		else
			$(idStr).text('获取验证码');
	}
}

/** 15位或18位身份证验证*/
function valiIdcard(value) {
	/*
	 * 身份证15位编码规则：dddddd yymmdd xx p
	 * dddddd：6位地区编码
	 * yymmdd: 出生年(两位年)月日，如：910215
	 * xx: 顺序编码，系统产生，无法确定
	 * p: 性别，奇数为男，偶数为女
	 * 
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y
	 * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
	 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
	 * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
	 * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
	 * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
	 */
	//15位或者18位身份证正则式
	var regStr = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-2]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	var valiStr = value;
	var flag = 0;
	if(!regStr.test(valiStr)) { //test()方法搜索字符串指定的值，根据结果并返回真或假。
		flag = 1; //匹配不正确
	} else {
		if(valiStr.length == 18) {
			var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2),
				idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2),
				idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
			for(var i = 0; i < 17; i++) {
				idCardWiSum += valiStr.substring(i, i + 1) * idCardWi[i];
			}

			var idCardMod = idCardWiSum % 11; //计算出校验码所在数组的位置
			var idCardLast = valiStr.substring(17); //得到最后一位身份证号码
			if(idCardMod == 2) {
				if(idCardLast == "X" || idCardLast == "x") {
					flag = 0;
				} else {
					flag = 1;
				}
			} else {
				//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
				if(idCardLast == idCardY[idCardMod]) {
					flag = 0;
				} else {
					flag = 1;
				}
			}
		} else {
			flag = 0;
		}
	}
	if(flag) { //匹配不正确
		return false;
	} else { //匹配正确
		return true;
	}
}

/** 
 * 时间字符串格式化
 * 20160505->2016-05-05
 */
function datetimeSkyle(str){
	var length=str.length,
		newStr=null;
	if(length==8){//yyyymmdd
		newStr=str.substr(0,4)+'-'+str.substr(4,2)+'-'+str.substr(6,2);
	}else if(length==14){//yyyymmddhhmmss
		newStr=str.substr(0,4)+'-'+str.substr(4,2)+'-'+str.substr(6,2);
		newStr+=' '+str.substr(8,2)+':'+str.substr(10,2)+':'+str.substr(12,2);
	}else{
		return str;
	}
	return newStr;
}