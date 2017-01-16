$(function(){
	paramsArr=getParam();
	getRedPacketInfo();
});

/* 获取红包信息 */
function getRedPacketInfo(){
	if(paramsArr.redPacketId=='' || paramsArr.redPacketId==undefined){
		falseAlert('红包不存在','无效的红包,请关闭或返回上一页');
	}else{
		var uri='',
			redPacketId=paramsArr.redPacketId;
		ajaxPost(uri,{'redPacketId':redPacketId},function(jsonStr){
			
		});
	}
}

/* 拆红包 */
function unpackRedPacket(){
	if(paramsArr.redPacketId=='' || paramsArr.redPacketId==undefined){
		falseAlert('红包不存在','无效的红包,请关闭或返回上一页');
	}else{
		var uri='../unpackRedPacket.do',
			redPacketId=paramsArr.redPacketId;
		ajaxPost(uri,{'redPacketId':redPacketId},function(jsonStr){
			
		});
	}
}
