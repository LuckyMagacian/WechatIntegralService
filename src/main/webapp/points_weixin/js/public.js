/* 配置文件 */
msgResetTime = 10; //秒

$(function() {
	mui.init();
});

/* 跳转 */
function hrefTo(linkStr) {
	location.href = linkStr + '.html';
}
/* 显示弹窗 */
function showDialog(str) {
	$('#dialog-' + str).removeClass('hidden');
}
/* 关闭弹窗 */
function closeDialog(str) {
	$('#dialog-' + str).addClass('hidden');
}

/* 发送短信 */
function sendMsg() {

}

function getParam() {
	var params = location.search.substr(1); //  获取参数 并且去掉？
	var ArrParam = params.split('&');
	var paramsArr = new Object();
	/*if(ArrParam.length == 1) {
		//只有一个参数的情况
		return params.split('=')[1];
	} else {
		//多个参数参数的情况
		for(var i = 0; i < ArrParam.length; i++) {
			return ArrParam[i].split('=')[1];
		}
	}*/
	if(ArrParam == '') {
		return '';
	} else {
		$.each(ArrParam, function(i) {
			var param = this.split('=');
			paramsArr[param[0]] = param[1];
		});
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
	dataJson.token = getCookie('token');
	$.ajax({
		type: "post",
		url: url,
		data: dataJson,
		dataType: "json",
		beforeSend: function() {
			showLoading();
		},
		success: function(jsonStr) {
			if(jsonStr.retCode == '0000') {
				if(jsonStr.token != undefined && jsonStr.token != '' && jsonStr.token != null) {
					setCookie('token', jsonStr.token);
				}
				if(typeof successFunc == 'function') {
					successFunc(jsonStr);
				}
			} else {
				switch(jsonStr.retCode) {
					case '9797':
						falseAlert('登录超时', '未登录或登录超时,将跳转至首页')
						setTimeout(function() {
							location.href = '/index.html';
						}, 1000);
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
			console.log(e);
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
	$('body').append('<div id="loadingToast">' +
		'<div class="weui-mask_transparent"></div>' +
		'<div class="weui-toast">' +
		'<i class="weui-loading weui-icon_toast"></i>' +
		'<p class="weui-toast__content">数据加载中</p></div></div>');
}

/* 显示info界面 */
function showInfo(msg) {
	$('body').append('<div id="infoToast">' +
		'<div class="weui-mask_transparent"></div>' +
		'<div class="weui-toast">' +
		'<i class="weui-icon-info-circle weui-icon_toast"></i>' +
		'<p class="weui-toast__content">' + msg + '</p></div></div>');
	setTimeout(function() {
		removeMsg('infoToast');
	}, 1800);
}

/* 跳转失败页面 */
function toFalsePage() {

}

/* 失败提示 */
function falseAlert(msgTitle, msg) {
	$('body').append('<div id="falseAlert"><div class="weui-mask"></div>' +
		'<div class="weui-dialog"><div class="weui-dialog__hd"><strong class="weui-dialog__title">' + msgTitle +
		'</strong></div><div class="weui-dialog__bd">' + msg +
		'</div><div class="weui-dialog__ft"><a href="javascript:removeMsg(\'falseAlert\');" class="weui-dialog__btn weui-dialog__btn_primary">确认</a></div></div></div>');
}

/* 去除弹出信息 */
function removeMsg(id) {
	$("#" + id).fadeOut('1000', function() {
		$("#" + id).remove();
	});

}

/** 正整数验证 */
function vailPositiveIntegral(val) {
	var patten = /^[1-9]\d*[Xx]*$/;
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