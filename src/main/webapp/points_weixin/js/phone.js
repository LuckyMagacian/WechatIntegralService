$(function() {
	getPhone();
});

function editPhone() {
	var newPhone = $("#newPhone").val(),
		validateCode = $('#phone-code').val();
	if(!valiPhone(newPhone)) {
		showInfo('手机号码格式有误');
		warnInfo('newPhone');
	} else if(!valiMsgNum(validateCode)) {
		showInfo('验证码必须为6位数字');
		warnInfo('phone-code');
	} else {
		showDialog('editPhone');
	}
}

function getPhone() {
	var uri = '../phone.do';
	ajaxPost(uri, {}, function(jsonStr) {
		var phone = jsonStr.phone;
		$("#phoneNumber").text(phone);
	});
}

/* 获取验证码 */
function sendPhoneMsg() {
	var token = getCookie('token'),
		phone = $("#newPhone").val();
	$('#dialogPhone').text(phone);
	if(valiPhone(phone)) {
		var $json = {
				'phone': phone
			},
			uri = "../updatePhone.do";
		ajaxPost(uri, $json, function(jsonStr) {
			showSuccess('短信发送成功!');
			$("#newPhone").attr("disabled", "disabled");
			$("#msgWord").attr('href', 'javascript:void(0)');
			$("#msgWord").addClass('rice-disabled');
			countDown(msgResetTime, 'msgWord', function() {
				$("#msgWord").attr('href', 'javascript:sendPhoneMsg();');
				$("#msgWord").removeClass('rice-disabled');
				$("#msgWord").text('获取验证码');
			});
		});
	} else {
		showInfo('手机号码格式有误');
		warnInfo('newPhone');
	}
}

function sendEditPhone() {
	var newPhone = $("#newPhone").val(),
		validateCode = $('#phone-code').val();
	var $json = {
			'phone': newPhone,
			'validateCode': validateCode
		},
		uri = "../updatePhoneOper.do";
	ajaxPost(uri, $json, function(jsonStr) {
		showSuccess('手机号修改成功!');
		setTimeout(function() {
			location.href = 'userInfo.html';
		}, 1800);
	});
}