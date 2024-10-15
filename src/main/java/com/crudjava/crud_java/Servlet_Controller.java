package com.crudjava.crud_java;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Model.OcupacionesDAO;
import com.crudjava.Utils.Validaciones;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.crudjava.Bean.Persona;
import com.crudjava.Model.PersonaDAO;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet(name = "Servlet_Controller", value = "/Servlet_Controller")
public class Servlet_Controller extends HttpServlet {
    //Lista que guarda los errores sobre los datos enviados del formulario
    ArrayList<String> lstErrores = new ArrayList<String>();
    PersonaDAO personaDAO = new PersonaDAO();//Instancia de la clase model para la tabla personas
    OcupacionesDAO ocupacionesDAO = new OcupacionesDAO();//Instancia de la clase model para la tabla ocupaciones
    Persona persona;
    Ocupacion ocupacion;
    String context;


    //Metodo utilizado para recibir las solicitudes de las vistas
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session_actual= request.getSession(false);
        // Obteniendo el contextPath, se almacena en la variable "context"
        context = request.getContextPath();
        // Revisando que el parametro operacion realmente exista
        if (request.getParameter("op") == null) {
            request.getRequestDispatcher("404.jsp").forward(request, response);
        } else {
            //Capturando y validando la operacion solicitada por las vistas
            String operacion = request.getParameter("op");
            if (operacion.equals("registros")) {
                listarRegistros(request, response);
            } else if (operacion.equals("nuevoRegistro")) {
                nuevoRegistro(request, response);
            } else if (operacion.equals("insertarRegistro")) {
                insertarRegistro(request, response);
            } else if (operacion.equals("eliminar")) {
                eliminarRegistro(request, response);
            } else if (operacion.equals("obReg")) {
                obtenerRegistro(request, response);
            } else if (operacion.equals("actualizarRegistro")) {
                actualizarRegistro(request, response);
            } else {
                request.getRequestDispatcher("404.jsp").forward(request, response);
            }

        }
    }

    //Metodo utilizado para preparar el listado de los registros y redireccionar a la vista de inicio
    private void listarRegistros(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Creando el atributo con la lista de los registros de la tabla personas
            request.setAttribute("listaRegistros", personaDAO.findAll());
            //redireccionando a la vista registros
            request.getRequestDispatcher("Registros.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Metodo utilizado para preparar el objeto persona y redireccionar a la vista para agregar un nuevo registro
    private void nuevoRegistro(HttpServletRequest request, HttpServletResponse response) {
        try {
            persona = new Persona(); //instancia de la clase persona
            //Creando el atributo con el objeto persona
            request.getSession().setAttribute("persona", persona);
            //Creando el atributo con la lista de los registros de la tabla ocupaciones
            request.setAttribute("listaOcupaciones", ocupacionesDAO.listarOcupaciones());
            //redireccionando a la vista para agregar nuevo registro
            request.getRequestDispatcher("NuevoRegistro.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Metodo utilizado para completar la insercion de un nuevo registro
    public void insertarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {

        lstErrores.clear();//limpiando la lista que almacena los errores enviados por el formulario
        //Variables booleanas que almacenan el estado de la operacion
        boolean ok = false; //operacion exitosa
        boolean noCompletado = false;//operacion fallida
        try {
            //llamada al metodo que valida los datos enviados por el formulario
            // y de estar correctos se guargan en el objeto persona
            persona = comprobarValidaciones(request, "insertar");

            //validando hay uno o mas errores en los datos
            if (lstErrores.size() > 0) {
                //Atributo con la lista de errores
                request.setAttribute("lstErrores", lstErrores);
                //atributo con el objeto persona
                request.setAttribute("persona", persona);
                //redireccionando a la vista para agregar nuevo registro
                RequestDispatcher rd = request.getRequestDispatcher("/Servlet_Controller?op=nuevoRegistro");
                rd.forward(request, response);
            } else {
                //si los datos estan correctos
                //llamada al metodo que calcula la edad de la persona a partir de la fecha de nacimiento
                persona.setEdad_persona(calcularEdad(persona.getFecha_nac()));

                //llamada al metodo de la clase model que completa la insercion del registro en la base de datos
                if (personaDAO.insertarRegistro(persona) > 0) {
                    //insercion exitosa
                    ok = true;
                    //atributos con mensajes de operacion exitosa
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "Se registró exitosamente.");
                } else {
                    //insercion fallida
                    noCompletado = true;
                    //atributos con mensajes de operacion fallida
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "No se pudo registrar.");
                }

                //redireccion a la vista que contiene todos los registros
                response.sendRedirect(context + "/Servlet_Controller?op=registros");
            }
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Metodo que atiende la operacion para eliminacion de un registro
    private void eliminarRegistro(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Variables booleanas que almacenan el estado de la operacion
            boolean ok = false; //operacion exitosa
            boolean noCompletado = false;//operacion fallida

            int id = 0;//almacena el identificador del registro a eliminar
            Validaciones validaciones = new Validaciones();//instancia de la clase validaciones
            //comprobacion para conocer si el identificador recibido es correcto
            if(!validaciones.validaridPersona(request.getParameter("id"))) {
                //la persona con el identificador recibido no existe en la base de datos
                noCompletado = true;
                request.getSession().setAttribute("noCompletado", noCompletado);
                request.getSession().setAttribute("mensaje", "El registro no existe en la base de datos.");
            }else {
                //la persona con el identificador recibido si existe en la base de datos
                //guardando el identificador
                id = Integer.parseInt(request.getParameter("id"));
                //llamada al metodo de la clase model que ejecuta la consulta de eliminacion
                if (personaDAO.eliminarRegistro(id) > 0) {
                    //eliminacion exitosa
                    ok = true;
                    //atributos con mensajes de operacion exitosa
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "El registro se elimino exitosamente.");
                } else {
                    //eliminacion fallida
                    noCompletado = true;
                    //atributos con mensajes de operacion fallida
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "El registro no se pudo eliminar.");
                }
            }
            //redireccion a la vista que contiene todos los registros
            response.sendRedirect(context + "/Servlet_Controller?op=registros");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Metodo que atiende la operacion para obtener un registro
    private void obtenerRegistro(HttpServletRequest request, HttpServletResponse response) {
        try{
            //Almacenado el identificador del registro a consultar
            int id = Integer.parseInt(request.getParameter("id"));
            persona = new Persona();//instancia de la clase persona
            //llamada al metodo para obtener el registro consultado y almacenarlo en el objeto persona
            persona = personaDAO.obtenerPersona(id);

            if (persona != null){//El registro mantiene existencia en la base de datos
                //creando atributo con el objeto persona
                request.getSession().setAttribute("persona", persona);
                //creando atributo con lista de ocupaciones
                request.setAttribute("listaOcupaciones", ocupacionesDAO.listarOcupaciones());
                //redireccionando a la vista para actualizar los datos del registro
                request.getRequestDispatcher("Actualizar.jsp").forward(request, response);
            }else {
                //El registro no existe en la base de datos
                //atributos con mensajes de operacion fallida
                request.getSession().setAttribute("noCompletado", true);
                request.getSession().setAttribute("mensaje", "El registro no se encuentra en la base de datos.");
                //redireccion a la vista que contiene todos los registros
                response.sendRedirect(context + "/Servlet_Controller?op=registros");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo que atiende la operacion para actualizar los datos de un registro
    public void actualizarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {

        lstErrores.clear();//limpiando la lista que almacena los errores enviados por el formulario
        //Variables booleanas que almacenan el estado de la operacion
        boolean ok = false; //operacion exitosa
        boolean noCompletado = false;//operacion fallida

        try {
            //llamada al metodo que valida los datos enviados por el formulario
            // y de estar correctos se guargan en el objeto persona
            persona = comprobarValidaciones(request, "actualizar");

            //validando hay uno o mas errores en los datos
            if (lstErrores.size() > 0) {
                //Atributo con la lista de errores
                request.setAttribute("lstErrores", lstErrores);
                //atributo con el objeto persona
                request.setAttribute("persona", persona);
                //redireccionando a la vista para actualizar el registro
                RequestDispatcher rd = request.getRequestDispatcher("/Servlet_Controller?op=obReg&id="+persona.getId_persona());
                rd.forward(request, response);
            } else {
                //si los datos estan correctos
                //llamada al metodo que calcula la edad de la persona a partir de la fecha de nacimiento
                persona.setEdad_persona(calcularEdad(persona.getFecha_nac()));
                //llamada al metodo de la clase model que completa la actualizacion del registro en la base de datos
                if (personaDAO.actualizarRegistro(persona) > 0) {
                    //actualizacion exitosa
                    ok = true;
                    //atributos con mensajes de operacion exitosa
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "El registro se actualizó correctamente.");
                } else {
                    //actualizacion fallida
                    noCompletado = true;
                    //atributos con mensajes de operacion fallida
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "El registro no se pudo actualizar.");
                }
                //redireccion a la vista que contiene todos los registros
                response.sendRedirect(context + "/Servlet_Controller?op=registros");
            }
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    //Metodo utilizado para validar que la informacion enviada a través del formulario este correcta,
    // recibe como parametros un objeto de tipo HttpServletRequest y un string para conocer la operacion a realizarse
    public Persona comprobarValidaciones(HttpServletRequest request, String operacion){
        persona = new Persona();//instancia de la clase persona
        ocupacion = new Ocupacion();//instancia de la clase ocupacion
        Validaciones validaciones = new Validaciones();//instancia de la clase validaciones

        //comprobacion para conocer si la operacion a realizarse es una actualizacion de registro, de ser asi,
        //se valida que el registro mantenga existencia en la base de datos
        if (operacion.equals("actualizar")){
            if(!validaciones.validaridPersona(request.getParameter("id"))) {//no contiene existencia en la base de datos
                //Agregando mensaje de error a la lista de errores
                lstErrores.add("La persona aun no ha sido registrada.");
            }else {
                //asignando el atributo identificador al objeto persona
                persona.setId_persona(Integer.parseInt(request.getParameter("id")));
            }
        }
        //validando la correcta escritura del campo nombre completo
        if(!validaciones.validarNombre(request.getParameter("nombre"))) {
            //Agregando mensaje de error a la lista de errores
            lstErrores.add("Complete correctamente el campo nombre.");
        }else {
            //asignando el atributo nombre al objeto persona
            persona.setNombre_persona(request.getParameter("nombre"));
        }
        //validando la correcta eleccion del campo genero
        if(!validaciones.validarGenero(request.getParameter("genero"))) {
            //Agregando mensaje de error a la lista de errores
            lstErrores.add("Complete correctamente el campo genero.");
        }else {
            //asignando el atributo genero al objeto persona
            persona.setSexo_persona(request.getParameter("genero"));
        }
        //validando la correcta eleccion del fecha de nacimiento
        if(!validaciones.validarFecha(request.getParameter("fecha_nac"))) {
            //Agregando mensaje de error a la lista de errores
            lstErrores.add("Complete correctamente el campo fecha de nacimiento.");
        }else {
            //asignando el atributo fecha de nacimiento al objeto persona
            persona.setFecha_nac(Date.valueOf(request.getParameter("fecha_nac")));
        }
        //validando la correcta eleccion del campo ocupacion
        if(!validaciones.validarOcupacion(request.getParameter("ocupacion"))) {
            //Agregando mensaje de error a la lista de errores
            lstErrores.add("Complete correctamente el campo ocupacion.");
        }else {
            //asignando el atributo ocupacion al objeto persona
            ocupacion.setId_ocupacion(Integer.parseInt(request.getParameter("ocupacion")));
            persona.setOcupacion(ocupacion);
        }
        //retornando el objeto persona
        return persona;
    }


    //Metodo utilizado para calcular la edad de una persona a partir de la fecha de nacimiento,
    //recibe como parametro un objeto date con la fecha
    public int calcularEdad(Date fecha){
        //Capturando el año de la fecha de nacimiento
        int yearRegistro = fecha.toLocalDate().getYear();
        //Almacenando la fecha actual
        LocalDate fechaActual = LocalDate.now();
        //guardando el año de la fecha actual
        int yearActual = fechaActual.getYear();
        //calculo de la edad
        int edad = yearActual - yearRegistro;
        return edad;//retornando la edad de la persona
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);

    }
}
