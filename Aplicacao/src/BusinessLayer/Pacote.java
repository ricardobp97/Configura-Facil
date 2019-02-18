/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Pacote {
    
    private int idPack;
    private String designacaoPack;
    private Map<Integer, Componente> listaCompPack;
    private Double precoPack;
    private String categoria;
    
    //Construtores (por omissão, parametrizado, de cópia)

    public Pacote() {
        this.idPack = 0;
        this.designacaoPack = "";
        this.listaCompPack = new HashMap<>();
        this.precoPack = 0.0;
        this.categoria = "";
    }
    
    public Pacote(int idPack, String designacaoPack, Map<Integer, Componente> listaCompPack, double precoPack, String categoria) {
        this.idPack = idPack;
        this.designacaoPack = designacaoPack;
        this.listaCompPack = listaCompPack;
        this.precoPack = precoPack;
        this.categoria = categoria;
        //calculaPrecoPack(this.listaCompPack);
    }

    public Pacote(Pacote umPacote) {
        this.idPack = umPacote.getIdPack();
        this.designacaoPack = umPacote.getDesignacaoPack();
        this.listaCompPack = umPacote.getListaCompPack();
        this.precoPack = umPacote.getPrecoPack();
        this.categoria = umPacote.getCategoria();
    }   
    
    // Getters

    public int getIdPack() {
        return idPack;
    }

    public String getDesignacaoPack() {
        return designacaoPack;
    }

    public Map<Integer, Componente> getListaCompPack() {
        return this.listaCompPack.values().stream().collect(Collectors.toMap((comp) -> comp.getIdComponente(),(comp) -> comp.clone()));
    }

    public Double getPrecoPack() {
        return precoPack;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    // Setters

    public void setIdPack(int idPack) {
        this.idPack = idPack;
    }

    public void setDesignacaoPack(String designacaoPack) {
        this.designacaoPack = designacaoPack;
    }

    public void setListaCompPack(HashMap<Integer, Componente> listaCompPack) {
        this.listaCompPack = listaCompPack;
    }

    public void setPrecoPack(Double precoPack) {
        
        this.precoPack = precoPack;
    }
    
    public void setCategoria(String categoria) {
        
        this.categoria = categoria;
    }
    
    public Pacote clone() 
    {
        return new Pacote(this); 
    } 
    
    public void calculaPrecoPack(Map<Integer, Componente> lista) {
        
        this.precoPack = 0.0;

        lista.values().forEach((Componente comp) -> { this.precoPack+=(comp.getPrecoComp()*0.8);});            
        
    }   
}
