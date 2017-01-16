prize=new Array('谢谢参与','10积分','20积分','50积分','联通20M或移动/电信10M流量','必胜客30元代金券','5元水果通兑券','10元水果通兑券','30元水果通兑券');
pic=new Array('5892624634.jpg','13423545841.jpg','1439236228.jpg','1123578354.jpg','1234434297.jpg','1819358713.jpg','1834678964.jpg','1987237348976.jpg','1381977809.jpg');
range=0,//获奖等级
$(function(){
	var flag=1,//刮奖标记
		timeFlag=0,//刮奖多次标记
		time=$("#number").val(),//今日刮刮乐刮奖次数
		integral=$("#integral").text();//获取积分
	window.onresize = function() {
		setHeight();
	};
	setHeight();
	$('#scratch').wScratchPad({
		size:20,
		bg:'img/happyScratch/restricted.png',
        fg: 'img/happyScratch/cover_mini.jpg',
        'cursor': 'url("img/happyScratch/coin.png") 5 5, default',
        scratchMove: function (e, percent){
        	if(flag){
        	if(integral<10 && time!=0){
        		alert("您的积分不足！");
				location.reload();
        	}
        	else if(timeFlag==0){
        		timeFlag=1;
        		$.ajax({
        			type:"post",
        			url:"../happyScratch/getWinningResult.do",
        			async:true,
        			dataType:"json",
        			success:function(jsonStr){//result
        				var result=jsonStr.result;//刮刮乐结果代码
        				range=parseInt(result);
        				if(result=="-1"){
        					alert("您的积分不足！");
        					location.reload();
        				}
        				else if(result>-1 && result<9){
        					if(time!=0)
        					$("#integral").text(integral-10);
        					$("#scratch").css("background-image","url('img/happyScratch/"+pic[result]+"')");
        					flag=0;timeFlag=0;
        				}
        				else{
        					alert("系统维护，请稍后刷新重试！");
        				}
        				
        			},
        			error:function(){
        				alert("请求超时，请刷新后重试！");
        			}
        		});
        		
        	}}
        	else if (percent > 35) {
        		$("canvas").fadeOut();
        		if(flag){//未正常的刮开（未向后台请求数据）
        			$("#scratch").css("background-image","url('img/happyScratch/default.jpg')");
        		}
        		else{
        			if(range>0){
        				$("#getPrize").removeClass("hide");
        				$("#reload").addClass("hide");
        			}
        			else{
        				$("#getPrize").addClass("hide");
        				$("#reload").removeClass("hide");
        			}
        			
        		}
        	}
        	
          }
        });
});

/* 设置header-pic高度  */
function setHeight() {
	var width = $(window).width(),_width= 480, //浏览器宽度
		_picH = 538,  //背景图片的原始宽（px）
		_scrathW = 304, _scratchH = 110, //图层的原始宽高（px）
		_scrathMT = 11,_scrathML = 82,//图层margin值
		_textPT=323,_textPL=30,_textPR=310, //文字 padding值
		_blackH=540,_blackW=410,//半透明背景原始宽高
		_infoW=378;//活动信息原始宽度
	var picH,scrathW,scratchH,scrathMT,scrathMR,textPT,textPL,textPR,blackH,blackW,infoW;
	if (width > 320 && width < 480) {
		picH=parseInt(_picH*width/_width);
		scrathW=_scrathW*width/_width;
		scratchH=_scratchH*width/_width;
		scrathMT=_scrathMT*width/_width;
		scrathML=_scrathML*width/_width;
		textPT=parseInt(_textPT*width/_width);
		textPL=parseInt(_textPL*width/_width);
		textPR=parseInt(_textPR*width/_width);
		blackW=_blackW*width/_width;
		blackH=_blackH+90*(_width-width)/120;
		infoW=_infoW*width/_width;
		
		$("header").css("height",picH+"px");
		$("#scratch").css({
			"width":scrathW+"px",
			"height":scratchH+"px",
			"margin-top":scrathMT+"px",
			"margin-left":scrathML+"px"
		});
		$("#text").css({
			"padding-top":textPT+"px",
			"padding-left":textPL+"px",
			"padding-right":textPR+"px"
		});
		$("#blackBg").css({
			"width":blackW+"px",
			"height":blackH+"px"
		});
		$("#activeInfo").css("width",infoW+"px");
	}else {
		$("header").attr("style","");
		$("#scratch").attr("style",'position: relative; cursor: url("img/happyScratch/coin.png") 5 5, default;');
		$("#text").attr("style","");
		$("#blackBg").attr("style","");
		$("#activeInfo").attr("style","");
	}
}

function showDialog(id){
	$("#" + id).removeClass("hide");
	$("#fullBlack").removeClass("hide");
	$(".retMsg").text(prize[range]);
}

function closeModel(id) {
	$("#" + id).addClass("hide");
	$("#fullBlack").addClass("hide");
	clearDialog();
	location.reload();
}

/* 提交中奖数据 */
function getPrizeInfo() {
	var phoneResult = validatePhone('prizePhone'),
		phone = $("#prizePhone").val();
	if (phoneResult) {
		$.ajax({
			type: "post",
			url: "../happyScratch/getPrize.do",
			cache: false,
			dataType: 'json',
			data: {
				"phone": phone
			},
			contentType: 'application/x-www-form-urlencoded',

			success: function(jsonStr) {
				var msg=jsonStr.retMsg,
				code=jsonStr.retCode;
				switch(code){
				case "0000"://兑换成功
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
				default:alert("系统维护中，请联系客服400-055-2797");
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
	if (!regStr.test(valiStr)) flag = 1; //test()方法搜索字符串指定的值，根据结果并返回真或假。
	else flag = 0;
	if (flag) //匹配不正确
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
	if (result) {
		idObj.removeClass("inputError");
		idObj.addClass("inputSussess");
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


