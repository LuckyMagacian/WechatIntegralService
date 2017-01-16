$(function(){
	paramsArr = getParam(); //获取url参数数组
	getInfo();//获取信息
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
		$("#userPic").attr('background','url('+headimgUrl+') no-repeat center center');
		setCookie('token',token);
	});
}
