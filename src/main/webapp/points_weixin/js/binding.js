$(function() {
	paramsArr = getParam(); //获取url参数数组
	console.log(paramsArr);
});

/* 确认绑定 */
function binding() {
	var token = paramsArr.token,
		name = $("#binding-name").val(),
		idcard = $("#binding-idcard").val(),
		phone = $("#binding-phone").val(),
		validateCode = $("#binding-code").val();
	if(!valiName(name)) {
		showInfo('姓名必须为汉字');
		warnInfo('binding-name');
	} else if(!valiIdcard(idcard)) {
		showInfo('身份证号码有误');
		warnInfo('binding-idcard');
	} else if(!valiPhone(phone)) { //手机号有误
		showInfo('手机号码格式有误');
		warnInfo('binding-phone');
	} else if(!valiMsgNum(validateCode)) {
		showInfo('验证码必须为6位数字');
		warnInfo('binding-code');
	} else {
		var $json = {
				'token': token,
				'phone': phone,
				'name': name,
				'idcard': idcard,
				'validateCode': validateCode
			},
			uri = "../bindingsJfOper.do";
		ajaxPost(uri, $json, function(jsonStr) {
			showSuccess('短信发送成功!');
			setTimeout(function() {
				location.href = 'index.html';
			}, 1800);
		});
	}
}

/* 获取验证码 */
function sendBindMsg() {
	var token = paramsArr.token,
		phone = $("#binding-phone").val();
	if(valiPhone(phone)) {
		var $json = {
				'token': token,
				'phone': phone
			},
			uri = '../bindingsJf.do';
		ajaxPost(uri, $json, function(jsonStr) {
			showSuccess('短信发送成功!');
			$("#binding-phone").attr("disabled", "disabled");
			$("#msgWord").attr('href', 'javascript:void(0)');
			$("#msgWord").addClass('rice-disabled');
			countDown(msgResetTime, 'msgWord', function() {
				$("#msgWord").attr('href', 'javascript:sendBindMsg();');
				$("#msgWord").removeClass('rice-disabled');
				$("#msgWord").text('获取验证码');
			});
		});
	} else {
		showInfo('手机号码格式有误');
		warnInfo('binding-phone');
	}
}