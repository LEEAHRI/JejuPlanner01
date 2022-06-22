<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	
<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/css/bootstrap.min.css" />
		<!-- site css -->
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/css/style.css" />
		<!-- responsive css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/css/responsive.css" />
		<!-- color css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/css/color_2.css" />
		<!-- select bootstrap -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/css/bootstrap-select.css" />	 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>		

		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

		<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>


<script type="text/javascript">
	$(function(){
		//////////파일 사진 미리보기//////////
		function readImage(input){
		    // 인풋 태그에 파일이 있는 경우
		    if(input.files && input.files[0]) {
		        // 이미지 파일인지 검사 (생략)
		        // FileReader 인스턴스 생성
		        const reader = new FileReader()
		        // 이미지가 로드가 된 경우
		        reader.onload = e => {
		            const previewImage = document.getElementById("preview-image")
		            previewImage.src = e.target.result
		        }
		        // reader가 이미지 읽도록 하기
		        reader.readAsDataURL(input.files[0])
		    }
		}
		const inputImage = document.getElementById("file")
		inputImage.addEventListener("change", e => {
		    readImage(e.target)
		})	
		///////////////////////////////
		
		
		
		//인기순/등록순관련
		$(".typeName").click(function(){
				$("#name").val($(this).val());
				selectAll(1);
		})
		
		$("#li").change(function(){	
			selectAll(1);
			
		});//click 
		

		$(document).on("click","#nowPage", function(){
			if($(this).text() == "NEXT"){
				selectAll($("#NextPage").val())
			}else if($(this).text() == "PREV"){
				selectAll($("#PrevPage").val())
			}else{
				selectAll($(this).text())
			}
		});
		
		

		
		
		function selectAll(paramNowPage){ //전,	
			if(paramNowPage == "undefined" || paramNowPage=="" || paramNowPage==null){
				paramNowPage=1;
			}
			
			$.ajax({
					url:"${pageContext.request.contextPath}/admin/all", //서버요청주소
					type:"post", // 요청방식(get, post)
					dataType:"json",//서버가 응답해주는 데이터타입(text,html,xml,json)
					data: {filter :$("#li").val(),name : $("#name").val(), nowPage :paramNowPage},//서버에게 보낼 parameter정보
					success: function(result){
						var paging ="";
						var data = "<table class='table'>";
						data+="<tr>";
						data+="<td>ID</td>";
						data+="<td>명칭</td>";
						data+="<td>설명</td>";
						data+="<td>담은 개수</td>";
						data+="</tr>";
						$.each(result.pageList.content, function(index, item){
						
						 	data+="<tr>";
							data+="<td>"+item.placeId+"</td>";
							data+=`<td><button class='btn btn-link-dark text-decoration-none' data-bs-toggle='modal' data-bs-target='#exampleModal' id='modal' placeId='\${item.placeId}' >\${item.placeName}</button></td>`;
							data+="<td>"+item.placeContent+"</td>";
							data+="<td>"+item.placeSave+"</td>";
							data+="</tr>";
							 
						})
						data+="</table>";
						
						if((result.startPage-result.blockCount) > 0){	
							paging +=`<a class='pagination-newer' href='#' id='nowPage'>PREV</a>`
							paging +=`<input type='hidden' id='PrevPage' value=${'${result.startPage-1}'}>`;
						}	
						paging +=`<span class='pagination-inner'>`;
					    for(let i=result.startPage ; i<=(result.startPage-1)+result.blockCount ;i++ ){
					    	if((i-1) >= result.totalPages)break
					    	paging +=`<a class='${i==nowPage?"pagination-active":page}' href='#'  id='nowPage'>${"${i}"}</a>`;
					    	
					    }
						paging +=`</span>`;
						if((result.startPage+result.blockCount)<=result.totalPages){	
							paging +=`<a class='pagination-older' href='#' id='nowPage'>NEXT</a>`;
							paging +=`<input type='hidden' id='NextPage' value=${'${result.startPage+result.blockCount}'}>`;
						}	

						$("#paging").html(paging);
						$("#table").html(data);
					},
					error: function(err){
						alert(err);
					}	
			});//ajax
		};
		
		
		//모달 뿌려주는 ajax
		function modalSelect(placeId){
			
			$.ajax({
					url:"${pageContext.request.contextPath}/admin/modalSelect", 
					type:"post", 
					dataType:"json",
					data: {placeId : placeId},
					success: function(result){
						$("#placeId").val(result.placeId);
						$("#placeCategory").val(result.placeCategory);
						$("#placeName").val(result.placeName);
						$("#placeContent").html(result.placeContent);
						$("#placeAddr").val(result.placeAddr);
						$("#placeLatitude").val(result.placeLatitude);
						$("#placeLongitude").val(result.placeLongitude);
						$("#placeUrl").val(result.placeUrl);
						$("#file").val(result.file);
						$("#delete").val(result.placeId);
						
						
						//readImage(result.placePhoto);
						
						
					},
					error: function(err){
						alert(err);
					}	
			});//ajax
		};
		$(document).on("click","#modal", function(){
			var targetPlace = $(this).attr('placeId')
			
			modalSelect(targetPlace);
		});

		$("#delete").click(function(){
			  $("#modalForm").attr("action", "${pageContext.request.contextPath}/admin/placeDelete");
			  $("#modalForm").submit();	
		})
		$("#update").click(function(){
			  $("#modalForm").attr("action", "${pageContext.request.contextPath}/admin/placeUpdate");
			  $("#modalForm").submit();	
		})

		
		selectAll();
	})
