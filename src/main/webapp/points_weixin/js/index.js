$(function(){
	paramsArr = getParam(); //获取url参数数组
	getInfo();//获取信息
	console.log(paramsArr);
});

function getInfo(){
	var uri='../infoJf.do';
	ajaxPost(uri,{},function(jsonStr){
		var name=jsonStr.name,
			integralValue=jsonStr.integralValue,
			headimgUrl=jsonStr.headimgUrl;
		$("#username").text(name);
		$("#userPoints").text(integralValue);
		$("#userPic").attr('background','url('+headimgUrl+') no-repeat center center');
	});
}
