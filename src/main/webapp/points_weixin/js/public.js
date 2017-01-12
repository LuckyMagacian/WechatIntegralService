$(function() {
	mui.init();
});

/* 跳转 */
function hrefTo(linkStr) {
	location.href = linkStr + '.html';
}
/* 显示弹窗 */
function showDialog(str) {
	$('#dialog-' + str).removeClass('hidden');
}
/* 关闭弹窗 */
function closeDialog(str) {
	$('#dialog-' + str).addClass('hidden');
}

/* 发送短信 */
function sendMsg() {

}