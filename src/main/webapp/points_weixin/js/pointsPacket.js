(function() {
	getJsSign();
})();

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