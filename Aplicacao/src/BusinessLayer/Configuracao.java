/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Configuracao {
    
    private int idConfig;
    private String nifCliente;
    private Map<Integer,Componente> listaComponentes;
    private Map<Integer,Pacote> listaPacks;

    // Construtores (por omissão, parametrizado, de cópia)
    
    public Configuracao() {
        this.idConfig = 0;
        this.nifCliente = "";
        this.listaComponentes = new HashMap<>();;
        this.listaPacks = new HashMap<>();
    }
    
    public Configuracao(int idConfig, String nifCliente, Map<Integer, Componente> listaComponentes, Map<Integer, Pacote> listaPacks) {
        this.idConfig = idConfig;
        this.nifCliente = nifCliente;
        this.listaComponentes = listaComponentes;
        this.listaPacks = listaPacks;
    }
    
    public Configuracao(Configuracao umaConfiguracao) {
        this.idConfig = umaConfiguracao.getIdConfig();
        this.nifCliente = umaConfiguracao.getNifCliente();
        this.listaComponentes = umaConfiguracao.getListaComponentes();
        this.listaPacks = umaConfiguracao.getListaPacks();
    }
    
    // Getters

    public int getIdConfig() {
        return idConfig;
    }

    public String getNifCliente() {
        return nifCliente;
    }

    public Map<Integer, Componente> getListaComponentes() {
        return this.listaComponentes.values().stream().collect(Collectors.toMap((comp) -> comp.getIdComponente(),(comp) -> comp.clone()));
    }

    public Map<Integer, Pacote> getListaPacks() {
        return this.listaPacks.values().stream().collect(Collectors.toMap((pack) -> pack.getIdPack(),(pack) -> pack.clone()));
    }
    
    // Setters

    public void setIdConfig(int idConfig) {
        this.idConfig = idConfig;
    }

    public void setCliente(String nifCliente) {
        this.nifCliente = nifCliente;
    }

    public void setListaComponentes(Map<Integer, Componente> listaComponentes) {
        this.listaComponentes = listaComponentes;
    }

    public void setListaPacks(Map<Integer, Pacote> listaPacks) {
        this.listaPacks = listaPacks;
    }
    
    public void addComponente(Componente c) {
        
        this.listaComponentes.put(c.getIdComponente(), c);
        
    }
    
    public void removeComponente(int id) {
        
        this.listaComponentes.remove(id);
        
    }
    
    public void addPacote(Pacote p) {
        
        this.listaPacks.put(p.getIdPack(), p);
        
    }
    
    public Double precoTotalConfiguracao (Configuracao config) {
        
        Double pt=0.0;
        try {
            for(Pacote p: this.listaPacks.values())
            pt += p.getPrecoPack();
        
        for(Componente c: this.listaComponentes.values())
            pt += c.getPrecoComp();
        }
        catch (NullPointerException e)
        {
            StringBuilder sb = new StringBuilder("Componentes inexistentes!");
            
        }
        
        return pt;

    }
    
    public Object clone() 
    {
        return new Configuracao(this);
    }
    
}
