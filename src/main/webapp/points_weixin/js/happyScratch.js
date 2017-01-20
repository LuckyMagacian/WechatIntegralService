prize = new Array('30元水果通兑券', '联通20M或移动/电信10M流量', '20积分', '10积分', '谢谢参与');
pic = new Array('1381977809.jpg', '1234434297.jpg', '1439236228.jpg', '13423545841.jpg', '5892624634.jpg');

range = 4; //获奖等级
$(function() {
	var flag = 1, //刮奖标记
		timeFlag=0,//刮奖多次标记
		token = getCookie('tookie'); //获取积分
	 getInfo();
	window.onresize = function() {
		setHeight();
	};
	setHeight();
	$('#scratch').wScratchPad({
		size: 20,
		bg: 'img/happyScratch/restricted.png',
		fg: 'img/happyScratch/cover_mini.jpg',
		'cursor': 'url("img/happyScratch/coin.png") 5 5, default',
		scratchMove: function(e, percent) {
			if(flag) {
				var integral=parseInt($("#integral").text());
				if(integral < 10) {
					falseAlert('刮奖失败','您的积分不足,系统将自动跳转至首页');
					setTimeout(function(){
						location.href="index.html";
					},2000);
				} else if(timeFlag==0){
        			timeFlag=1;
					ajaxPost('../game.do', {
						'token': token,
						'gameId': '1002'
					}, function(jsonStr) {
						var result = parseInt(jsonStr.obj); //刮刮乐结果代码
						getInfo();
						if(result > -1 && result < 4) {
							$("#scratch").css("background-image", "url('img/happyScratch/" + pic[result] + "')");
							flag = 0;
							timeFlag = 0;
							showInfo('相应奖励已下发,请至卡券包内查看');
							$("#reload").removeClass("hide");
						} else {
							$("#scratch").css("background-image", "url('img/happyScratch/" + pic[4] + "')");
						}
					});
				}
			} else if(percent > 35) {
				$("canvas").fadeOut();
				if(flag) { //未正常的刮开（未向后台请求数据）
					$("#scratch").css("background-image", "url('img/happyScratch/default.jpg')");
				} else {
						$("#reload").removeClass("hide");
				}
			}

		}
	});
});

/* 设置header-pic高度  */
function setHeight() {
	var width = $(window).width(),
		_width = 480, //浏览器宽度
		_picH = 538, //背景图片的原始宽（px）
		_scrathW = 304,
		_scratchH = 110, //图层的原始宽高（px）
		_scrathMT = 11,
		_scrathML = 82, //图层margin值
		_textPT = 323,
		_textPL = 30,
		_textPR = 310, //文字 padding值
		_blackH = 540,
		_blackW = 410, //半透明背景原始宽高
		_infoW = 378; //活动信息原始宽度
	var picH, scrathW, scratchH, scrathMT, scrathMR, textPT, textPL, textPR, blackH, blackW, infoW;
	if(width > 320 && width < 480) {
		picH = parseInt(_picH * width / _width);
		scrathW = _scrathW * width / _width;
		scratchH = _scratchH * width / _width;
		scrathMT = _scrathMT * width / _width;
		scrathML = _scrathML * width / _width;
		textPT = parseInt(_textPT * width / _width);
		textPL = parseInt(_textPL * width / _width);
		textPR = parseInt(_textPR * width / _width);
		blackW = _blackW * width / _width;
		blackH = _blackH + 90 * (_width - width) / 120;
		infoW = _infoW * width / _width;

		$("header").css("height", picH + "px");
		$("#scratch").css({
			"width": scrathW + "px",
			"height": scratchH + "px",
			"margin-top": scrathMT + "px",
			"margin-left": scrathML + "px"
		});
		$("#text").css({
			"padding-top": textPT + "px",
			"padding-left": textPL + "px",
			"padding-right": textPR + "px"
		});
		$("#blackBg").css({
			"width": blackW + "px",
			"height": blackH + "px"
		});
		$("#activeInfo").css("width", infoW + "px");
	} else {
		$("header").attr("style", "");
		$("#scratch").attr("style", 'position: relative; cursor: url("img/happyScratch/coin.png") 5 5, default;');
		$("#text").attr("style", "");
		$("#blackBg").attr("style", "");
		$("#activeInfo").attr("style", "");
	}
}

/* 获取用户信息 */
function getInfo() {
	var uri = '../getInfoByToken.do';
	ajaxPost(uri, {
		'token': getCookie('token')
	}, function(jsonStr) {
		var name = jsonStr.name,
			integralValue = jsonStr.integralValue;
		$("#nickname").text(name);
		$("#integral").text(parseInt(integralValue));
		return integralValue;
	});
}