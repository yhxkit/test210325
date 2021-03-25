
function checkEmptyInput(str){
    if($.trim(str) == '' || str == 'undefined' || str == 'null'){
        return false
    }else{
        return true
    }
}

function submitServicer(){

    $('#errorarea').text('');

    let svcName = $.trim($('#serviceName').val());
    let cb =  $.trim($('#callback').val());

    if(checkEmptyInput(svcName) &&  checkEmptyInput(cb)) {
        let checkcb = cb.replace(/-/gi, "");
        // console.log("체크")
        // console.log(checkcb);
        // console.log(isNaN(checkcb));
        if(isNaN(checkcb)){
            alert("전화번호를 입력하세요 (000-0000-0000 / 00000000000)");
        }else{
            let simpleServicer = {"servicerName": svcName, "callback": cb};
            $.ajax({
                type: 'post',
                url: serviceurl,
                data: JSON.stringify(simpleServicer),
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    console.log(JSON.stringify(data));
                    alert(data.message);
                    window.location.reload();

                },
                error: function (data) {
                    alert(JSON.stringify(data));
                }
            });

        }

    }else{
        $('#errorarea').text("서비스명과 전화번호를 모두 채우세요");
    }



}

function deleteSelectedServicer(){

    let servicerList = new Array();

    $("input[name=check-inside]:checked").each(function(){

        let name = $(this).parents('.item-row').find('span[class=serviceName]').text();
        let cb = $(this).parents('.item-row').find('span[class=callback]').text();

        let newServicer = {"servicerName" : name, "callback" : cb};
        // console.log(newServicer);
        servicerList.push(newServicer);
    });
    // console.log(JSON.stringify(servicerList));

    if(servicerList.length<1){
        alert("삭제할 할목을 선택하세요");
    }else {
        $.ajax({
            type: 'delete',
            url: serviceurl,
            data: JSON.stringify(servicerList),
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                alert(data.message);
                window.location.reload();
            },
            error: function (data) {
                console.log(JSON.stringify(data));
            }
        });

    }


}
