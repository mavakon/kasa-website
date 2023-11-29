<!DOCTYPE html>
<html>
<head>
    <title>Kasa</title>
</head>
<body>
<h3>Products:</h3>
    <#list products as product>
        <div>
            <p><b>${product.name}</b> ${product.price} грн.</p>
        </div>
    </#list>

</body>
</html>