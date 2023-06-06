$(document).ready(function() {
    $('table #btnUpdateStore').on('click', function(e) {
        e.preventDefault();
        let href = $(this).attr("href");

        $.get(href, function(store, status) {
            $('#formUpdate input[name=id][type=hidden]').remove();
            let inputId = `<input type="hidden" class="form-control" name="id" value="${store.id}">`;
            $('#formUpdate').append(inputId);
            $('#idEdit').val(store.id);
            $('#costEdit').val(store.costPrice);
            $('#saleEdit').val(store.salePrice);
            $('#quantityEdit').val(store.quantity);
        });
        $('#updateStore').modal('show');
    });

    function removeStore(storeId) {
        $.ajax({
            url: `http://localhost:8019/admin/stores/${storeId}`,
            type: 'DELETE',
            contentType: 'application/json',
            success: function (result) {
                swal({
                    title: "Success!",
                    text: "Delete success store!",
                    icon: "success",
                    button: "Close!",
                }).then((value) => {
                    window.location.href = "http://localhost:8019/admin/stores";
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
});