let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{
			this.save();
		}); // on (이벤트, 활성화시 로직)
		$("#btn-update").on("click", ()=>{
			this.update();
		}); // on (이벤트, 활성화시 로직)
	},
	
	save: function(){
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// console.log(data); // 데이터 확인
		
		// ajax 호출시 default가 비동기 호출
		// ajax 성공시 자동으로 JSON을 Object로 파싱
		$.ajax({  // ajax 통신으로 3개 데이터를 -> json Stringfy 후 전송
			//회원가입 수행 요청 (비동기 호출이므로 로직이 아무리 오래걸려도 아래 함수들과 함께 수행 가능)
			type: "POST",
			url: "/auth/joinProc", // "/join" 을 굳이 안넣음 method 가 post 면 insert 이므로
			data: JSON.stringify(data), //Object를 JSON String으로 변경
			contentType: "application/json; charset=utf-8", // 서버로 보내는 요청의 MIME Type 명시
			dataType: "json" // 서버에서 오는 응답의 타입을 미리 정해줌(오는 데이터는 String으로 옴) => javascript object로 변경
		}).done(function(resp){
			// 성공시 
			alert("회원가입이 완료되었습니다.");
			//console.log(resp); // 응답 1로 했으므로 alert 창에 1이 뜸
			location.href = "/";
		}).fail(function(error){
			// 실패시  에러 응답이 오게됨 그 응답을 error로 명명 후 alert 창에 명시
			alert(JSON.stringify(error));
		});
	},
		
	update: function(){
		//alert('user의 save함수 호출됨');
		let data = {
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};		
		$.ajax({ 
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(resp){
			alert("회원 정보 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();