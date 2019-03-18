var idCheck = 0;
var pwdCheck = 0;

function checkId(){
	idCheck=0;
	var userid=document.join.userId.value;
	$.ajax({
		method : "post",
    	url : "/z_project-beta/registerCheck",
		data : {
			userId : userid
		},
		success : function(data){
			if(userid==""&& data=='0'){
				$(".signupbtn").prop("disabled",true);
				$(".signupbtn").css("background-color","#aaaaaa");
				$("#userId").css("background-color","#ffcece");
				idCheck = 1;
			}else if (data =='0'){
				  $("#userId").css("background-color", "#B0F6AC");
                  idCheck = 1;
                  if(idCheck==1 && pwdCheck == 1) {
                      $(".signupbtn").prop("disabled", false);
                      $(".signupbtn").css("background-color", "#4CAF50");
                      signupCheck();
                  }
              }else if (data == '1') {
                  $(".signupbtn").prop("disabled", true);
                  $(".signupbtn").css("background-color", "#aaaaaa");
                  $("#userId").css("background-color", "#FFCECE");
                  idCheck = 0;
              }
		}
	})
}
//재입력 비밀번호 체크하여 가입버튼 비활성화 또는 맞지않음을 알림.
function checkPw() {
    var inputed = $('#userPw').val();
    var reinputed = $('#userPw2').val();
    if(reinputed=="" && (inputed != reinputed || inputed == reinputed)){
        $(".signupbtn").prop("disabled", true);
        $(".signupbtn").css("background-color", "#aaaaaa");
        $("#userPw2").css("background-color", "#FFCECE");
    }
    else if (inputed == reinputed) {
        $("#userPw2").css("background-color", "#B0F6AC");
        pwdCheck = 1;
        if(idCheck==1 && pwdCheck == 1) {
            $(".signupbtn").prop("disabled", false);
            $(".signupbtn").css("background-color", "#4CAF50");
            signupCheck();
        }
    } else if (inputed != reinputed) {
        pwdCheck = 0;
        $(".signupbtn").prop("disabled", true);
        $(".signupbtn").css("background-color", "#aaaaaa");
        $("#userPw2").css("background-color", "#FFCECE");

    }
}
//암호화 공개키 바당옴
function getmodulus(){
	$.ajax({
		type: "get",
		url: "/z_project-beta/module",
		dataType: 'json',
	})
}
//form 전송 암호화
function signup(){
	var rsa=new RSAKey();
	var rsam=document.join.RSAModulus.value;
	var rase=document.join.RSAExponent.value;
	
	rsa.setPublic (rsam, rase);
	var userid=document.join.userId.value;
	var userpw=document.join.userPw.value;

	var id=rsa.encrypt(userid);
	var pw=rsa.encrypt(userpw)
	alert(userid);
	$.ajax({
		type:"post",
		url: "/z_project-beta/signup",
		 async : true,
		data: {
			userId: id,
			userPw: pw,
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