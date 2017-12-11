/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Coordinador;
import co.unicauca.proyectobase.entidades.Resolucion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Asus
 */
@Stateless
public class ResolucionFacade extends AbstractFacade<Resolucion> {

    @PersistenceContext(unitName = "ProyectoIIMaestria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResolucionFacade() {
        super(Resolucion.class);
    }
}
