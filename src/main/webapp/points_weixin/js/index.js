$(function(){
	paramsArr = getParam(); //获取url参数数组
	getInfo();//获取信息
	getJsSign();
});

function getInfo(){
	var uri='../getInfoByToken.do';
	ajaxPost(uri,{'token':paramsArr.token},function(jsonStr){
		var name=jsonStr.name,
			integralValue=jsonStr.integralValue,
			headimgUrl=jsonStr.headimgUrl,
			token=jsonStr.token;
		$("#username").text(name);
		$("#userPoints").text(integralValue);
		$("#userPic").css('background-image','url('+headimgUrl+')');
	});
}

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
		appId: 'wxc6e892670e84ab71', // 必填，公众号的唯一标识
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
		var shareStr = {
				title: '蓝喜微信积分系统', // 分享标题
				desc: '您的好友邀请您使用蓝喜微信积分系统', // 分享描述
				link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc6e892670e84ab71&redirect_uri=http://www.188lanxi.com/WechatIntegralService/intoJf.do?&response_type=code&scope=snsapi_userinfo&state=test#wechat_redirect' // 分享链接
			};
			wx.onMenuShareTimeline(shareStr); //分享到朋友圈
			wx.onMenuShareAppMessage(shareStr); //分享给朋友
			wx.onMenuShareQQ(shareStr);
			wx.onMenuShareWeibo(shareStr);
			wx.onMenuShareQZone(shareStr);
	});
	wx.error(function(res) {
		console.log(res);
	});

}