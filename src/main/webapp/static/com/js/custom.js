/*회원*/
var idCheck = 0;
var pwdCheck = 0;
//중복체크
function checkId(){
	idCheck=0;
	var userid=document.join.m_id.value;

	$.ajax({
		method : "post",
    	url : "/z_project-beta/register_check",
		data : {
			m_id : userid,
		},
		success : function(data){
			if(userid==""&& data=='0'){
				$(".signupbtn").prop("disabled",true);
				$(".signupbtn").css("background-color","#aaaaaa");
				$("#m_id").css("background-color","#ffcece");
				$("#id_check").empty();
				 $("#id_check").append("아이디를 입력해주세요.")
				idCheck = 1;
			}else if (data =='0'){
				  $("#m_id").css("background-color", "#B0F6AC");
                  idCheck = 1;
                  $("#id_check").empty();
                  if(idCheck==1 && pwdCheck == 1) {
                      $(".signupbtn").prop("disabled", false);
                      $(".signupbtn").css("background-color", "#4CAF50");
                  }
              }else if (data == '1') {
                  $(".signupbtn").prop("disabled", true);
                  $(".signupbtn").css("background-color", "#aaaaaa");
                  $("#m_id").css("background-color", "#FFCECE");
                  idCheck = 0;
                  $("#id_check").empty();
                  $("#id_check").append("이미 아이디가 존재합니다.")
              }
		},

	})
}
//재입력 비밀번호 체크하여 가입버튼 비활성화 또는 맞지않음을 알림.
function checkPw() {
    var inputed = $('#m_pw').val();
    var reinputed = $('#m_pw2').val();
    if(reinputed=="" && (inputed != reinputed || inputed == reinputed)){
        $(".signupbtn").prop("disabled", true);
        $(".signupbtn").css("background-color", "#aaaaaa");
    }
    else if (inputed == reinputed) {
        $("#m_pw2").css("background-color", "#B0F6AC");
        pwdCheck = 1;
        if(idCheck==1 && pwdCheck == 1) {
            $(".signupbtn").prop("disabled", false);
            $(".signupbtn").css("background-color", "#4CAF50");
        }
    } else if (inputed != reinputed) {
        pwdCheck = 0;
        $(".signupbtn").prop("disabled", true);
        $(".signupbtn").css("background-color", "#aaaaaa");
        $("#m_pw2").css("background-color", "#FFCECE");

    }
}
//암호화 공개키 바당옴
function getmodulus(){
	$.ajax({
		type: "get",
		url: "/z_project-beta/log",
		success: function(data){
			$('#RSAModulus').val(data['RSAModulus']);
			$('#RSAExponent').val(data['RSAExponent']);
		},
	})
}

