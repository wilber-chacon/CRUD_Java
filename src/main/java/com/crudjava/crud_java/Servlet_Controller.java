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
    ArrayList<String> lstErrores = new ArrayList<String>();
    PersonaDAO personaDAO = new PersonaDAO();
    OcupacionesDAO ocupacionesDAO = new OcupacionesDAO();
    Persona persona;
    Ocupacion ocupacion;
    String context;


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session_actual= request.getSession(false);
        // Obteniendo el contextPath, se almacena en la variable "context"
        context = request.getContextPath();
        // Revisando que el parametro operacion realmente exista
        if (request.getParameter("op") == null) {
            request.getRequestDispatcher("404.jsp").forward(request, response);
        } else {

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

    private void listarRegistros(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listaRegistros", personaDAO.findAll());
            request.getRequestDispatcher("Registros.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void nuevoRegistro(HttpServletRequest request, HttpServletResponse response) {
        try {
            persona = new Persona();
            request.getSession().setAttribute("persona", persona);
            request.setAttribute("listaOcupaciones", ocupacionesDAO.listarOcupaciones());
            request.getRequestDispatcher("NuevoRegistro.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {

        lstErrores.clear();
        boolean ok = false;
        boolean noCompletado = false;
        try {
            persona = comprobarValidaciones(request, "insertar");

            if (lstErrores.size() > 0) {
                request.setAttribute("lstErrores", lstErrores);
                request.setAttribute("persona", persona);
                RequestDispatcher rd = request.getRequestDispatcher("/Servlet_Controller?op=nuevoRegistro");
                rd.forward(request, response);
            } else {
                persona.setEdad_persona(calcularEdad(persona.getFecha_nac()));
                if (personaDAO.insertarRegistro(persona) > 0) {
                    ok = true;
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "Se registró exitosamente.");
                } else {
                    noCompletado = true;
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "No se pudo registrar.");
                }
                response.sendRedirect(context + "/Servlet_Controller?op=registros");
            }
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void eliminarRegistro(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean ok = false;
            boolean noCompletado = false;
            int id = 0;
            Validaciones validaciones = new Validaciones();
            if(!validaciones.validaridPersona(request.getParameter("id"))) {
                noCompletado = true;
                request.getSession().setAttribute("noCompletado", noCompletado);
                request.getSession().setAttribute("mensaje", "El registro no existe en la base de datos.");
            }else {
                id = Integer.parseInt(request.getParameter("id"));
                if (personaDAO.eliminarRegistro(id) > 0) {
                    ok = true;
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "El registro se elimino exitosamente.");
                } else {
                    noCompletado = true;
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "El registro no se pudo eliminar.");
                }
            }

            response.sendRedirect(context + "/Servlet_Controller?op=registros");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void obtenerRegistro(HttpServletRequest request, HttpServletResponse response) {
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            persona = new Persona();
            persona = personaDAO.obtenerPersona(id);
            if (persona != null){
                request.getSession().setAttribute("persona", persona);
                request.setAttribute("listaOcupaciones", ocupacionesDAO.listarOcupaciones());
                request.getRequestDispatcher("Actualizar.jsp").forward(request, response);
            }else {
                request.getSession().setAttribute("noCompletado", true);
                request.getSession().setAttribute("mensaje", "El registro no se encuentra en la base de datos.");
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

    public void actualizarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {

        lstErrores.clear();
        boolean ok = false;
        boolean noCompletado = false;
        try {
            persona = comprobarValidaciones(request, "actualizar");

            if (lstErrores.size() > 0) {
                request.setAttribute("lstErrores", lstErrores);
                request.setAttribute("persona", persona);
                RequestDispatcher rd = request.getRequestDispatcher("/Servlet_Controller?op=obReg&id="+persona.getId_persona());
                rd.forward(request, response);
            } else {
                persona.setEdad_persona(calcularEdad(persona.getFecha_nac()));
                if (personaDAO.actualizarRegistro(persona) > 0) {
                    ok = true;
                    request.getSession().setAttribute("ok", ok);
                    request.getSession().setAttribute("mensaje", "El registro se actualizó correctamente.");
                } else {
                    noCompletado = true;
                    request.getSession().setAttribute("noCompletado", noCompletado);
                    request.getSession().setAttribute("mensaje", "El registro no se pudo actualizar.");
                }
                response.sendRedirect(context + "/Servlet_Controller?op=registros");
            }
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Persona comprobarValidaciones(HttpServletRequest request, String operacion){
        persona = new Persona();
        ocupacion = new Ocupacion();
        Validaciones validaciones = new Validaciones();

        if (operacion.equals("actualizar")){
            if(!validaciones.validaridPersona(request.getParameter("id"))) {
                lstErrores.add("La persona aun no ha sido registrada.");
            }else {
                persona.setId_persona(Integer.parseInt(request.getParameter("id")));
            }
        }
        if(!validaciones.validarNombre(request.getParameter("nombre"))) {
            lstErrores.add("Complete correctamente el campo nombre.");
        }else {
            persona.setNombre_persona(request.getParameter("nombre"));
        }
        if(!validaciones.validarGenero(request.getParameter("genero"))) {
            lstErrores.add("Complete correctamente el campo genero.");
        }else {
            persona.setSexo_persona(request.getParameter("genero"));
        }
        if(!validaciones.validarFecha(request.getParameter("fecha_nac"))) {
            lstErrores.add("Complete correctamente el campo fecha de nacimiento.");
        }else {
            persona.setFecha_nac(Date.valueOf(request.getParameter("fecha_nac")));
        }
        if(!validaciones.validarOcupacion(request.getParameter("ocupacion"))) {
            lstErrores.add("Complete correctamente el campo ocupacion.");
        }else {
            ocupacion.setId_ocupacion(Integer.parseInt(request.getParameter("ocupacion")));
            persona.setOcupacion(ocupacion);
        }
        return persona;
    }

    public int calcularEdad(Date fecha){
        int yearRegistro = fecha.toLocalDate().getYear();
        LocalDate fechaActual = LocalDate.now();
        int yearActual = fechaActual.getYear();
        int edad = yearActual - yearRegistro;
        return edad;
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
