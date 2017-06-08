/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.model.Area;
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
@Path("com.model.titulo")
public class TituloFacadeREST extends AbstractFacade<Titulo> {
    @EJB
    AreaFacadeREST areaFacadeREST;
    @PersistenceContext(unitName = "Refuerzo_BDDPU")
    private EntityManager em;

    public TituloFacadeREST() {
        super(Titulo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Titulo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Titulo entity) {
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
    public Titulo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Titulo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Titulo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
 //--------------------------------CREAR-----------------------------------------------
    @POST
    @Path("crear")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String crear(@FormParam("nivel") String nivel,@FormParam("nombre") String nombre,@FormParam("universidad")String universidad,@FormParam ("idarea") int idArea) {
        String mensaje="{\"exitoso\":false}";
        Area a= areaFacadeREST.find(idArea);
        try{
            if (obtener_usuario(nombre)== null){
                create(new Titulo(nivel, nombre,universidad, new Date(), false,a));
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
    public List<Titulo> consulta(@FormParam("nombre")String nombre) {
         List<Titulo> retorno=obtenerid(nombre);
         return retorno;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    ///------------------------METODOS PARA REALIZAR LOS POST-------------------
//---------------------METODO PARA BUSCAR  SI EL USUARIO EXISTE Y PODER CREARLO----------------
        public Titulo obtener_usuario(String nombre){
        Titulo t=null;
        TypedQuery<Titulo> qry;
        qry = getEntityManager().createQuery("SELECT t FROM Titulo t WHERE t.nombre = :nombre and t.eliminado = :eliminado", Titulo.class);
        qry.setParameter("nombre",nombre);
        qry.setParameter("eliminado",false);
         try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    //-----------------METODO PARA LEER------------------------------------------------------
        List<Titulo> obtenerid(String valor) {
        TypedQuery<Titulo> qry;
        qry = getEntityManager().createQuery("SELECT t FROM Titulo t WHERE t.nombre = :nombre", Titulo.class);
        qry.setParameter("nombre", valor);
        try {
            return qry.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }     
    
}
