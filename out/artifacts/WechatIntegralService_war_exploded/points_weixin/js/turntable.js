$(function() {
	$("#pointer").rotate({
		bind: {
			click: clickFun = function() {
				$("#pointer").unbind("click", clickFun); //移除监听事件，并在转动结束后添加监听
				var prizeNum = "0", //默认奖品（谢谢参与）
					angle = prize(prizeNum); //默认旋转至谢谢参与
				_this = this;
				/*$.ajax({
					type: "get",
					url: "../turnTable/getWinningResult.do",
					cache: false,
					dataType: 'json',
					contentType: 'application/x-www-form-urlencoded',
					success: function(jsonStr) {
						if (jsonStr.result == "-1") {
							getDialog(jsonStr);
						} else {
							if (jsonStr.result != "") {
								prizeNum = jsonStr.result;
							}
							if (prize(prizeNum) !== false) {
								angle = prize(prizeNum);
							}
							rotateAngle(angle, getDialog, jsonStr);
						}
					},
					error: function() {
						alert("请求提交失败！请重试");
						$("#pointer").bind("click",clickFun);//添加监听
					}
				});*/
				var token = getCookie('token');
				ajaxPost('../game.do', {
					'token': token,
					'gameId': '1001'
				}, function(jsonStr) {
					if(jsonStr.obj != "") {
						prizeNum = jsonStr.obj;
					}
					var tempAngle=prize(prizeNum);
					if(tempAngle !== false) {
						angle = tempAngle;
					}
					rotateAngle(angle, getDialog, jsonStr);
				});
			}
		}
	});
	window.onresize = function() {
		setHeight();
	};
	setHeight();
	setHeight();
});

/* 设置各种高度  */
function setHeight() {
	_width = $(window).width(), //浏览器宽度
		_height = $(window).height(), //浏览器高度
		_picH = 673, _picW = 480, //背景图片的原始宽高（px）
		_tableW = 354, _pointW = 90, //转盘，指针的原始宽度（px）
		_tableP = 249; //转盘padding值
	var bgH, turntableH, tablePadding, pointW;
	if(_width > 320 && _width < 480) {
		bgH = _picH * _width / _picW,
			turntableH = _tableW * _width / _picW,
			tablePadding = _tableP * _width / _picW,
			pointW = _pointW * _width / _picW;
	} else if(_width > 479) { //浏览器宽度大于等于480px，则使用原始值
		bgH = _picH;
		turntableH = _tableW;
		tablePadding = _tableP,
			pointW = _pointW;
	} else {
		bgH = _picH * 2 / 3;
		turntableH = _tableW * 2 / 3;
		tablePadding = _tableP * 2 / 3,
			pointW = _pointW * 2 / 3;
	}

	$("#lottery").css("height", bgH + "px"); //设置背景图片高
	$("#turntable").css({ //设置转盘样式
		height: turntableH + "px",
		width: turntableH + "px",
		paddingTop: tablePadding + "px"
	});
	$("#pointer").css({ //设置指针样式
		height: turntableH + "px",
		width: pointW + "px",
		lineHeight: turntableH + "px"
	});
}

/** 获取2个值之间的随机数 
 *  头尾各去掉5度（去除转动至边缘的可能）
 */
function randomAngle(min, max) {
	var Range = max - min - 10;
	var Rand = Math.random();
	return(min + Math.round(Rand * Range) + 5);
}
/** 获取0~num之间的随机整数 */
function randomNum(num) {
	var num = Math.floor(Math.abs(num));
	return Math.round(num * Math.random());
}

/** 设置弹出框
 *  获奖用户提交手机号
 * code代表特定的含义 -1:积分不足 0：谢谢参与 1：再来一次
 *  9：特等奖 8：一等奖 7：二等奖 6：三等奖
 * */
function getDialog(jsonStr) {
	var code = jsonStr.result, //获奖代码
		//code=jsonStr.retCode,
		msg = "未中奖", //获奖提示
		id = jsonStr.openId; //用户id
	$("#openId").val(id);
	if(code == "9" || code == "8" || code == "7" || code == "6") {
		$("#prizeModel").removeClass("noPrize");
		$(".sorry").addClass("hide");
		$(".prost").removeClass("hide");
		switch(code) {
			case "9":
				msg = "特等奖";
				break;
			case "8":
				msg = "一等奖";
				break;
			case "7":
				msg = "二等奖";
				break;
			case "6":
				msg = "三等奖";
				break;
		}
		$(".retMsg").text(msg);
	} else {
		$("#prizeModel").addClass("noPrize");
		$(".prost").addClass("hide");
		$(".sorry").removeClass("hide");
		if(code == "1") {
			msg = "获得再来一次！";
			$("#turnTime").val(0);
		} else if(code == "0")
			msg = "谢谢参与";
		else if(code == "-1")
			msg = "您的积分不足";
		else msg = "系统维护，请刷新后重试";

		$(".retMsg").text(msg);
	}
	$("#pointer").bind("click", clickFun); //添加事件监听
	$("#prizeModel").removeClass("hide");
	$("#fullBlack").removeClass("hide");
	return true;
}
/** 转盘转动
 *  angle：转动角度  callback：回调函数
 *  */