//form 전송 암호화
function signup(){
	var rsa=new RSAKey();
	var rsam=document.join.RSAModulus.value;
	var rase=document.join.RSAExponent.value;
	rsa.setPublic (rsam, rase);
	var userid=document.join.m_id.value;
	var userpw=document.join.m_pw.value;
	var username=document.join.m_name.value;
	var userphone=document.join.m_phone.value;

	$('#m_id').val(rsa.encrypt(userid));
	$('#m_pw').val(rsa.encrypt(userpw));
	$('#m_pw2').val('');
	$('#m_name').val(rsa.encrypt(username));
	$('#m_phone').val(rsa.encrypt(userphone));
}
//로그아웃
function logout(){
	$.ajax({
		type: "get",
		url: "/z_project-beta/logout",
		success: function(){
			window.location.reload();
		}
	})
}
//암호화 로그인
function a_log(){
	$.ajax({
		type: "get",
		url: "/z_project-beta/log",
		success: function(data){
			$('#RSAModulus').val(data['RSAModulus']);
			$('#RSAExponent').val(data['RSAExponent']);
			var rsa=new RSAKey();
			rsa.setPublic (data['RSAModulus'], data['RSAExponent']);
			var userid=document.login.m_id.value;
			var userpw=document.login.m_pw.value;
			//로그인 무결성체크
			if(userid.length == 0){
				$('#login_check').empty();
				$('#login_check').append("아이디를 입력해주세요.")
				return false;
			}else if(userpw.length == 0){
				$('#login_check').empty();
				$('#login_check').append("비밀번호를 입력해주세요.")
				return false;
			}
			//로그인시 평문데이터 암호화 전송
			$.ajax({
				type:"post",
				url:"/z_project-beta/log",
				data:{
					m_id: rsa.encrypt(userid),
					m_pw: rsa.encrypt(userpw),
				},
				success: function(data){
					if(data=='1'){
						location.href="/z_project-beta/home";
					}else{
						$('#login_check').empty();
						$('#login_check').append("아이디 또는 비밀번호가 일치하지 않습니다.")
					}
				}
			})
		},
	})
}
//회원 검색
function user_list(){
	var search=$(search_user).val();
	$('#user_list').empty()
	$.ajax({
		type:"POST",
		url:"/z_project-beta/chat/box",
		dataType:"JSON",
		data:{
			m_id : search,
		},
		success: function(data){
			$('#user_list').empty()
			for(var i=0; i<data.length; i++){
			$('#user_list').append("<li><a onclick='clearInterval(inter),$(\"#user_list\").empty(),getInfiniteChat(\""+data[i]['m_id']+"\")' href='#tab1' data-toggle='tab'>"+
						"<div class='profile_img'>"+
						"<span class='prfil-img' >"+
						"<img style='width:50px; height:50px; border-radius:50%'"+
						"src='/z_project-beta/images/avatar/"+data[i]['m_id']+".jpg'"+
						"onerror=this.src='/z_project-beta/images/avatar/null.jpg'>"+
						"</span>"+
						"<div class='user-name'>"+
						"<p>"+data[i]['m_id']+"</p>"+
						"<span>"+data[i]['m_name']+"</span>"+
						"</div>"+
						"<div class='clearfix'></div>"+
						"</div>"+
						"</a></li>"	
			)
			}
		}
	});
}
/*회원*/
/*채팅*/
//메시지 보내기
var scroll_f=0;
function send_talk(){
	$.ajax({
		type: "post",
		url: "/z_project-beta/chat/talk",
		data: {
			chat_to: $('#send_to_id').val(),
			chat_content: $('#chat_content').val(),
		},
		success: function(result){
			$('#chat_content').val(''); 
			scroll_f=0;
		}

	});
}
var inter;
//채팅내용 가져오기
var day=0;
function getTalk(to_id){
	$.ajax({
		type:"get",
		url:"/z_project-beta/chat/talk",
		dataType:"JSON",
		data: {
			chat_to: to_id,
		},
		success: function(data){
			$('#send_to_id').val(to_id);
			$('#chat_box').empty();
			if(data=="") return false;
			var chatRead=1;
			for(var i=0;i<data.length;i++){
				chatRead=data[i]['CHAT_READ'];
	
				if(chatRead == 1){
					chatRead='&nbsp;(1)&nbsp;'
				}else if(chatRead == 0){
					chatRead='';
				}
				if(data[i]['CHAT_DATE'] != day){
					day=data[i]['CHAT_DATE'];
					talkListDate(day);
				}
				if(data[i]['CHAT_TO'] == to_id){
					talkList2(data[i]['CHAT_CONTENT'],data[i]['CHAT_TIME'],chatRead);
				}else{
					talkList1(data[i]['CHAT_FROM'],data[i]['CHAT_CONTENT'],data[i]['CHAT_TIME'],chatRead);
				}
			}
			var scroll_h=$('#chat_scroll').prop("scrollHeight");
			
				if(scroll_f==0 || scroll_h-$('#chat_scroll').scrollTop()<700){
					talk_read(to_id);
					scroll_f=1;
					$('#chat_scroll').scrollTop($('#chat_scroll')[0].scrollHeight);
				}
			day=0;
		}
	});
}
//채팅내용 테이블 출력
function talkList1(chatId, chatContent, chatTime,chatRead){
	$('#chat_box').append("<tr class='unread checked'"+
			"style='background-color:#E4DDFA '>"+
			"<td class='chat_img' style='width:20%'>"+
			"<img onerror=this.src='/z_project-beta/images/avatar/null.jpg' src='/z_project-beta/images/avatar/"+chatId+".jpg'>"+
			"<br><div style='text-align:center; width:50px'>"+chatId+"</div>"+
			"</td>"+
			"<td>"+chatContent+"</td>"+
			"<td></td>"+
			"<td style='text-align:right'>"+chatTime+chatRead+"</td>"+
			"</tr>");
}
function talkList2(chatContent, chatTime,chatRead){
	$('#chat_box').append("<tr class='unread checked' style='background-color:#E0FDED'>"+
								"<td>"+chatRead+chatTime+"</td>"+
								"<td class='chat_img'></td>"+
								"<td></td>"+
								"<td style='text-align:right'>"+chatContent+"</td>"+
							"</tr>");
}
function talkListDate(day){
	$('#chat_box').append("<tr class='unread checked'>"+
			"<td colspan='4' style='text-align:center'>"+day+"</td>"+
		"</tr>");
}
//채팅 반동기화
function getInfiniteChat(to_id){
	inter=setInterval(function(){
		getTalk(to_id);
	},3000);
}
//textarea
$(function() {
	  $('#chat_content').on('keydown', function(event) {
	      if (event.keyCode == 13)
	          if (!event.shiftKey){
	             event.preventDefault();
	             var button=document.getElementById('send_talk_btn');
	             button.click();
	          }
	  });
	});
