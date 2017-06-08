/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.model.Docente;
import com.model.Materia;
import com.model.Titulo;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Valeria
 */
@Stateless
@Path("com.model.docente")
public class DocenteFacadeREST extends AbstractFacade<Docente> {
        //-----------SE INSTACIA LA CLASE---------------------------
    @EJB
    MateriaFacadeREST materiaFacadeREST;
    TituloFacadeREST tituloFacadeREST;

    @PersistenceContext(unitName = "Refuerzo_BDDPU")
    private EntityManager em;

    public DocenteFacadeREST() {
        super(Docente.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Docente entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Docente entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Docente find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Docente> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Docente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
        //--------------------------------CREAR-----------------------------------------------
    @POST
    @Path("crear")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String crear(@FormParam("nombre") String nombre, @FormParam("apellido") String apellido,@FormParam("cedula") String cedula,
            @FormParam("materia") int id_materia,@FormParam("titulo") int id_titulo) {
        String mensaje="{\"exitoso\":false}";
        Materia m= materiaFacadeREST.find(id_materia);
        Titulo t= tituloFacadeREST.find(id_titulo);
        try{
            if (obtener_usuario(nombre)== null){
                create(new Docente(nombre,apellido,cedula,false,new Date(),m,t));
                mensaje="{\"exitoso\":true}"; 
            }      
        }catch(Exception ex){           
        }
            
        return mensaje;
    }
    //---------------------------LEER--------------------------------------------
    @POST
    @Path("consulta")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Docente> consulta(@FormParam("nombre")String nombre) {
         List<Docente> retorno=obtenerid(nombre);
         return retorno;
    }
    //--------------------ACTUALIZAR-------------------------------------------
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam("id") int id,@FormParam("nombre") String nombre, @FormParam("apellido") String apellido,@FormParam("cedula") String cedula,
            @FormParam("materia") int id_materia,@FormParam("titulo") int id_titulo) {
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Docente u= buscarid(id);
        Materia m= materiaFacadeREST.find(id_materia);
        Titulo t= tituloFacadeREST.find(id_titulo);
        if(u!=null){
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCedula(cedula);
            u.setFechaCreacion(new Date());
            u.setEliminado(false);
            u.setIdMateria(m);
            u.setIdTitulo(t);
            try {
                edit(u);
                mensaje = "{\"exitoso\":true";
            } catch (Exception ex) {
                mensaje += "\"Execpcion en base\"";
            }
        }else{
            mensaje+="\"Datos no correctos\"";
        }
        mensaje+="}";   
        return mensaje;
    }
    //--------------------ELIMINAR---------------------------------------------
    @POST
    @Path("eliminar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String eliminar(@FormParam("id") int id){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Docente u= buscarid(id);
        if(u!=null){
            u.setEliminado(true);
            try {
                edit(id, u);
                mensaje = "{\"exitoso\":true";
            } catch (Exception e) {
                mensaje += "\"Execpcion en base\"";
            }
        }else{
            mensaje+="\"Datos no correctos\"";
        }
        mensaje+="}";   
        return mensaje;
    }
    //-------------------------------------------------------------------------

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //---------------------METODO PARA BUSCAR  SI EL USUARIO EXISTE Y PODER CREARLO----------------
        public Docente obtener_usuario(String nombre){
        Docente d=null;
        TypedQuery<Docente> qry;
        qry = getEntityManager().createQuery("SELECT d FROM Docente d WHERE d.nombre = :nombre and d.eliminado = :eliminado", Docente.class);
        qry.setParameter("nombre",nombre);
        qry.setParameter("eliminado",false);
         try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    //-----------------METODO PARA LEER------------------------------------------------------
        List<Docente> obtenerid(String valor) {
        TypedQuery<Docente> qry;
        qry = getEntityManager().createQuery("SELECT d FROM Docente d WHERE d.nombre = :nombre ", Docente.class);
        qry.setParameter("nombre", valor);
        try {
            return qry.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    } 
    //---------------METODO PARA BUSCAR EL ID DEL DOCENTE Y ASÍ PODER ACTUALIZAR-------------------------------
        public Docente buscarid(int pk_usuario){
        Docente u=null;
        TypedQuery<Docente> qry;
        qry = getEntityManager().createQuery("SELECT d FROM Docente d WHERE d.idDocente = :idDocente and d.eliminado = :eliminado", Docente.class);
        qry.setParameter("idocente",pk_usuario);
        qry.setParameter("eliminado",false);
        
         try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
