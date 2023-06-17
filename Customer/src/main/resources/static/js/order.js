function changeStatus(orderId, status) {
    $.ajax({
        url: `http://phoneshop.azurewebsites.net/shop/order/${orderId}?status=${status}`,
        type: 'POST',
        contentType: 'application/json',
        success: function (result) {
            swal({
                title: "Success!",
                text: "Cancel successfully order!",
                icon: "success",
                button: "Close!",
            }).then((value) => {
                window.location.href = "http://phoneshop.azurewebsites.net/shop/account/order-history";
            });

        },
        error: function (error) {
            swal({
                title: "Error!",
                text: "The server has been errors!",
                icon: "error",
            });
        }
    });
}


function orderSuccess() {
    swal({
        title: "Order!",
        text: "Order success!",
        icon: "success",
        button: "Close!",
    });
}

function orderFailed() {
    swal({
        title: "Order!",
        text: "Order failed!",
        icon: "error",
        button: "Close!",
    });
}
