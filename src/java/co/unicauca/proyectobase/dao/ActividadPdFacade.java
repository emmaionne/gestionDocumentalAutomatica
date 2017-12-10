package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.ActividadPd;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase para las operaciones sobre la tabla actividades de practica docentes
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Stateless
public class ActividadPdFacade extends AbstractFacade<ActividadPd> {

    @PersistenceContext(unitName = "ProyectoIIMaestria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadPdFacade() {
        super(ActividadPd.class);
    }

}
