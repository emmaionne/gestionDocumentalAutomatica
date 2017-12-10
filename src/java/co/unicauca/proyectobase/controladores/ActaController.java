package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Acta;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.JsfUtil.PersistAction;
import co.unicauca.proyectobase.dao.ActaFacade;
import co.unicauca.proyectobase.dao.PalabraClaveFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Coordinador;
import co.unicauca.proyectobase.entidades.PalabraClave;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.utilidades.Palabra;
import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("actaController")
@SessionScoped
public class ActaController implements Serializable {

    @EJB
    private ActaFacade ejbFacade;
    @EJB
    private PublicacionFacade ejbPublicacion;
    @EJB
    private PalabraClaveFacade ejbPalabra;
    
    private List<Acta> items = null;
    private Acta actual;

    private UploadedFile documento;
    private Coordinador coordinador;
    private Publicacion pub;
    private PalabraClave keyword;
    private CargarVistaCoordinador cvc;
    
    List<Palabra> listaPalabras = new ArrayList<>();
    String nombre_doc;
//<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    
    public ActaController() {
        cvc = new CargarVistaCoordinador();
        pub = new Publicacion();
    }

    public Acta getSelected() {
        return actual;
    }

    public void setSelected(Acta actual) {
        this.actual = actual;
    }

    public UploadedFile getDocumento() {
        return documento;
    }

    public void setDocumento(UploadedFile documento) {
        this.documento = documento;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public PalabraClave getKeyword() {
        return keyword;
    }

    public void setKeyword(PalabraClave keyword) {
        this.keyword = keyword;
    }

    public List<Palabra> getListaPalabras() {
        return listaPalabras;
    }

    public void setListaPalabras(List<Palabra> listaPalabras) {
        this.listaPalabras = listaPalabras;
    }

    public Publicacion getPub() {
        return pub;
    }

    public void setPub(Publicacion pub) {
        this.pub = pub;
    }

    public String getNombre_doc() {
        return nombre_doc;
    }

    public void setNombre_doc(String nombre_doc) {
        this.nombre_doc = nombre_doc;
    }
//</editor-fold>
      
//<editor-fold defaultstate="collapsed" desc="Metodos Controlador">
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActaFacade getFacade() {
        return ejbFacade;
    }

    public Acta prepareCreate() {
        actual = new Acta();
        initializeEmbeddableKey();
        return actual;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleActa").getString("ActaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleActa").getString("ActaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleActa").getString("ActaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            actual = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Acta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (actual != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(actual);
                } else {
                    getFacade().remove(actual);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleActa").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleActa").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Acta getActa(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Acta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Acta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Acta.class)
    public static class ActaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActaController controller = (ActaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actaController");
            return controller.getActa(getKey(value));
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
            if (object instanceof Acta) {
                Acta o = (Acta) object;
                return getStringKey(o.getActIdentificador());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acta.class.getName()});
                return null;
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Nuevos Metodos">
    /**
     * Redireccion registrar acta coordinador
     */
    public void redirigirARegistrarCoor() {
        this.prepareCreate(); // Inicializar el Objeto
        listaPalabras.clear();
        keyword = new PalabraClave();
        cvc.registrarDocumento();
        Utilidades.redireccionar(cvc.getRuta());
    }
    
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
     * Agregar un acta a traves de un coordinador
     * @throws java.io.IOException
     */
    public void AgregarActa() throws IOException{
        System.out.println("Registrando Acta");
        // Formato Valido
        boolean formatoValido = true;
        /*if (!documento.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(documento.getContentType())) {

            FacesContext.getCurrentInstance().addMessage("valPublicacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se debe subir el documento en formato pdf", ""));
            formatoValido = false;
        }*/
        if (formatoValido == true) {
            boolean puedeSubir = false;
            
            if (documento.getFileName().equalsIgnoreCase("")) {
                FacesContext.getCurrentInstance().addMessage("Documento Acta", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe subir el archivo", ""));             
            }
            else 
                puedeSubir = true; 
            
            if(puedeSubir){
                System.out.println("Agregando Acta");
                Coordinador coor = getCoordinador(); // el acta esta relacionada con este coordinador
                try{
                    // pub.setPubCooIdentificador(coor); Identificar el coordinador
                    int numPubRevis = ejbPublicacion.getnumFilasPubRev();
                    pub.setPubIdentificador(numPubRevis);
                
                    pub.setPubTipoPublicacion("Acta");
                    actual.setActIdentificador(numPubRevis);
                    try{
                        pub.AgregarActa(documento);
                    }
                    catch (IOException ex) {
                        Logger.getLogger(ActaController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error Agregando acta");
                    }
                    //almacenar el objeto en la base de datos
                    pub.setPubFechaPublicacion(actual.getActFecha());
                    ejbPublicacion.create(pub);
                    ejbPublicacion.flush();                    
                    ejbFacade.create(this.actual);
                    int numPal = ejbPalabra.getnumFilas();
                    /* keyword.setIdActa(actual);
                    if(!listaPalabras.isEmpty()){
                        for(int i=0;i<listaPalabras.size();i++){
                            keyword.setIdPal(numPal+i);
                            keyword.setPalabra(listaPalabras.get(i).getWord());
                            daoPalabra.create(keyword);
                        }
                    }
                    else{
                        keyword.setIdPal(numPal);
                        daoPalabra.create(keyword);
                    }
                    */
                    mensajeconfirmarRegistro();
                    redirigirAlistar(); 
                }
                catch(EJBException ex)
                {
                    System.out.println("Error: No se pudo registrar el acta error: " + ex.getMessage());
                }
            }

        }

    }
    public void mensajeconfirmarRegistro() {
        System.out.println("Registrada con exito");
    }
    public void redirigirAlistar() 
    {                
        cvc.listarEstudiantes();
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
    
//</editor-fold>
}
