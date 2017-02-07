(function() {
	paramsArr = getParam();
	getJsSign(getRedPacketInfo);
})();

/* 获取红包信息 */
function getRedPacketInfo() {
	if(paramsArr.redPacketId == '' || paramsArr.redPacketId == undefined) {
		falseAlert('红包不存在', '无效的红包,请关闭或返回上一页');
	} else {
		var uri = '../redPacketInfo.do',
			redPacketId = paramsArr.redPacketId;
		ajaxPost(uri, {
			'redPacketId': redPacketId,
			'token': paramsArr.token
		}, function(jsonStr) {
			var $obj = jsonStr.obj,
				nickName = $obj.nickName,
				redPacketName = $obj.redPacketName,
				img = $obj.beiy,
				redPacketLessCount = $obj.redPacketLessCount,
				redPacketCount = $obj.redPacketCount,
				totalIntegral = $obj.totalIntegral;
			nickName = (nickName == undefined || nickName == '') ? '您好友' : nickName;
			$("#packetHeadPic").css('background-image', 'url(' + img + ')');
			$("#packetUser").text(nickName);
			$("#packetinfo").text(redPacketName);
			$("#redPacketNum").text(redPacketCount);
			$("#redPacketTotal").text(totalIntegral);
			if(redPacketLessCount == 0) {
				infoAlert('领光啦!', '红包已经全部被领走了,下次早点哦');
			}
			var shareStr = {
				title: redPacketName, // 分享标题
				desc: nickName + '发来的积分红包', // 分享描述
				link: getOpenLink(redPacketId), // 分享链接
				imgUrl: projectStr + 'img/icon-redPacket.png', // 分享图标
				success: function() {
					showSuccess('积分红包分享成功!');
				},
				cancel: function() {
					showInfo('积分红包未分享!');
				}
			};
			wx.onMenuShareTimeline(shareStr); //分享到朋友圈
			wx.onMenuShareAppMessage(shareStr); //分享给朋友
			wx.onMenuShareQQ(shareStr);
			wx.onMenuShareWeibo(shareStr);
			wx.onMenuShareQZone(shareStr);
			alert(JSON.stringify(shareStr));
		});
	}
}

/* 拆红包 */
function unpackRedPacket() {
	if(paramsArr.redPacketId == '' || paramsArr.redPacketId == undefined) {
		falseAlert('红包不存在', '无效的红包,请关闭或返回上一页');
	} else {
		var uri = '../unpackRedPacket.do',
			redPacketId = paramsArr.redPacketId;
		ajaxPost(uri, {
			'redPacketId': redPacketId,
			'token': paramsArr.token
		}, function(jsonStr) {
			location.href = 'redPacketList.html?redPacketId=' + paramsArr.redPacketId;
		});
	}
}