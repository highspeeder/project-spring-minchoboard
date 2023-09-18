let index = {
    init: function () {
        $("#btn-save").on("click", () => { //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
            this.save();
        });

        $("#btn-delete").on("click", () => { //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
            this.deleteById();
        });

        $("#btn-update").on("click", () => { //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
            this.update();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data), //http body
            contentType: "application/json; charest=utf-8", //MIME타입
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
        }).fail(function (error) {
            alert("글쓰기가 실패 하였습니다.");
            alert(error);
        }).done(function (resp) {
            alert("글쓰기가 완료 되었습니다.");
            location.href = "/";
        })
    },

    deleteById: function () {
        let id = $("#id").text();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id,
            dataType: "json",
            contentType: "application/json; charest=utf-8", //MIME타입
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
        }).done(function (resp) {
            alert("삭제가 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert("삭제가 실패 하였습니다.");
            alert(error);
        })
    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data), //http body
            contentType: "application/json; charest=utf-8", //MIME타입
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
        }).done(function (resp) {
            alert("글수정이 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert("글수정이 실패 하였습니다.");
            alert(error);
        })
    },
}

index.init();