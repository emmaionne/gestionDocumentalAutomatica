package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Resolucion;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.JsfUtil.PersistAction;
import co.unicauca.proyectobase.dao.PalabraClaveFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.dao.ResolucionFacade;
import co.unicauca.proyectobase.entidades.Coordinador;
import co.unicauca.proyectobase.entidades.PalabraClave;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.utilidades.Palabra;
import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.UploadedFile;

@Named("resolucionController")
@SessionScoped
public class ResolucionController implements Serializable {

    @EJB
    private co.unicauca.proyectobase.dao.ResolucionFacade ejbFacade;
    @EJB
    private co.unicauca.proyectobase.dao.CoordinadorFacade ejbFacadeCoordinador;
    @EJB
    private PalabraClaveFacade ejbFacadePalabra;
    @EJB
    private PublicacionFacade ejbPublicacion;
     
    private List<Resolucion> items = null;
    
    private UploadedFile documento;
    private Coordinador coordinador;
    private Publicacion pub;
    
    private Resolucion selected;
    List<Palabra> listaPalabras = new ArrayList<>();
    private PalabraClave keyword;
    private CargarVistaCoordinador cvc = new CargarVistaCoordinador();

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

    //<editor-fold defaultstate="collapsed" desc="setter´s and getter´s">
    public List<Palabra> getListaPalabras() {
        return listaPalabras;
    }

    public void setListaPalabras(List<Palabra> listaPalabras) {
        this.listaPalabras = listaPalabras;
    }
    
    public PalabraClave getKeyword() {
        return keyword;
    }

    public void setKeyword(PalabraClave keyword) {
        this.keyword = keyword;
    }
    
     public UploadedFile getDocumento() {
        return documento;
    }

    public void setDocumento(UploadedFile documento) {
        this.documento = documento;
    }
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="codigo nuevo">
    
    /**
     * Redireccion registrar resolucion coordinador
     */
    public void redirigirARegistrarCoorResolucion() {
        this.prepareCreate(); // Inicializar el Objeto
        listaPalabras.clear();
        keyword = new PalabraClave();
        cvc.registrarDocumentoResolucion();
        Utilidades.redireccionar(cvc.getRuta());
    }
    
    /**
     * 
     */
    public void agregarPalabra(){
        System.out.print("adicionando palabra "+keyword.getPalClapalabra());
        if(!keyword.getPalClapalabra().equals("")){
            if(!listaPalabras.contains(new Palabra(keyword.getPalClapalabra())))
            {
                listaPalabras.add(new Palabra(keyword.getPalClapalabra()));
                System.out.println("Palabra adicionada" + keyword.getPalClapalabra());
            }                        
        }
        else{
            //FacesContext.getCurrentInstance().addMessage("msjValAutores", new FacesMessage(FacesMessage.SEVERITY_ERROR, " not a text file", ""));
            System.out.println("palabra repetida");            
        }
        keyword.setPalClapalabra("");
    }
    public void eliminarPalabra(String word){        
        System.out.print("eliminar palabra: " + word);        
        for (int i = 0; i < listaPalabras.size(); i++) {
            if(listaPalabras.get(i).getWord().equals(word)){
                listaPalabras.remove(i);
                break;
            }
        }
        System.out.println("  tamaño: " + listaPalabras.size());        
        mostrarLista();
    }
    public void mostrarLista(){
        System.out.println("mostrando lista....");
        for (Palabra lis : listaPalabras) {
            System.out.print(lis.getWord()+ "     ");
        }
    }   
    
    public void redirigirAlistar() {                
        cvc.listarEstudiantes();
        Utilidades.redireccionar(cvc.getRuta());
    }        
    
    public void AgregarResolucion(String userName) throws IOException {
        System.out.println("Registrando resolucion");
        // Formato Valido
        boolean formatoValido = true;
        /*if (!documento.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(documento.getContentType())) {

            FacesContext.getCurrentInstance().addMessage("valPublicacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se debe subir el documento en formato pdf", ""));
            formatoValido = false;
        }*/
        if (formatoValido) {
            boolean puedeSubir = false;

            if (documento.getFileName().equalsIgnoreCase("")) {
                FacesContext.getCurrentInstance().addMessage("Documento Acta", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe subir el archivo", ""));
            } else {
                puedeSubir = true;
            }

            if (puedeSubir) {
                if (!listaPalabras.isEmpty()) {
                    System.out.println("Agregando resolucion");
                    try {
                        Coordinador coor = buscarCoordinador(userName);
                        pub.setPubCooIdentificador(coor); //Identificar el coordinador
                        int numPubRevis = ejbPublicacion.getnumFilasPubRev();
                        pub.setPubIdentificador(numPubRevis);

                        pub.setPubTipoPublicacion("Resolución");
                        selected.setPubIdentificador(numPubRevis);
                        try {
                            pub.AgregarActa(documento);
                        } catch (IOException ex) {
                            Logger.getLogger(ActaController.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("error Agregando acta");
                        }
                        //almacenar el objeto en la base de datos
                        pub.setPubFechaPublicacion(new Date());
                        ejbPublicacion.create(pub);
                        ejbPublicacion.flush();
                        ejbFacade.create(this.selected);

                        //agregar la palabra clave a la tabla palabra
                        int numPal = ejbFacadePalabra.getnumFilas();
                        keyword.setPubIdentificador(pub);
                        for (int i = 0; i < listaPalabras.size(); i++) {
                            keyword.setPalClaidentificador(numPal + i);
                            keyword.setPalClapalabra(listaPalabras.get(i).getWord());
                            ejbFacadePalabra.create(keyword);
                        }

                        mensajeconfirmarRegistro();
                        redirigirAlistar();
                    } catch (EJBException ex) {
                        System.out.println("Error: No se pudo registrar la resolucion error: " + ex.getMessage());
                    }
                } else {                    
                    //ejbFacadePalabra.create(keyword);
                }
            }

        }

    }
    
    public void mensajeconfirmarRegistro() {
        System.out.println("Registrada con exito");
    }
    
    private Coordinador buscarCoordinador(String userName) {
        return ejbFacadeCoordinador.buscarCoordinador(userName);
    }
        
    //</editor-fold>
    
    
    
    
}
