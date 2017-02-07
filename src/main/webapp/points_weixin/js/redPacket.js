(function() {
	paramsArr = getParam();
	getJsSign();
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

/* 获取js签名 */
function getJsSign(func) {
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
		getRedPacketInfo();
	});
	wx.error(function(res) {
		console.log(res);
	});

}