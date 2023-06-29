<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #b4e1fa;
}

.container {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
    text-align: center;
    color: #333333;
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-size: 16px;
    color: #333333;
}

input[type="text"],
textarea,
input[type="number"],
input[type="file"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #dddddd;
    border-radius: 5px;
    font-size: 16px;
    background-color: #f9f9f9;
}

input[type="checkbox"] {
    margin-right: 5px;
}

.submit-button {
    display: block;
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #ff9e85;
    color: #ffffff;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
}



</style>

<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Add Product</h2>

        <form action="add-product" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productName">Product Name:</label>
                <input type="text" id="productName" name="productName" required>
            </div>

            <div class="form-group">
                <label for="productDescription">Product Description:</label>
                <textarea id="productDescription" name="productDescription"></textarea>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="petType">Pet Type:</label>
                <label><input type="checkbox" name="petType" value="dog">Dog</label>
                <label><input type="checkbox" name="petType" value="cat">Cat</label>
            </div>

            <div class="form-group">
                <label for="productOptions">Product Options:</label>
                <label><input type="checkbox" name="productOptions" value="food">Food</label>
                <label><input type="checkbox" name="productOptions" value="toy">Toy</label>
                <label><input type="checkbox" name="productOptions" value="bedding">Bedding</label>
                <label><input type="checkbox" name="productOptions" value="pads">Pads</label>
            </div>

            <div class="form-group">
                <label for="image">Image:</label>
                <input type="file" id="image" name="image" accept="image/*">
            </div>

            <input type="submit" value="Add Product" class="submit-button">
        </form>
    </div>
</body>
</html>
