$(document).ready(function() {
    function showDescription(data) {
        $('#showDescription').text('');
        $('#showDescription').append(data);
        $('#header-description').hide();
    }

    function addToCartSuccess() {
        swal({
            title: "Cart!",
            text: "Add item to cart successfully!",
            icon: "success",
            button: "Close!",
        });
    }
    function addToCartFail() {
        swal({
            title: "Cart!",
            text: "This item is temporarily out of stock!",
            icon: "error",
            button: "Close!",
        });
    }

    function addFavoriteSuccess() {
        swal({
            title: "Favorite!",
            text: "Added to favorites",
            icon: "success",
            button: "Close!",
        });
    }
    function removeFavoriteSuccess() {
        swal({
            title: "Favorite!",
            text: "removed to favorites",
            icon: "success",
            button: "Close!",
        });
    }
});