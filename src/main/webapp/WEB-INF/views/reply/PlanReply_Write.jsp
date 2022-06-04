<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/planboard/scripts.js"></script>
 
<link href="../css/planboard/styles.css" rel="stylesheet" /> 


</head>
<body>
<!-- Comments section-->
<section class="mb-5">
  <div class="card bg-light">
    <div class="card-body">
       
<!-- Comment form-->
<form class="mb-4" name="writeForm" method="post" action="${pageContext.request.contextPath}/reply/planinsert" >
  <textarea class="form-control" rows="2" placeholder="댓글을 입력하세요"></textarea>
</form>

<div align="right">
  <button onclick="location = '${pageContext.request.contextPath}/board/Planboard_Detail'" class="btn btn-primary py-1 px-2" type="submit" id="sendMessageButton">등록하기</button>
</div>
                                  
</div>
</div><p><p>

<div align="right">
  <textarea readonly class="form-control">${planReply.pboardReplyContent}</textarea>
   <button onclick="location = '${pageContext.request.contextPath}/reply/PlanReply_Write'" class="btn btn-primary py-1 px-2" type="submit" id="sendMessageButton">수정</button>
   <button onclick="location = '${pageContext.request.contextPath}/reply/PlanReply_Write'" class="btn btn-primary py-1 px-2" type="submit" id="sendMessageButton">삭제</button>
</div>

</section>

</body>
</html>