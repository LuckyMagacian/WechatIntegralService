$(function(){
	paramsArr = getParam(); //获取url参数数组
	console.log(paramsArr);
});

function getInfo(){
	var uri='../infoJf.do';
	ajaxPost(uri,{token:paramsArr.token},function(jsonStr){
		alert(JSON.stringify(jsonStr));
	});
}
