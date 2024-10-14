
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">
<head>
    <title>Recurso no encontrado</title>
    <meta charset='UTF-8'>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container shadow bg-white rounded pt-5">

    <h1 class="font-weight-bold text-center mt-5" style="font-size: 15em;">404</h1>
    <p class="text-center text-secondary mt-2" style="font-size: 1.2em;"><%= "Recurso no encontrado." %></p>
    <br/>
    <div class="d-flex justify-content-center pt-2 pb-5">
        <form id="index-form" action="Servlet_Controller" method="post">
            <input type="hidden" name="op" value="registros">
            <input type="submit" name="continuar" class="btn btn-primary btn-lg" value="Ir a inicio">
        </form>
    </div>

</div>
</body>
</html>
