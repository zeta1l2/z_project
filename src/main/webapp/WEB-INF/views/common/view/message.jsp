<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="inner-block">
	<div class="inbox">
		<h2>Inbox</h2>
		<div class="col-md-4 compose">
			<a href="#tab1" data-toggle="tab">
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
			</a>
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
					<ul id="user_list" style="overflow-y:auto;overflow-x:hidden; max-height:380px;">	
					</ul>
				</nav>
			</div>

		</div>
		<div class="col-md-8 mailbox-content  tab-content tab-content-in">
			<div class="tab-pane active text-style" id="tab1">
				<div class="mailbox-border" style="height:500px; overflow-y:auto;overflow-x:hidden;">
					<table class="table tab-border">
						<tbody id="chat_box">
							<tr class="unread checked" style="background-color:#E4DDFA ">
								<td class="chat_img">
								<img src="${pageContext.request.contextPath}/images/avatar/${userId}.jpg">
								</td>
								<td>${userId }</td>
								<td>Lorem Ipsum is simply</td>
								<td></td>
								<td>12 march</td>
							</tr>
							<tr class="unread checked" style="background-color:#E0FDED">
								<td></td>
								<td></td>
								<td class="chat_img"></td>
								<td>12 march</td>
								<td>Lorem Ipsum is simply</td>
							</tr>
						</tbody>
					</table>
				</div>
				<hr style="border: 1px solid #FC8213">
				<div class="col-xs-10">
					<textarea style="height: 80px; resize: none;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요." maxlength="100"></textarea>
				</div>
				<div class="col-xs-2">
					<button type="button" class="btn btn-default pull-right" onclick="sumbitFunction();">전송</button>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>