</script>
</head>
<body>
			

	<select id="li">
		<option value='none'>선택</option>
		<option value='placeSave'>인기순</option>
		<option value='placeId'>등록순</option>
		<option value='placeName'>가나다순</option>
	</select>
			
	
	<div class="col-md-12">
       <div class="white_shd full margin_bottom_30">
			<div class="full graph_head">
				<div class="heading1 margin_0">
                	<a href="${pageContext.request.contextPath}/admin/list">플래너 데이터 관리</a>
				</div>
					<input type="button" value="장소" id="place" class="typeName"><input type="button" value="숙소" id="home" class="typeName">
					<input type="hidden" value="selectAll" id="name">
			
			</div>
			<div class="table_section padding_infor_info">
				<div class="table-responsive-sm" id="table">
					테이블 장소
				</div>
				<div class="row">
				<div class="container">
					<div class="row">
						<div class="col-lg-12 text-center">
							<div class="pagination-wrap" id="paging"> 
                 
							</div>
        				</div>
					</div>
 				</div>
					<a href="/admin/insertForm" >등록하기</a>
				</div>  
			</div>
		</div>
    </div>	
<div id="test"></div>
	 






<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" id="modalForm" action="${pageContext.request.contextPath}/admin/placeUpdate" enctype="multipart/form-data">	
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">플래너 데이터 관리</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        
	 		<div class="col-xxl">
                  <div class="card mb-4">
                    <div class="card-header d-flex align-items-center justify-content-between">
                      <h5 class="mb-0">Basic Layout</h5>
                      <small class="text-muted float-end">Default label</small>
                    </div>
                    <div class="card-body">
                      
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-name">분류</label>
                          
                         <div class="col-sm-10">
                        	<input type="hidden" id="placeId" class="form-control placeId-mask" name="placeId" />
                         	<input type="text" id="placeCategory" class="form-control placeCategory-mask" name="placeCategory"/>
                          </div>
                       </div>
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-company">명칭</label>
                          <div class="col-sm-10">
                            <input type="text" id="placeName" class="form-control placeName-mask" placeholder="내용을 입력해주세요." name="placeName"  />
                          </div>
                       </div>
                        
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-message">설명</label>
                          <div class="col-sm-10">
                            <textarea id="placeContent" class="form-control" placeholder="내용을 입력해주세요." name="placeContent"></textarea>
                          </div>
                       </div>
                        
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-phone">주소</label>
                          <div class="col-sm-10">
                            <input type="text" id="placeAddr" class="form-control addr-mask" placeholder="내용을 입력해주세요." name="placeAddr"/>
                          </div>
                       </div>
                        
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-phone">위도</label>
                          <div class="col-sm-10">
                            <input type="text" id="placeLatitude" class="form-control placeLatitude-mask" placeholder="ex)125.3032" name="placeLatitude"/>
                          </div>
                       </div>
                        
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-phone">경도</label>
                          <div class="col-sm-10">
                            <input type="text" id="placeLongitude" class="form-control placeLongitude-mask" placeholder="ex)45.3032" name="placeLongitude"/>
                          </div>
                       </div>
                       
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-phone">연결 링크</label>
                          <div class="col-sm-10">
                            <input type="text" id="placeUrl" class="form-control URL-mask" name="placeUrl"/>
                          </div>
                       </div>
                       
                       <div class="row mb-3">
                          <label class="col-sm-2 col-form-label" for="basic-default-phone">첨부 사진</label>
                          <div class="col-sm-10">
                          
                          	<input type="file" id="file" name="file" accept=".png, .jpg]"/>
                          	<img style="width: 100px;" id="preview-image" src="https://dummyimage.com/500x500/ffffff/000000.png&text=preview+image">
                          </div>
 
                       </div> 
                    </div>
                  </div>
                </div>

      </div>
      <div class="modal-footer">
        <button id="delete" type="button" class="btn btn-danger" value="">삭제</button>
        <button id="update" type="button" class="btn btn-primary">수정하기</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </div>
      </form>  
    </div>
  </div>
</div>





</body>

</html>