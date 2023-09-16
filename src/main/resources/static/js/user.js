let index = {
    init:function () {
        $("#btn-save").on("click", ()=>{ //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
            this.save();
        });
    },

    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        }
        
        var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

        //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), //http body
            contentType: "application/json; charest=utf-8", //MIME타입
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
            },
        }).done(function (resp) {
            alert("회원가입이 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert("회원가입을 실패하였습니다.");
            alert(error);
        })
    },
}

index.init();