package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
import co.unicauca.proyectobase.entidades.Congreso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones del nombre de la ponencia. Esta clase
 * se usa para el registro de evento.
 * @author Juan
 */
@FacesValidator(value="validadorNombrePonencia")
public class ValidadorNombrePonencias implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es el nombre de la ponencia y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título de ponencia es obligatorio.");
            throw new ValidatorException(msg);
        }        
        
        if(nombre.length() < 10 || nombre.length() > 200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título de la ponencia debe contener entre 10 y 200 caracteres.");
            throw new ValidatorException(msg);
        } 
        
        if(!validadorNombrePonencia(nombre)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la ponencia es incorrecto.");
            throw new ValidatorException(msg);
        } 
        if(isRegistrado(nombre, context)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título de la ponencia ya se encuentra registrado");
            throw new ValidatorException(msg);
        }

    }
    
    //valida que el nombre de la revista no contenga caracteres especiales
    public boolean validadorNombrePonencia(String nomRevista) {
        Pattern p = Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s0-9]");
        Matcher m = p.matcher(nomRevista);
        return m.find();
    }
    
    
    /**
     * Funcion que determina si ya se encuentra en el sistema otro registro con
     * el mismo titulo de la ponencia. True si hay otro registro, de lo contrario
     * false.
     * @param ponencia
     * @param context
     * @return 
     */
    public boolean isRegistrado(String ponencia, FacesContext context){
        /*Buscar en la bd si esta registrado*/
        boolean variable = false;
        PublicacionController controller = (PublicacionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "publicacionController");
        Congreso congreso = controller.buscarPonenciaPorTitulo(ponencia);
        if (congreso != null && congreso.getPubIdentificador() != controller.getActual().getPubIdentificador()) {
            variable = true;
        }
        return variable;
    }                   
}
