/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.util.ArrayList;
import java.util.List;


public class Componente {

    private int idComponente;
    private String designacaoComp;
    private Double precoComp;
    private int stock;
    private String categoria;
    private ArrayList<Integer> listaIdCompIncompativeis;
    private ArrayList<Integer> listaIdCompObrigatorios;
    
    //Construtores (por omissão, parametrizado, de cópia)
   
    public Componente() {
        this.idComponente=0;
        this.designacaoComp="";
        this.precoComp=0.0;
        this.stock=0;
        this.categoria="";
        this.listaIdCompIncompativeis = new ArrayList <Integer>();
        this.listaIdCompObrigatorios = new ArrayList <Integer>();     
    }

    public Componente(int idComponente, String designacaoComp, double precoComp, int stock, String categoria, ArrayList<Integer> listaIdCompIncompativeis, ArrayList<Integer> listaIdCompObrigatorios) {
        this.idComponente = idComponente;
        this.designacaoComp = designacaoComp;
        this.precoComp = precoComp;
        this.stock = stock;
        this.categoria = categoria;
        this.listaIdCompIncompativeis = listaIdCompIncompativeis;
        this.listaIdCompObrigatorios = listaIdCompObrigatorios;
    }

    public Componente(Componente umComponente) {
        this.idComponente=umComponente.getIdComponente();
        this.designacaoComp=umComponente.getDesignacaoComp();
        this.precoComp=umComponente.getPrecoComp();
        this.stock=umComponente.getStock();
        this.categoria=umComponente.getCategoria();
        this.listaIdCompIncompativeis = umComponente.getListaIdCompIncompativeis();
        this.listaIdCompObrigatorios = umComponente.getListaIdCompObrigatorios();    
    }

    // Getters

    public int getIdComponente() {
        return idComponente;
    }

    public String getDesignacaoComp() {
        return designacaoComp;
    }

    public Double getPrecoComp() {
        return precoComp;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public ArrayList<Integer> getListaIdCompIncompativeis() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        
        for(int comp: listaIdCompIncompativeis)
        {
            lista.add(comp);
        }
        return lista;
    }

    public ArrayList<Integer> getListaIdCompObrigatorios() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        
        for(Integer comp: listaIdCompObrigatorios)
        {
            lista.add(comp);
        }
        return lista;
    }
    
    // Setters

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public void setDesignacaoComp(String designacaoComp) {
        this.designacaoComp = designacaoComp;
    }

    public void setPrecoComp(Double precoComp) {
        this.precoComp = precoComp;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setListaIdCompIncompativeis(ArrayList<Integer> novaListaIdCompIncompativeis) {
        this.listaIdCompIncompativeis = novaListaIdCompIncompativeis;
    }

    public void setListaIdCompObrigatorios(ArrayList<Integer> novaListaIdCompObrigatorios) {
        this.listaIdCompObrigatorios = novaListaIdCompObrigatorios;
    }
    
   public Componente clone() 
   {
       return new Componente(this); 
   } 
   
}
