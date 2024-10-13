package com.crudjava.crud_java;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Model.OcupacionesDAO;
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

    public void insertarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        lstErrores.clear();
        boolean ok = false;
        boolean noCompletado = false;
        String expresionNombre = "^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$";

        try {
            persona = new Persona();
            ocupacion = new Ocupacion();

            persona.setNombre_persona(request.getParameter("nombre"));
            persona.setEdad_persona(Integer.parseInt(request.getParameter("edad")));
            persona.setSexo_persona(request.getParameter("genero"));
            persona.setFecha_nac(Date.valueOf(request.getParameter("fecha_nac")));
            ocupacion.setId_ocupacion(Integer.parseInt(request.getParameter("ocupacion")));
            persona.setOcupacion(ocupacion);

            if(persona.getNombre_persona().length()==0 || persona.getNombre_persona().equals("") || !persona.getNombre_persona().matches(expresionNombre)) {
                lstErrores.add("Complete correctamente el nombre.");
            }

            if (lstErrores.size() > 0) {
                request.setAttribute("lstErrores", lstErrores);
                request.setAttribute("persona", persona);
                RequestDispatcher rd = request.getRequestDispatcher("/Servlet_Controller?op=nuevoRegistro");
                rd.forward(request, response);
            } else {
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
            int id = Integer.parseInt(request.getParameter("id"));
            if (personaDAO.eliminarRegistro(id) > 0) {
                ok = true;
                request.getSession().setAttribute("ok", ok);
                request.getSession().setAttribute("mensaje", "El registro se elimino exitosamente.");
            } else {
                noCompletado = true;
                request.getSession().setAttribute("noCompletado", noCompletado);
                request.getSession().setAttribute("mensaje", "El registro no se pudo eliminar.");
            }
            response.sendRedirect(context + "/Servlet_Controller?op=registros");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
