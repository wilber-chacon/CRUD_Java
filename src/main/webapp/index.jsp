<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inicio</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body style="background-color: #dddcdc;">
<div class="container shadow bg-white rounded mt-5 pt-5">
    <h1 class="text-success font-weight-bold text-center mt-5"><%= "¡Bienvenido!" %>
    </h1>
    <br/>
    <div class="d-flex justify-content-center mt-5 pt-5 pb-5">
        <form id="index-form" action="Servlet_Controller" method="post">
            <input type="hidden" name="op" value="registros">
            <input type="submit" name="continuar" class="btn btn-primary btn-lg" value="Continuar">
        </form>
    </div>
</div>
</body>
</html>