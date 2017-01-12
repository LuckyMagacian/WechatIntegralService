$(function() {
	paramsArr = getParam(); //获取url参数数组
	console.log(paramsArr);
});

/* 确认绑定 */
function binding() {

}

/* 获取验证码 */
function sendBindMsg() {
	var token = paramsArr.token,
		name = $("#binding-name").val(),
		idcard = $("#binding-idcard").val(),
		phone = $("#binding-phone").val();
	if(valiPhone(phone)) {
		var $json = {
				'token': token,
				'name': name,
				'idcard': idcard,
				'phone': phone
			},
			uri = '../bindingsJf.do';
		console.log('[ajax]' + uri + ',params:' + $json);
		ajaxPost(uri, $json, function(jsonStr) {
			console.log(jsonStr);

		});
	} else {
		showInfo('手机号码格式有误');
		warnInfo('binding-phone');
		$("#msgWord").attr('href', 'javascript:void(0)');
		$("#msgWord").addClass('rice-disabled');
		countDown(msgResetTime, 'msgWord', function() {
			$("#msgWord").attr('href', 'javascript:sendBindMsg();');
			$("#msgWord").removeClass('rice-disabled');
			$("#msgWord").text('获取验证码');
		});
	}
}