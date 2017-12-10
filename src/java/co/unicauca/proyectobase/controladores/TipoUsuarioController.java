package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.TipoUsuario;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.PaginationHelper;
import co.unicauca.proyectobase.dao.TipoUsuarioFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 * Controlador donde se definene las funciones para el manejo del
 * tipo de usuario
 * @author Santiago
 */
@Named("tipoUsuarioController")
@SessionScoped
public class TipoUsuarioController implements Serializable {

    //Almacenar el tipo de usuario actual
    private TipoUsuario current;
    
    //Para hacer operaciones sobre la tabla TipoUsuario
    @EJB
    private co.unicauca.proyectobase.dao.TipoUsuarioFacade ejbFacade;
    //Atributos utilizados por los metodos creados por el framework
    private DataModel items = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoUsuarioController() {
    }

    public TipoUsuario getSelected() {
        if (current == null) {
            current = new TipoUsuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoUsuarioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    /* funcion que crea un objeto de lista de usuarios para ser usados en vista
    
    */
    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    /* funcion que crea un objeto de tipo "view" que se usa para preparar la vista
    que se va a mostrar    
    */
    public String prepareView() {
        current = (TipoUsuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    /**
     * Metodo que inicializa un nuevo objeto usuario listo para ser utilizado
     * en la creacion de un usuario
     * @return 
     */
    public String prepareCreate() {
        current = new TipoUsuario();
        selectedItemIndex = -1;
        return "Create";
    }
    
    /**
     * Funcino que crea un registro en la tabla usuario con los datos guardados
     * en el objeto current
     * @return "Create" si se creo el registro, de lo contrario null
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("TipoUsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    /* funcion que crea un objeto de usuario, cos sus items por separado, para
    ser usados en vista de edicion    
    */
    public String prepareEdit() {
        current = (TipoUsuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
     
    /* funcion que crea un objeto de vista que actualiza la lista de usuarios
    y se muestran en la pantalla
    */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("TipoUsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoUsuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("TipoUsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public TipoUsuario getTipoUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = TipoUsuario.class)
    public static class TipoUsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoUsuarioController controller = (TipoUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoUsuarioController");
            return controller.getTipoUsuario(getKey(value));
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
            if (object instanceof TipoUsuario) {
                TipoUsuario o = (TipoUsuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoUsuario.class.getName());
            }
        }

    }

}
