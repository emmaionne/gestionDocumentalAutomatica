/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.utilidades;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class SelectOneMenuView{
    private String formato;  
    private Map<String,String> formatos = new HashMap<String, String>();
    private Map<String,String> nombres = new HashMap<String, String>();
    private Map<String,String> dependencias = new HashMap<String, String>();
    @PostConstruct
    public void init() {         
        //Formatos
        formatos = new HashMap<String, String>();
        nombres = new HashMap<String, String>();
        dependencias = new HashMap<String, String>();
        formatos.put("PE-GE-2.2-FOR-6", "PE-GE-2.2-FOR-6");
        formatos.put("OP-FOR-006", "OP-FOR-006");
        nombres.put("Acta de Reuniones","Acta de Reuniones");
        nombres.put("Acta General para Actividades Universitarias","Acta General para Actividades Universitarias");
        dependencias.put("Departamento de Electrónica Instrumentación y Control","Departamento de Electrónica Instrumentación y Control");
        dependencias.put("Oficina de Posgrados - FIET","Oficina de Posgrados - FIET");
        
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Map<String, String> getFormatos() {
        return formatos;
    }

    public void setFormatos(Map<String, String> formatos) {
        this.formatos = formatos;
    }

    public Map<String, String> getNombres() {
        return nombres;
    }

    public Map<String, String> getDependencias() {
        return dependencias;
    }
    
    
}
