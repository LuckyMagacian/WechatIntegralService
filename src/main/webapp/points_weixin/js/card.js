/* 全局变量 */
toDelLi = null;

$(function() {
	//getCardList();

	$('#cardSwipe').on('slideleft', '.mui-table-view-cell', function(event) {
		var elem = this;
		toDelLi = elem; //i.parentNode.removeChild(li);$.swipeoutClose(li);
		showDialog('cardDel');
	});
});

function cardDel() {
	toDelLi.parentNode.removeChild(toDelLi);
	closeDialog('cardDel');
}

function cardDelCancel() {
	mui.swipeoutClose(toDelLi);
	closeDialog('cardDel');
}

/* 获取用户卡券列表 */
function getCardList() {
	var uri = '../coupons.do';
	ajaxPost(uri, {}, function(jsonStr) {
		var $row = jsonStr.obj;
		$.each($row, function(i) {
			var row = this;
			var temp = '<li class="mui-table-view-cell" id=' + row.id + '><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red">删除</a></div>' +
				'<a class="weui-media-box weui-media-box_appmsg mui-slider-handle" href="javascript:cardDetail(\'' + row.id + '\')"><div class="weui-media-box__hd"><img class="weui-media-box__thumb" src="img/' + row.imageCode + '"/></div>' +
				'<div class="weui-media-box__bd"><label>' + row.beiy + '</label><p class="cardConent"><span class="red">' + row.price + '元</span>代金券</p></div>' +
				'<div class="cardDeadline"><span>有效期至:<label>' + datetimeSkyle(row.startTime) + '</label></span></div></a></li>';5
			$("#cardSwipe").append(temp);
		});
	});
}

/* 卡券详情 */
function cardDetail(id){
	
}
