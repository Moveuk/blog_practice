let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		// on (이벤트, 활성화시 로직)
	},
	
	save: function(){
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		// console.log(data); // 데이터 확인
		
		$.ajax().done().fail(); // ajax 통신으로 3개 데이터를 -> json Stringfy 후 전송
		
		
	}
}

index.init();