(function() {
	getJsSign();
})();

/* 获取js签名 */
function getJsSign() {
	var urlLink = location.href.split('#')[0];
	ajaxPost('../getJsSign.do', {
		'url': urlLink
	}, function(jsonStr) {
		wxApi(jsonStr.obj);
	});
}

function wxApi(jsonStr) {
	var wxStr = {
		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: 'wxf235257ae41bb440', // 必填，公众号的唯一标识
		timestamp: jsonStr.timeStamp, // 必填，生成签名的时间戳
		nonceStr: jsonStr.nonce, // 必填，生成签名的随机串
		signature: jsonStr.sign, // 必填，签名，见附录1
		jsApiList: [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'onMenuShareQZone'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	};
	wx.config(wxStr);
	wx.ready(function() {
		console.log('config信息验证通过');
	});
	wx.error(function(res) {
		console.log(res);
	});

}

/* 发红包 */
function sendPacket() {
	var redPacketCount = $("#redPacketCount").val(),
		redPacketIntegral = $("#redPacketIntegral").val(),
		redPacketName = $("#redPacketName").val();
	if(!vailPositiveIntegral(redPacketCount)) {
		showInfo('红包个数只能为正整数');
		warnInfo('redPacketCount');
	} else if(!vailPositiveIntegral(redPacketIntegral)) {
		showInfo('积分值只能为正整数');
		warnInfo('redPacketIntegral');
	} else {
		if(redPacketName == '') {
			redPacketName = '积分红包,速来领取';
		}
		var $json = {
				'redPacketCount': redPacketCount,
				'redPacketIntegral': redPacketIntegral,
				'redPacketName': redPacketName
			},
			uri = '../grantRedPacket.do';
		ajaxPost(uri, $json, function(jsonStr) {
			var obj = jsonStr.obj,
				redPacketUrl = obj.redPacketUrl,
				redPacketName = obj.redPacketName, //红包备注(标题)
				nickName = (obj.nickName == undefined) ? '您的好友' : obj.nickName,
				img = projectStr + 'img/icon-redPacket.png',
				shareStr = {
					title: redPacketName, // 分享标题
					desc: nickName + '发来的积分红包', // 分享描述
					link: redPacketUrl, // 分享链接
					imgUrl: img, // 分享图标
					success: function() {
						showSuccess('积分红包分享成功!');
					},
					cancel: function() {
						showInfo('未分享的红包将在24小时后退回您的账户');
					}
				};
			wx.onMenuShareTimeline(shareStr); //分享到朋友圈
			wx.onMenuShareAppMessage(shareStr); //分享给朋友
			wx.onMenuShareQQ(shareStr);
			wx.onMenuShareWeibo(shareStr);
			wx.onMenuShareQZone(shareStr);
			showDialog('packet');
		});
	}
}