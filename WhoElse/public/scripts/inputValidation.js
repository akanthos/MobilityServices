function validateCreateItemForm() {
    var price = document.forms["createItem"]["price"].value;
    if (isNaN(price)) {
        alert("Price must be a number");
        return false;
    }
}