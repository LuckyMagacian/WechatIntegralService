$(function(){
	paramsArr=getParam();
	getRedPacketInfo();
});

/* 获取抢红包列表信息 */
fucntion getPacketDetail(){
	var uri='../redPacketDetail.do',
		redPacketId=paramsArr.redPacketId;
	ajaxPost(uri,{'redPacketId':redPacketId},function(jsonStr){
		var rows=jsonStr.obj,
			today=parseInt(new Date().getDate());
		$("#getPoints").text(jsonStr.retMsg);
		$("#redPacketDetailList").html('');
		alert(JSON.stringify(rows));
		$.each(rows, function(i) {
			var row=rows[i];
			var integral=row.integral,
				nickName=row.nickName,
				img=row.beiy,
				receiveTime=row.receiveTime;
			var thisDay=parseInt(receiveTime.substr(6,2));
			if(thisDay!=today){
				receiveTime=datetimeSkyle(receiveTime);
			}else{
				receiveTime=receiveTime.substr(8,2)+':'+receiveTime.substr(10,2)+':'+receiveTime.substr(12,2);
			}
			var temp='<div class="weui-cell"><div class="weui-cell__hd"><img src="'+img+'"/></div><div class="weui-cell__bd"><p>'+
			nickName+'</p><p>'+receiveTime+'</p></div><div class="weui-cell__ft">'+integral+'积分</div></div>';
			alert(temp);
			$("#redPacketDetailList").append(temp);
		});
	});
}


/* 获取红包信息 */
function getRedPacketInfo(){
	if(paramsArr.redPacketId=='' || paramsArr.redPacketId==undefined){
		falseAlert('红包不存在','无效的红包,请关闭或返回上一页');
	}else{
		var uri='../redPacketInfo.do',
			redPacketId=paramsArr.redPacketId;
		ajaxPost(uri,{'redPacketId':redPacketId},function(jsonStr){
			var $obj=jsonStr.obj,
				nickName=$obj.nickName,
				redPacketName=$obj.redPacketName,
				img=$obj.beiy,
				redPacketCount=$obj.redPacketCount,
				redPacketLessCount=$obj.redPacketLessCount;
			nickName=(nickName==undefined || nickName=='')?'您好友':nickName;
			alert(JSON.stringify(jsonStr));
			$("#packetHeadPic").css('background-image','url('+img+')');
			$("#packetUser").text(nickName);
			$("#packeInfo").text(redPacketName);
			$("#packetNum").text(redPacketCount);
			$("#packetHave").text(redPacketLessCount);
			alert(1);
			getPacketDetail();
		});
	}
}