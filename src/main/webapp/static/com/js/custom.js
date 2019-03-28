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
			$('#user_list').append("<li><a href='#tab1' data-toggle='tab'>"+
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
		}

	});
}
//채팅내용 가져오기
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
			if(data=="") return;
			$('#chat_box').empty();
			for(var i=0;i<data.length;i++){
				talkList(data[i]['CHAT_TO'],data[i]['CHAT_CONTENT'],data[i]['CHAT_DATE'])
			}
			getInfiniteChat(to_id);
		}
	});
}
//채팅내용 테이블 출력
function talkList(chatId, chatContent, chatTime){
	$('#chat_box').append("<tr class='unread checked'"+
			"style='background-color:#E4DDFA '>"+
			"<td class='chat_img'>"+
			"<img onerror=this.src='/z_project-beta/images/avatar/null.jpg' src='/z_project-beta/images/avatar/"+chatId+".jpg'>"+
			"</td>"+
			"<td>"+chatId+"</td>"+
			"<td>"+chatContent+"</td>"+
			"<td></td>"+
			"<td>"+chatTime+"</td>"+
			"</tr>");
	
	$('#chat_scroll').scrollTop($('#chat_scroll')[0].scrollHeight);
}

function getInfiniteChat(to_id){
	setInterval(function(){
		getTalk(to_id);
	},10000);
}
/*채팅*/