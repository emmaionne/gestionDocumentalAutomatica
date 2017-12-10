package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Resolucion;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.JsfUtil.PersistAction;
import co.unicauca.proyectobase.dao.ResolucionFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("resolucionController")
@SessionScoped
public class ResolucionController implements Serializable {

    @EJB
    private co.unicauca.proyectobase.dao.ResolucionFacade ejbFacade;
    private List<Resolucion> items = null;
    private Resolucion selected;

    public ResolucionController() {
    }

    public Resolucion getSelected() {
        return selected;
    }

    public void setSelected(Resolucion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ResolucionFacade getFacade() {
        return ejbFacade;
    }

    public Resolucion prepareCreate() {
        selected = new Resolucion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleResolucion").getString("ResolucionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleResolucion").getString("ResolucionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleResolucion").getString("ResolucionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Resolucion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleResolucion").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleResolucion").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Resolucion getResolucion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Resolucion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Resolucion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Resolucion.class)
    public static class ResolucionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResolucionController controller = (ResolucionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resolucionController");
            return controller.getResolucion(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Resolucion) {
                Resolucion o = (Resolucion) object;
                return getStringKey(o.getPubIdentificador());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Resolucion.class.getName()});
                return null;
            }
        }

    }

}
