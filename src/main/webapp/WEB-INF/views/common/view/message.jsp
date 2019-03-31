<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:if test="${not empty from_id}">
		<script>
		clearInterval(inter);
		getInfiniteChat('<%=(String)request.getAttribute("from_id")%>');
		</script>
	</c:if>
<div class="inner-block">
	<div class="inbox">
		<h2>Inbox</h2>
		<div class="col-md-4 compose">
			<a href="#tab1" data-toggle="tab" onclick="clearInterval(inter),$('#user_list').empty(),getInfiniteChat('${userId}')">
			<div class="mail-profile">
				<div class="mail-pic">
					<img id="avatar"
						src="${pageContext.request.contextPath}/images/avatar/${userId}.jpg"
						onerror="this.src='/z_project-beta/images/avatar/null.jpg'">
				</div>
				<div class="mailer-name">
					<h5>${userId}</h5>
					<h6>${userGrade}</h6>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="compose-bottom" id="ff">
				<nav class="">
					<ul class="">
						<li class=""><a href="#tab1" data-toggle="tab"><input
								id="search_user" type="text" placeholder="Find your friend"
								oninput="user_list()" onclick="user_list()">
								<div class="clearfix"></div></a></li>
					</ul>
				</nav>
				<nav class="">
					<ul id="user_list">	
					</ul>
				</nav>
			</div>

		</div>
		<div class="col-md-8 mailbox-content  tab-content tab-content-in">
			<div class="tab-pane active text-style" id="tab1">
				<div class="mailbox-border" id="chat_scroll">
					<table class="table tab-border">
						<tbody id="chat_box">
						</tbody>
					</table>
				</div>
				<hr style="border: 1px solid #FC8213">
				<form class="col-xs-10" name="talk">
					<input type="hidden" value="" id="send_to_id">
					<textarea id="chat_content" class="form-control" placeholder="메시지를 입력하세요." maxlength="100" ></textarea>
				</form>
				<div class="col-xs-2">
					<button type="button" id="send_talk_btn"class="btn btn-default pull-right" onclick="send_talk()">전송</button>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>