toDelLi=null;

$(function() {
	$('#cardSwipe').on('slideleft', '.mui-table-view-cell', function(event) {
		var elem = this;
		toDelLi = elem;//i.parentNode.removeChild(li);$.swipeoutClose(li);
		showDialog('cardDel');
	});
});

function cardDel(){
	toDelLi.parentNode.removeChild(toDelLi);
	closeDialog('cardDel');
}
function cardDelCancel(){
	mui.swipeoutClose(toDelLi);
	closeDialog('cardDel');
}
