$(function() {

});

/* 发送验证码 */
function sendGiveMsg() {
	var token = getCookie('token'),
		points = $("#points").val(),
		idcard = $("#idcard").val();
	if(!vailPositiveIntegral(points)) {
		showInfo('积分格式有误');
		warnInfo('points');
	} else if(!valiIdcard(idcard)) {
		showInfo('身份证号码有误');
		warnInfo('idcard');
	} else {
		var $json = {
				'token': token,
				'integral': points,
				'receiverIdCard': idcard
			},
			uri = '../deliveryIntegral.do';
		ajaxPost(uri, $json, function(jsonStr) {
			showSuccess('短信发送成功!');
			$("#msgWord").attr('href', 'javascript:void(0)');
			$("#msgWord").addClass('rice-disabled');
			countDown(msgResetTime, 'msgWord', function() {
				$("#msgWord").attr('href', 'javascript:sendGiveMsg();');
				$("#msgWord").removeClass('rice-disabled');
				$("#msgWord").text('获取验证码');
			});
		});
	}
}

/* 确认转赠 */
function sureGive() {
	var token = getCookie('token'),
		points = $("#points").val(),
		idcard = $("#idcard").val(),
		code = $("#code").val(),
		checkedFlag = $("#giveMsgCheck")[0].checked;
	givephone = $("#givePhone").val();
	console.log(checkedFlag);
	if(!vailPositiveIntegral(points)) {
		showInfo('积分格式有误');
		warnInfo('points');
	} else if(!valiIdcard(idcard)) {
		showInfo('身份证号码有误');
		warnInfo('idcard');
	} else if(!valiMsgNum(code)) {
		showInfo('验证码为6位数字');
		warnInfo('code');
	} else {
		if((checkedFlag && valiPhone(givephone)) || !checkedFlag) {
			var $json = {
					'token': token,
					'integral': points,
					'receiverIdCard': idcard,
					'validateCode': code
				},
				uri = '../deliveryIntegralOper.do';
			ajaxPost(uri, $json, function(jsonStr) {
				ajaxPost('../sendMessage.do',{'token':getCookie('token'),'phone':givephone},function(jsonStr){
					showSuccess('积分转赠成功!');
				});
			});
		} else {
			showInfo('请输入受赠人手机号');
			warnInfo('givePhone');
		}
	}
}

function toggleGivePhone(value) {
	if(value) { //选中
		$("#givePhoneCells").removeClass('hidden');
	} else {
		$("#givePhoneCells").addClass('hidden');
	}
}