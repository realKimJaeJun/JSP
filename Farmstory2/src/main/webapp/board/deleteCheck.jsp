<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	const success = "${ param.success }";
	
	if(success == "300") {
		alert('삭제 완료');
		location.href="/Farmstory2/board/list.do?cate=${param.cate}&tit=${param.tit}&pg=${param.pg}&search=${param.search}";
	} else {
		location.href="/";
	}
</script>