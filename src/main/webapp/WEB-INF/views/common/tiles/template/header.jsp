<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:url var="path" value="/"/>
<link rel="stylesheet" href="<c:url value='/css/login_form.css'/>">
<script src="${path}js/login_form.js"></script>
<script src="${path}js/rsa.js"></script>
<script src="${path}js/jsbn.js"></script>
<script src="${path}js/prng4.js"></script>
<script src="${path}js/rng.js"></script>
<div class="mother-grid-inner">
	<!--header start here-->
	<div class="header-main fixed">
		<div class="header-left">
			<div class="logo-name">
				<a href="index.html">
					<h1>Shoppy</h1> <!--<img id="logo" src="" alt="Logo"/>-->
				</a>
			</div>
			<!--search-box-->
			<div class="search-box">
				<form>
					<input type="text" placeholder="Search..." required=""> <input
						type="submit" value="">
				</form>
			</div>
			<!--//end-search-box-->
			<div class="clearfix"></div>
		</div>
		<div class="header-right">
			<div class="profile_details_left">
				<!--notifications of menu start -->
				<ul class="nofitications-dropdown">
					<li class="dropdown head-dpdn"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-expanded="false"><i class="fa fa-envelope"></i><span
							class="badge">3</span></a>
						<ul class="dropdown-menu">
							<li>
								<div class="notification_header">
									<h3>You have 3 new messages</h3>
								</div>
							</li>
							<li><a href="#">
									<div class="user_img">
										<img src="images/p4.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li class="odd"><a href="#">
									<div class="user_img">
										<img src="images/p2.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li><a href="#">
									<div class="user_img">
										<img src="images/p3.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li>
								<div class="notification_bottom">
									<a href="#">See all messages</a>
								</div>
							</li>
						</ul></li>
					<li class="dropdown head-dpdn"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-expanded="false"><i class="fa fa-bell"></i><span
							class="badge blue">3</span></a>
						<ul class="dropdown-menu">
							<li>
								<div class="notification_header">
									<h3>You have 3 new notification</h3>
								</div>
							</li>
							<li><a href="#">
									<div class="user_img">
										<img src="images/p5.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li class="odd"><a href="#">
									<div class="user_img">
										<img src="images/p6.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li><a href="#">
									<div class="user_img">
										<img src="images/p7.png" alt="">
									</div>
									<div class="notification_desc">
										<p>Lorem ipsum dolor</p>
										<p>
											<span>1 hour ago</span>
										</p>
									</div>
									<div class="clearfix"></div>
							</a></li>
							<li>
								<div class="notification_bottom">
									<a href="#">See all notifications</a>
								</div>
							</li>
						</ul></li>
					<li class="dropdown head-dpdn"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-expanded="false"><i class="fa fa-tasks"></i><span
							class="badge blue1">9</span></a>
						<ul class="dropdown-menu">
							<li>
								<div class="notification_header">
									<h3>You have 8 pending task</h3>
								</div>
							</li>
							<li><a href="#">
									<div class="task-info">
										<span class="task-desc">Database update</span><span
											class="percentage">40%</span>
										<div class="clearfix"></div>
									</div>
									<div class="progress progress-striped active">
										<div class="bar yellow" style="width: 40%;"></div>
									</div>
							</a></li>
							<li><a href="#">
									<div class="task-info">
										<span class="task-desc">Dashboard done</span><span
											class="percentage">90%</span>
										<div class="clearfix"></div>
									</div>
									<div class="progress progress-striped active">
										<div class="bar green" style="width: 90%;"></div>
									</div>
							</a></li>
							<li><a href="#">
									<div class="task-info">
										<span class="task-desc">Mobile App</span><span
											class="percentage">33%</span>
										<div class="clearfix"></div>
									</div>
									<div class="progress progress-striped active">
										<div class="bar red" style="width: 33%;"></div>
									</div>
							</a></li>
							<li><a href="#">
									<div class="task-info">
										<span class="task-desc">Issues fixed</span><span
											class="percentage">80%</span>
										<div class="clearfix"></div>
									</div>
									<div class="progress progress-striped active">
										<div class="bar  blue" style="width: 80%;"></div>
									</div>
							</a></li>
							<li>
								<div class="notification_bottom">
									<a href="#">See all pending tasks</a>
								</div>
							</li>
						</ul></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<!--notification menu end -->
			<div class="profile_details">
				<c:choose>
					<c:when test="${empty userId}">
						<input type="button" class="btn btn-success btn mb-2 mr-sm-2"
							id="login_btn"
							onclick="document.getElementById('login').style.display='block'"
							value="Login" style="margin: 0px;" />
						<!-- 로그인 화면 -->
						<div class="login-page modal" id="login">
							<div class="login-main animate">
								<div class="login-head imgcontainer">
								<span onclick="document.getElementById('login').style.display='none'" class="close" title="Close Modal">&times;</span>
									<h1>Login</h1>
								</div>
								<div class="login-block">
									<form>
										<input type="text" name="email" placeholder="ID"
											required="" class="form-control">
										<input type="password" name="password"
											class="lock form-control" placeholder="Password">
										<div class="forgot-top-grids">
											<div class="forgot">
												<a href="#">Forgot password?</a>
											</div>
											<div class="clearfix"></div>
										</div>
										<input type="submit" name="Sign In" value="Login">
										<h3>
											<button type="button" onclick="getmodulus(), document.getElementById('login').style.display='none',document.getElementById('singup').style.display='block'" class="joinbtn">SignUp</button>
										</h3>
										<h2>or login with</h2>
										<div class="login-icons">
											<ul>
												<li><a href="#" class="facebook"><i
														class="fa fa-facebook"></i></a></li>
												<li><a href="#" class="twitter"><i
														class="fa fa-twitter"></i></a></li>
												<li><a href="#" class="google"><i
														class="fa fa-google-plus"></i></a></li>
											</ul>
										</div>
									</form>
									<h5>
										<a href="${path }home">Go Back to Home</a>
									</h5>
								</div>
							</div>
						</div>
						<!-- 회원가입 창 -->
						
						<div class="signup-page-main modal" id="singup">
							<div class="signup-main">
								<div class="signup-head">
									<h1>Sign Up</h1>
								</div>
								<div class="signup-block">
									<form name="join" onsubmit="signup()">
										<input type="hidden" id="RSAModulus" value="${RSAModulus}"/>
        								<input type="hidden" id="RSAExponent" value="${RSAExponent}"/>
										<input class="form-control id" type="text" id="userId" name="userId" maxlength="20" placeholder="아이디를 입력하세요."  onfocusout="checkId()">
										<input onkeyup="checkPw()" class="form-control" id="userPw" type="password" name="userPw" maxlength="20" placeholder="비밀번호를 입력하세요.">
										<input onkeyup="checkPw()" class="form-control" id="userPw2" type="password" name="userPw2" maxlength="20" placeholder="비밀번호확인을 입력하세요.">
										<input type="submit" name="Sign In" value="Sign up">
									</form>
									<div class="sign-down">
										<h4>
											Already have an account?
											<button type="button" onclick="document.getElementById('login').style.display='block',document.getElementById('singup').style.display='none'" class="joinbtn">Login</button>
										</h4>
										<h5>
											<a href="${path}home">Go Back to Home</a>
										</h5>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<tiles:insertAttribute name="logout" />
					</c:otherwise>
				</c:choose>

			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<script>
	$(document).ready(function() {
		var navoffeset = $(".header-main").offset().top;
		$(window).scroll(function() {
			var scrollpos = $(window).scrollTop();
			if (scrollpos >= navoffeset) {
				$(".header-main").addClass("fixed");
			} else {
				$(".header-main").removeClass("fixed");
			}
		});

	});
</script>