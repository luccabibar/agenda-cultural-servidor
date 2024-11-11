function makeRequest()
{
    let url = $("#input-url").val();
    let method = $("#input-method").val();
    let data = {
        'msg': $("#input-msg").val(),
    }

    console.log(url, method, data);

    $.ajax({
        'url': url,  
        'method': method,
        'data': data,
        'success': (result) => {
            $("#output-response").html(JSON.stringify(result));
        }
    });
}

window.onload = () => 
{
    $("#input-go").on("click", makeRequest);
}