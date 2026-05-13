$(function() {
    $.ajaxSetup({
        error:function(xhr) {
            if(xhr.responseJSON && xhr.responseJSON.message) {
                console.log(xhr.responseJSON.message);
            } else if(xhr.status == 400) {
                console.log("잘못된 형식의 요청입니다.");
            }
        }
    })
})