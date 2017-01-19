(function() {
	mui.init({
		pullRefresh: {
			container: '#pullRefresh',
			down: {
				callback: function() {
					getPointsList(1);//初次加载
				}
			},
			up: {
				contentrefresh: '正在加载...',
				container: '#pullRefresh',
				callback: function() {
					var thisPage=$("#page").val();
					getPointsList(thisPage);
				}
			}
		}
	});
	getPointsList(1);//初次加载
})(mui);

/* 加载列表 */
function getPointsList(page) {
	var token = getCookie('token'),
		uri = '../integralQuery.do',
		$json = {
			'token': token,
			'page': page
		};
	ajaxPost(uri, $json, function(jsonStr) {
		var $list = jsonStr.message,
			totalPage=jsonStr.totalPage;
		if($list == undefined || $list == '') {
			mui('#pullRefresh').pullRefresh().endPullupToRefresh(true);
		} else {
			var temp = '';
			$.each($list, function(i) {
				var row = this,
					pointsVal = Number(row.pointsVal),
					occurDate = datetimeSkyle(row.occurDate),
					pointType = row.pointType;
				if(pointsVal < 0) {
					temp += '<tr><td>' + occurDate + '</td><td class="red">' + pointsVal + '</td><td>' + pointType + '</td></tr>';
				} else if(pointsVal > 0) {
					temp += '<tr><td>' + occurDate + '</td><td class="green">' + pointsVal + '</td><td>' + pointType + '</td></tr>';
				} else {
					temp += '<tr><td>' + occurDate + '</td><td>' + pointsVal + '</td><td>' + pointType + '</td></tr>';
				}
			});
			if(page == 1) {//第一页
				$("#pointsDetailTr").html(temp);
				mui('#pullRefresh').pullRefresh().endPulldownToRefresh();
			} else {
				$("#pointsDetailTable").append(temp);
				if(page==totalPage){//最后一页
					mui('#pullRefresh').pullRefresh().endPullupToRefresh(true);
				}else{
					mui('#pullRefresh').pullRefresh().endPullupToRefresh(false);
				}
			}
			$("#page").val(page);
		}
	});
}