function rotateAngle(angle, callback, jsonStr) {
	$(_this).rotate({
		duration: 3000,
		angle: 0,
		animateTo: 1440 + angle,
		easing: $.easing.easeOutSine,
		callback: function() {
			var integral = $("#integral").text(),
				time = $("#turnTime").val();
			if(time != "0") {
				$("#integral").text(integral - 10); //转动一次扣10积分
			}
			$("#turnTime").val(parseInt(time) + 1); //转动次数加1
			callback(jsonStr);
		}
	});
}

/** num代表特定的含义 -1:积分不足 0：谢谢参与 1：再来一次
 *  9：特等奖 8：一等奖 7：二等奖 6：三等奖
 *  prize函数调用了随机数函数，将转动至该奖项内的随机位置
 * */
function prize(num) {
	var seat = 0, //默认位置
		turnAngle = 20; //默认转动角度
	switch(num) {
		case "0": //特等奖 270~315
			return randomAngle(270, 315);
			break;
		case "1": //一等奖 0~45
			return randomAngle(0, 45);
			break;
		case "2": //二等奖 90~135
			return randomAngle(90, 135);
			break;
		case "3": //三等奖 180~225
			return randomAngle(180, 225);
			break;
		default://未中奖,谢谢参与 45~90 225~270 315~360
			seat = randomNum(2); //0,1,2
			switch(seat) {
				case 0:
					return randomAngle(45, 90);
					break;
				case 1:
					return randomAngle(225, 270);
					break;
				case 2:
					return randomAngle(315, 360);
					break;
				default:
					console.log("[ERROR]：seat num undefined!");
					return false;
					break;
			}
			break;
	}
}

function closeModel(id) {
	$("#" + id).addClass("hide");
	$("#fullBlack").addClass("hide");
	clearDialog();
}

/* 提交中奖数据 */
function getPrizeInfo() {
	var phoneResult = validatePhone('prizePhone'),
		phone = $("#prizePhone").val(),
		openId = $("#openId").val();
	if(phoneResult) {
		$.ajax({
			type: "post",
			url: "../turnTable/getPrize.do",
			cache: false,
			dataType: 'json',
			data: {
				"openId": openId,
				"phone": phone
			},
			contentType: 'application/x-www-form-urlencoded',
			success: function(jsonStr) {
				var msg = jsonStr.retMsg,
					code = jsonStr.retCode;
				switch(code) {
					case "0000": //兑换成功
						alert("奖品已通过短信下发至您的手机中，请注意查收。");
						break;
					case "9001":
						alert("兑奖失败！您未中奖。");
						break;
					case "9002":
						alert("兑奖失败！您已领取过奖励。");
						break;
					case "9003":
						alert("兑奖失败！您的奖项已过期。");
						break;
					case "9999":
						alert("系统错误，请联系客服400-055-2797");
						break;
					default:
						alert("系统维护中，请联系客服400-055-2797");
						break;
				}
				closeModel("prizeModel");
			},
			error: function() {
				$("#dialogHint").text("兑换请求提交失败，请重试！");

			}
		});
	}
}

/** 通用验证函数 
    regStr:正则式
    vailStr:待验证字符串
    id:效果显示在该id上
*/
function pubValidate(regStr, valiStr) {
	var flag = 0;
	if(!regStr.test(valiStr)) flag = 1; //test()方法搜索字符串指定的值，根据结果并返回真或假。
	else flag = 0;
	if(flag) //匹配不正确
		return false;
	else //匹配正确
		return true;
}

/** 手机号码验证 */
function validatePhone(id) {
	var regStr = /^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/, //匹配手机号
		idObj = $("#" + id),
		valiStr = idObj.val(),
		result = pubValidate(regStr, valiStr);
	if(result) {
		idObj.removeClass("inputError");
		idObj.addClass("inputSussess");
		$("#dialogHint").html("&nbsp;");
	} else {
		idObj.removeClass("inputSussess");
		idObj.addClass("inputError");
		$("#dialogHint").text("您输入的手机号码有误！");
	}
	return result;
}

/* 清除用户输入痕迹 */
function clearDialog() {
	$("#prizePhone").val("");
	$("#dialogHint").html("&nbsp;");
	$("#prizePhone").removeClass("inputError");
	$("#prizePhone").removeClass("inputSussess");
}

/* POINTS-WEIXIN 新增 */
/* 获取游戏基础数据-----奖品列表 */
function getGameInfo() {
	var token = getCookie('token'),
		uri = '../getGifts.do';
	ajaxPost(uri, {
		'token': token,
		'gameId': '1001'
	}, function(jsonStr) {
		var obj = jsonStr.obj;
		$.each(obj, function(i) {
			var $row = this;
			switch($row.prizeLevel) {
				case 0: //特等奖
					$("#zeroPrize").text($row.name + $row.count + '份');
					break;
				case 1:
					$("#onePrize").text($row.name + $row.count + '份');
					break;
				case 2:
					$("#twoPrize").text($row.name + $row.count + '份');
					break;
				case 3:
					$("#threePrize").text($row.name + $row.count + '份');
					break;
				default:
					console.log("未定义prizeLevel:" + $row.prizeLevel);
					break;
			}
		});
	});
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
		$("#integral").text(integralValue);
	});
}