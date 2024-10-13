<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html lang="es">
<head>
    <meta charset='UTF-8'>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Nuevo registro</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js" ></script>
</head>
<body>
<br/>
<br/>
<br/>
<section class='container-sm mr-3 ml-3 pr-3 pl-3 pb-5' style="background-color: white; height: auto;">
    <div class="form-control p-4" style="width: 50%; margin: 10px auto; box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2); background-color: #D2EBE4;">
        <h3 class="text-center text-success">Registrar persona</h3>
        <br/>
        <br/>
        <c:if test="${not empty requestScope.lstErrores}">
            <div class="alert alert-danger">
                <ul>
                    <c:forEach var="error" items="${requestScope.lstErrores}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>

            </div>
        </c:if>
        <form enctype="multipart/form-data" id="save-form" name="frmPersona" class="form" action="${context}/Servlet_Controller" method="post">
            <div class="form-group">
                <label for="nombre" class="text-success"><span class="text-danger">* </span>Nombre completo:</label><br>
                <input type="text" name="nombre" id="nombre"  class="form-control" pattern="^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$" value="${persona.nombre_persona}" required>
            </div>
            <div class="form-group">
                <label for="edad" class="text-success"><span class="text-danger">* </span>Edad:</label><br>
                <input type="number" id="edad" name="edad" min="18" max="100" class="form-control" value="${persona.edad_persona}" required />
            </div>
            <div class="form-group">
                <label for="genero" class="text-success"><span class="text-danger">* </span> Genero:</label>
                <div class="input-group">
                    <select name="genero" id="genero" value="${persona.sexo_persona}" class="form-control" required>
                        <option value="Masculino" selected>Masculino</option>
                        <option value="Femenino">Masculino</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="fecha_nac" class="text-success"><span class="text-danger">* </span>Fecha de nacimiento:</label><br>
                <input type="date" name="fecha_nac" id="fecha_nac" class="form-control" value="${persona.fecha_nac}" required>
            </div>
            <div class="form-group">
                <label for="ocupacion" class="text-success"><span class="text-danger">* </span> Ocupación:</label>
                <div class="input-group">
                    <select name="ocupacion" id="ocupacion" class="form-control" required>
                        <c:if test="${not empty requestScope.listaOcupaciones}">
                            <c:forEach var="ocp" items="${requestScope.listaOcupaciones}">
                                <option value="${ocp.getId_ocupacion()}"> ${ocp.getOcupacion()}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </div>
            <p><span class="text-danger">* </span>Los campos son requeridos.</p>
            <span STYLE="display: none;" class="text-danger" id="alertaCampos">Complete los campos requeridos.</span>
            <div class="form-group">
                <input type="hidden" name="op" value="insertarRegistro" />
                <input type="submit" id="btnEnviar" name="submit" class="btn btn-success" form="save-form" value="Enviar" style="width: 40%;">
                <a href="${context}/Servlet_Controller?op=registros" style='cursor: pointer; float: right; width: 40%;' class='btn btn-warning text-white'>Regresar</a>
            </div>
        </form>
    </div>
</section>
</body>
</html>