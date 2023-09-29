let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
      this.save();
    });

    $("#btn-delete").on("click", () => {
      //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
      this.deleteById();
    });

    $("#btn-update").on("click", () => {
      //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
      this.update();
    });

    $("#btn-reply-save").on("click", () => {
      //function대신 ()=>를 사용하는 이유는 this를 바인딩하기 위해
      this.replySave();
    });
  },

  save: function () {
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
    $.ajax({
      type: "POST",
      url: "/api/board",
      data: JSON.stringify(data), //http body
      contentType: "application/json; charest=utf-8", //MIME타입
      beforeSend: function (xhr) {
        /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        xhr.setRequestHeader(header, token);
      },
    })
      .fail(function (error) {
        alert("글쓰기가 실패 하였습니다.");
        //alert(error.responseText);
      })
      .done(function (resp) {
        alert("글쓰기가 완료 되었습니다.");
        location.href = "/";
      });
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
      beforeSend: function (xhr) {
        /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        xhr.setRequestHeader(header, token);
      },
    })
      .done(function (resp) {
        alert("글 삭제가 완료 되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        alert("글 삭제가 실패 하였습니다.");
        //alert(error.responseText);
      });
  },

  update: function () {
    let id = $("#id").val();

    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
    $.ajax({
      type: "PUT",
      url: "/api/board/" + id,
      data: JSON.stringify(data), //http body
      contentType: "application/json; charest=utf-8", //MIME타입
      beforeSend: function (xhr) {
        /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        xhr.setRequestHeader(header, token);
      },
    })
      .done(function (resp) {
        alert("글수정이 완료 되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        alert("글수정이 실패 하였습니다.");
        //alert(error.responseText);
      });
  },

  replySave: function () {
    let data = {
      userId: $("#userId").val(),
      boardId: $("#boardId").val(),
      content: $("#reply-content").val(),
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
      type: "POST",
      url: `/api/board/${data.boardId}/reply`, // `는 작은 따옴표(')가 아니고 백틱(`)임 변수를 글자안에 넣기 위함.
      data: JSON.stringify(data), //http body
      contentType: "application/json; charest=utf-8", //MIME타입
      beforeSend: function (xhr) {
        /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        xhr.setRequestHeader(header, token);
      },
    })
      .fail(function (error) {
        alert("댓글쓰기가 실패 하였습니다.");
        //alert(error.responseText);
      })
      .done(function (resp) {
        alert("댓글쓰기가 완료 되었습니다.");
        location.href = `/board/${data.boardId}`;
      });
  },

  replyDelete: function (boardId, replyId) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
      type: "DELETE",
      url: `/api/board/${boardId}/reply/${replyId}`, // `는 작은 따옴표(')가 아니고 백틱(`)임 변수를 글자안에 넣기 위함.
      beforeSend: function (xhr) {
        /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        xhr.setRequestHeader(header, token);
      },
    })
      .done(function (resp) {
        alert("댓글삭제가 성공 하였습니다.");
        location.href = `/board/${boardId}`;
      })
      .fail(function (error) {
        alert("댓글삭제가 실패 하였습니다.");
        //alert(error.responseText);
      });
  },
};

index.init();
