<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Registros</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/sweetalert2/sweetalert2.css">
    <link rel="stylesheet" href="resources/datatables/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="resources/datatables/select.dataTables.min.css">
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
<table class="table row-bordered table table-striped table-hover dataTable" id="dataTable" width="100%" cellspacing="0" style="white-space: nowrap; overflow-x: auto;">
    <thead class='text-white bg-info'>
    <tr>
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
                <td>${reg.getNombre_persona()}</td>
                <td>${reg.getEdad_persona()}</td>
                <td>${reg.getSexo_persona()}</td>
                <td>${reg.getOcupacion().getOcupacion()}</td>
                <td>${reg.getFecha_nac()}</td>
                <td class="text-center">
                    <a class="btn btn-warning" href="${context}/Servlet_Controller?op=obReg&id=${reg.getId_persona()}">Editar</a>
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#EliminarModal" onclick="confirmacionEliminar('${reg.getId_persona()}')">
                        Eliminar
                    </button>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>

    <div class="modal fade" id="EliminarModal" tabindex="-1" aria-labelledby="eliminarModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="eliminarModalLabel">Eliminar</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    ¿Seguro que desea eliminar el registro?
                </div>
                <div class="modal-footer">
                    <form enctype="multipart/form-data" id="form-del" name="form-del" action="${context}/Servlet_Controller" method="post">
                        <input type="hidden" name="op" value="eliminar" />
                        <input type="hidden" name="id" id="idRegistro" value="0" />
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <input type="submit" id="btn-ok" name="submit" class="btn btn-success" form="form-del" value="Aceptar">
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
<script src="resources/datatables/datatables.min.js"></script>
<script src="resources/datatables/dataTables.bootstrap4.min.js"></script>
<script src="resources/datatables/dataTables.select.min.js"></script>
<script src="resources/datatables/datatables-ini.js"></script>
<script src="resources/sweetalert2/sweetalert2.js"></script>
<script src="resources/js/action.js"/></script>
<script>
    <c:if test = "${not empty ok and ok != false}">
    sweetAlert("¡Exito!", "<c:out value="${mensaje}"></c:out>", "success");
    <c:set var="ok" value="false" scope="session"/>
    </c:if>

    <c:if test = "${not empty noCompletado and noCompletado != false}">
    sweetAlert("¡Error!", "<c:out value="${mensaje}"></c:out>", "error");
    <c:set var="noCompletado" value="false" scope="session"/>
    </c:if>
</script>
</body>
</html>
