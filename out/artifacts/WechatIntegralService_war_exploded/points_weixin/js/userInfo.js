$(function(){
	getUserInfo();
});

/* 用户解绑 */
function unbundling(){
	var uri='../cancelBinding.do';
	ajaxPost(uri,{},function(jsonStr){
		showSuccess('帐号解绑成功,将跳转至绑定页面');
		setTimeout(function(){
			location.href='binding.html';
		},1800);
	});
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
