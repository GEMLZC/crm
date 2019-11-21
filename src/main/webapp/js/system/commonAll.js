$(function () {


})

function message(data) {
    if (data.success){
        $.messager.alert('温馨提示',"操作成功2s后自动刷新");
        setTimeout(function () {
            location.reload();
        },2000)
    }else {
        $.messager.alert('温馨提示',data.message);
    }
}

