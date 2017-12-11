package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Coordinador;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos coordinador
 * @author Sahydo
 */
@Stateless
public class CoordinadorFacade extends AbstractFacade<Coordinador> {

    @PersistenceContext(unitName = "ProyectoIIMaestria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoordinadorFacade() {
        super(Coordinador.class);
    }
    
    
      public Coordinador buscarCoordinador(String userName) {
        javax.persistence.Query query = getEntityManager().createNamedQuery("Coordinador.findByCooUsuario");
        query.setParameter("cooUsuario", userName);
        List<Coordinador> lista = null;
        try {            
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());            
        }
        return lista.get(0);
    }
    
   
    
}
