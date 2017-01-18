(function() {
	mui.init({
		subpages: [{
			url: "pointsDetailList.html",
			id: "pointsDetailList",
			styles: {
				top: "175px"
			}
		}]
	});
	getPointsDetail();

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