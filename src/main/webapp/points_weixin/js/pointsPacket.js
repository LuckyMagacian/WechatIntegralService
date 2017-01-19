(function() {
/*	//getJsSign();
	$("#copyLink").attr('data-clipboard-text','2312312312312');
	var client = new ZeroClipboard(document.getElementById("copyLink"));
	client.on("ready", function(readyEvent) {
		alert(1);
		client.on("aftercopy", function(event) {
			event.target.style.display = "none";
			alert("Copied text to clipboard: " + event.data["text/plain"]);
		});
	});*/
})();

/* 获取js签名 */
function getJsSign() {
	ajaxPost('../getJsSign.do', {}, function(jsonStr) {
		wxApi(jsonStr);
	});
}

function wxApi(jsonStr) {
	wx.config({
		debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: 'wxf235257ae41bb440', // 必填，公众号的唯一标识
		timestamp: jsonStr.timeStamp, // 必填，生成签名的时间戳
		nonceStr: jsonStr.nonce, // 必填，生成签名的随机串
		signature: jsonStr.sign, // 必填，签名，见附录1
		jsApiList: ['onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function() {
		console.log('config信息验证通过');
	});
	wx.error(function(res) {
		console.log(res);
	});
	wx.onMenuShareAppMessage({
		title: '测试', // 分享标题
		desc: '测试的点点滴滴', // 分享描述
		link: 'index.html', // 分享链接
		imgUrl: 'img/redPacket.png', // 分享图标
		type: 'link', // 分享类型,music、video或link，不填默认为link
		dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		success: function() {
			showSuccess('fenxiangchenggonb');
		},
		cancel: function() {
			showInfo('info');
		}
	});
}

/* 发红包 */
function sendPacket() {
	var redPacketCount = $("#redPacketCount").val(),
		redPacketIntegral = $("#redPacketIntegral").val(),
		redPacketName = $("#redPacketName").val();
	var $json = {
			'redPacketCount': redPacketCount,
			'redPacketIntegral': redPacketIntegral,
			'redPacketName': redPacketName
		},
		uri = '../grantRedPacket.do';
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
		ajaxPost(uri, $json, function(jsonStr) {
			var obj = jsonStr.obj;
			var redPacketUrl = obj.redPacketUrl;
			showDialog('packet');
		});
	}
}

/* 复制红包链接 */
function copyLink() {
	var txt = $("#copyLink").val();
	//showSuccess('复制成功');
	setTimeout(function() {
		closeDialog('packet');
	}, 1800);
}