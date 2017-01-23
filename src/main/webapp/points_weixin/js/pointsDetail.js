(function() {
	mui.init({
		subpages: [{
			url: "pointsDetailList.html",
			id: "pointsDetailList",
			styles: {
				top: "230px"
			}
		}]
	});
	getPointsDetail();
	$("#intDetailDate").on('click',function(){
		weui.datePicker({
			start: 2000,
            end: new Date().getFullYear(),
            onConfirm: function (result) {
            	var month=result[1],
            		day=result[2];
            	month=month<10?'0'+month:month;
            	day=day<10?'0'+day:day;
                var selectDateInfo=result[0]+'-'+month+'-'+day,
                	selectDate=''+result[0]+month+day;
                $("#selectDateInfo").text(selectDateInfo);
                $("#selectDate").val(selectDate);
                document.getElementById('pointsDetailList').contentWindow.location.reload(true);
            }
		});
	});
})(mui);

function getPointsDetail() {
	var token=getCookie('token'),
		uri='../integralQuery.do',
		$json={
			'token':token,
			'page':1
		};
		ajaxPost(uri,$json,function(jsonStr){
			var deadLinePoint=jsonStr.deadLinePoint,
				addPoint=jsonStr.addPoint,
				totalPoints=jsonStr.totalPoints;
			$("#totalPoints").text(totalPoints);
			$("#addPoint").text(addPoint);
			$("#deadLinePoint").text(deadLinePoint);
		});
}