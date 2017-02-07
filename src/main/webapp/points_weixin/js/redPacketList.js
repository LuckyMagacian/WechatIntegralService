$(function() {
	paramsArr = getParam();
	//getRedPacketInfo();
	getJsSign(getRedPacketInfo);
});

/* 获取抢红包列表信息 */
function getPacketDetail() {
	var uri = '../redPacketDetail.do',
		redPacketId = paramsArr.redPacketId;
	ajaxPost(uri, {
		'redPacketId': redPacketId
	}, function(jsonStr) {
		var rows = jsonStr.obj,
			today = parseInt(new Date().getDate());
		$("#getPoints").text(jsonStr.retMsg);
		$("#redPacketDetailList").html('');
		$.each(rows, function(i) {
			var row = rows[i];
			integral = row.integral,
				nickName = row.nickName,
				img = row.beiy,
				receiveTime = row.receiveTime;
			var thisDay = parseInt(receiveTime.substr(6, 2));
			if(thisDay != today) {
				receiveTime = datetimeSkyle(receiveTime);
			} else {
				receiveTime = receiveTime.substr(8, 2) + ':' + receiveTime.substr(10, 2) + ':' + receiveTime.substr(12, 2);
			}
			nickName = (nickName == undefined) ? '&nbsp;' : nickName;
			var temp = '<div class="weui-cell"><div class="weui-cell__hd"><img src="' + img + '"/></div><div class="weui-cell__bd"><p>' +
				nickName + '</p><p>' + receiveTime + '</p></div><div class="weui-cell__ft">' + integral + '积分</div></div>';
			$("#redPacketDetailList").append(temp);
		});

		ajaxPost('../generatorRedPacketUrl.do', {
			'redPacketId': redPacketId
		}, function(jsonStr) {
			var shareStr = {
				title: '积分红包', // 分享标题
				desc: nickName + '发来的积分红包', // 分享描述
				link: jsonStr.obj, // 分享链接
				imgUrl: img, // 分享图标
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
		});
	});
}

/* 获取红包信息 */
function getRedPacketInfo() {
	if(paramsArr.redPacketId == '' || paramsArr.redPacketId == undefined) {
		falseAlert('红包不存在', '无效的红包,请关闭或返回上一页');
	} else {
		var uri = '../redPacketInfo.do',
			redPacketId = paramsArr.redPacketId;
		ajaxPost(uri, {
			'redPacketId': redPacketId
		}, function(jsonStr) {
			var $obj = jsonStr.obj,
				nickName = $obj.nickName,
				redPacketName = $obj.redPacketName,
				img = $obj.beiy,
				redPacketCount = $obj.redPacketCount,
				redPacketLessCount = $obj.redPacketLessCount;
			nickName = (nickName == undefined || nickName == '') ? '您好友' : nickName;
			$("#packetHeadPic").css('background-image', 'url(' + img + ')');
			$("#packetUser").text(nickName);
			$("#packeInfo").text(redPacketName);
			$("#packetNum").text(redPacketCount);
			$("#packetHave").text(redPacketLessCount);
			getPacketDetail();
		});
	}
}