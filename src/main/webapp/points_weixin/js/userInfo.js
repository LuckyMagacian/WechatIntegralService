$(function(){
	getUserInfo();
});

/* 用户解绑 */
function unbundling(){
	
}

/* 获取用户信息 */
function getUserInfo(){
	var uri="../userInfo.do";
	ajaxPost(uri,{},function(jsonStr){
		var idCard=jsonStr.idCard,
			phone=jsonStr.phone,
			nickname=jsonStr.nickname;
		$("#idCard").text(idCard);
		$("#phone").text(phone);
		$("#nickname").text(nickname);
	});
}