//안읽은 메시지 갯수 출력
function getRead_m(){
	$.ajax({
		type : "get",
		url : "/z_project-beta/chat/read",
		dataType: "json",
		success : function(data){
			$("#talk_read").empty();
			$("#talk_read").append(data.length);
			
			$("#new_talk").empty();
			if(data.length == 0){
				$("#new_talk").append("<li>"+
										  "<div class='notification_header'>"+
										  "<h3>you have not new message</h3>"+
										  "</div>"+
										  "</li>");
				
			}else{
				$("#new_talk").append("<li>"+
						"<div class='notification_header'>"+
						"<h3>You have "+data.length+" new messages</h3>"+
						"</div>"+
				"</li>");
			for(var i=0;i<data.length;i++){
				get_new_talk(data[i]["CHAT_FROM"],data[i]["CHAT_CONTENT"],data[i]["CHAT_DATE"])
			}
			}
			$("#new_talk").append("<li>"+
						"<div class='notification_bottom'>"+
							"<a href='/z_project-beta/chat'>See all messages</a>"+
						"</div>"+
					"</li>");
			
			startTime();
		}
	})
}
//읽지 않는 최신 메시지 append
function get_new_talk(avatar,talk,time){
	$('#new_talk').append("<li><a href='/z_project-beta/chat/"+avatar+"'>"+
						   "<div class='user_img'>"+
							"<img id='avatar1' src='/z_project-beta/images/avatar/"+avatar+".jpg' onerror='this.src=\"/z_project-beta/images/avatar/null.jpg\"'>"+
							"<div style='text-align:center; width:30px'>"+avatar+"</div>"+
						   "</div>"+
						   "<div class='notification_desc'>"+
						   "<p>"+talk+"</p>"+
						   "<p>"+
						   "<span>"+time+"</span>"+
						   "</p>"+
						   "</div>"+
						   "<div class='clearfix'></div>"+
						   "</a></li>")
	
}
//이번에는 setTimeout으로 재귀함수 구현 
var timerID
function startTime(){
	timerId=setTimeout("getRead_m()",3000);
}
//읽음처리 함수
function talk_read(to_id){
	$.ajax({
		type : "POST",
		url : "/z_project-beta/chat/read",
		dataType: "json",
		data : {
			chat_to : to_id,
		}
	})
}
/*채팅*/