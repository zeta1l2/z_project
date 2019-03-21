var idCheck = 0;
var pwdCheck = 0;
function checkId(){
	idCheck=0;
	var userid=document.join.m_id.value;
	
	$.ajax({
		method : "post",
    	url : "/z_project-beta/registerCheck",
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
function a_log(){
	getmodulus();
	var rsa=new RSAKey();
	var rsam=document.join.RSAModulus.value;
	var rase=document.join.RSAExponent.value;
	rsa.setPublic (rsam, rase);
	var userid=document.login.m_id.value;
	var userpw=document.login.m_pw.value;
	
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
}

//메시지 보내기
function sendChat(){
	$.ajax({
		type: "post",
		url: "/z_project-beta/chat",
		data: {
			toId: $('#toId').val(),
			chatContent: $('#chatContent').val(),
		},
		success: function(result){
			$('#chatContent').val('');
		}

	});
}
var lastId=0;
function chatListFunction(type){
	$.ajax({
		type:"post",
		url:"/z_project-beta/chatlist",
		dataType:"JSON",
		data: {
			toId:$('#toId').val(),
		},
		success: function(data){
			
			if(data=="")return;
			$('#chatList').empty();
			for(var i=0;i<data.length;i++){
				addChat(data[i]['toId'],data[i]['chatContent'],data[i]['chatTime'])
			}
			lastId=Number(data.last);
		}
	});
}

function addChat(chatName, chatContent, chatTime){
	$('#chatList').append('<div class="row"'+
			'<div class="col-lg-12">'+
			'<div class="media">'+
			'<a class="pull-left" href="#">'+
			'<img class="media-object img-circle" src="/z_project-beta/static/com/img/test.png" alt="">'+
			'</a>'+
			'<div class="media-body">'+
			'<h4 class="media-heading">'+
			chatName +
			'<span class="small pull-right">'+
			chatTime +
			'</span>'+
			'</h4>'+
			'<p>'+
			chatContent +
			'</p>'+
			'</div>'+
			'</div>'+
			'</div>'+
			'</div>'+
			'<hr>');
	$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
}
function getInfiniteChat(){
	setInterval(function(){
		chatListFunction(lastId);
	},3000);
}