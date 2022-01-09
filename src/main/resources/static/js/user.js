let index={
    init: function() {
        $("#btn-save").on("click", () =>
            this.save()
        ); // btn-save 버튼을 click하면 save 함수 호출
    },

    save: function() {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        // console.log(data);

        $.ajax({
            type: "POST",
            url: "/auth/join_proc",
            data: JSON.stringify(data), // http body data
            contentType: "application/json; charset=utf-8", // body data type
            dataType: "json" // response type / 기본은 String으로 오나 생긴게 json이라면 js type로 변환
        }).done(function(resp){
            alert("Sign Up complete!");
            location.href = "/";
            console.log(resp); // 1 return
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통하여 3개의 데이터를 json으로 변경하여 insert 요청
    }

    
}

index.init();