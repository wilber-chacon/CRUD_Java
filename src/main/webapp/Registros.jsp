<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Registros</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js" ></script>
</head>
<body style="background-color: #dddcdc;">
<section class='container-sm m-3 p-5 rounded shadow' style="background-color: white; height: auto;">

    <h1 class='text-center text-dark pt-5'>LISTA DE REGISTROS</h1>
    <br/>
    <div>
        <a href="${context}/Servlet_Controller?op=nuevoRegistro" style='cursor: pointer; float: left; margin-left: 30px;' id="btnRegistrar" class='btn btn-primary text-white'>Agregar registro</a>
    </div>
    <br/>
    <br/>
    <br/>
<table BORDER class='table'>
    <thead class='text-white bg-info'>
    <tr>
        <th scope='col'>ID</th>
        <th scope='col'>Nombre</th>
        <th scope='col'>Edad</th>
        <th scope='col'>Genero</th>
        <th scope='col'>Ocupación</th>
        <th scope='col'>Fecha de nacimiento</th>
        <th scope='col'class="text-center" STYLE="width: 230px;">Operaciones</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty requestScope.listaRegistros}">
        <c:forEach var="reg" items="${requestScope.listaRegistros}">
            <tr>
                <td>${reg.getId_persona()}</td>
                <td>${reg.getNombre_persona()}</td>
                <td>${reg.getEdad_persona()}</td>
                <td>${reg.getSexo_persona()}</td>
                <td>${reg.getOcupacion().getOcupacion()}</td>
                <td>${reg.getFecha_nac()}</td>
                <td class="text-center">
                    <a class="btn btn-warning" href="${context}/Controller?op=obtenerRegistro&id=${reg.getId_persona()}">Editar</a>
                    <button class="btn btn-danger" onclick="confirmacionEliminar('${reg.getId_persona()}')">Eliminar</button>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</section>
</body>
</html>
