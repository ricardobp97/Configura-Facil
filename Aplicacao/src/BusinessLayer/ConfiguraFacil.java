/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import DataLayer.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;


public class ConfiguraFacil extends Observable {
    
    private HashMap<String,Funcionario> listaFuncionarios;
    private Map<Integer,Componente> listaComponentes;
    private Map<Integer,Pacote> listaPacotes;
    private Map<Integer,Configuracao> listaConfiguracoes;
    private Configuracao config;
    private String[] categoriasComponentes = {"Motor", "Pintura", "Jantes", "Estofos", "Detalhes Interiores", "Detalhes Exteriores"};
    private String[] categoriasPacks = {"Base", "Opcional"};
    private ArrayList<Integer> listaCompConfig;
    private ArrayList<Integer> listaPackConfig;
    private ArrayList<Integer> listaCompConfigTemp;
    private ArrayList<Integer> listaPackConfigTemp;
    private ArrayList<Integer> configProduzir;
    private ArrayList<Integer> configProduzirTemp;
    
    private ArrayList<Integer> componentesRemover = new ArrayList <Integer>();
    private ArrayList<Integer> componentesInserir = new ArrayList <Integer>();
    private ArrayList<Integer> packsRemover = new ArrayList <Integer>();
    private ArrayList<Integer> packsInserir = new ArrayList <Integer>();
   
    private ArrayList<Integer> listaProducaoTemp = new ArrayList <Integer>();
   
    // Construtor
    public ConfiguraFacil() {
        
        this.listaFuncionarios = new HashMap<>();
        this.listaComponentes = new HashMap<>();
        this.listaPacotes = new HashMap<>();
        this.listaConfiguracoes = new HashMap<>();
        this.listaCompConfig = new ArrayList <Integer>();
        this.listaPackConfig = new ArrayList <Integer>();
        this.listaCompConfigTemp = new ArrayList <Integer>();
        this.listaPackConfigTemp = new ArrayList <Integer>();
        this.configProduzir = new ArrayList <Integer>();
        this.configProduzirTemp = new ArrayList <Integer>();
        
    }
        
    public int login(String username, String password) throws SQLException
    {    
        
       if(FuncionarioDAO.verificaLogin(username, password)) {
            String letra = username.substring(0, 1);
                if (letra.equals ("f"))
                     return 1;
                 else
                     return 2;
        }
        
        return 0;
    }
    
    //Inicia uma nova configuracao
    public void iniciaConfig() {  
        
        listaCompConfig.clear();
        listaPackConfig.clear();
        
    }    
    
    public void escolheComponentes(int idComponente) {
                
        componentesRemover.clear();
        componentesInserir.clear();
        packsRemover.clear();
        packsInserir.clear();
        listaCompConfigTemp.clear();
        listaCompConfigTemp.addAll(listaCompConfig);
        listaPackConfigTemp.clear();
        //Verificar componentes a remover (incompatíveis)
        devolveCompRemover(idComponente);
        //Verificar componentes a inserir (obrigatórias)
        devolveCompInserir(idComponente);
        
        for(int i:componentesInserir) {
            devolveCompRemover(i);
        }
        
        //Determina lista de componentes temp
        atualizaListaCompTemp(idComponente);        
        //Determina lista de packs temp
        atualizaListaPacksTemp();
        //Verifica packs a remover (com algum componente incompatível)
        devolvePacksRemover();
        //Verifica packs a inserir 
        devolvePacksAdicionar();
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    //Verificar componentes a remover (incompatíveis)
    public void devolveCompRemover(int idComponente) {
        
        Componente c = this.listaComponentes.get(idComponente);
        
        for(int idC: c.getListaIdCompIncompativeis())
            if(listaCompConfig.contains(idC))
                componentesRemover.add(idC);

    }
    
     //Verificar componentes a inserir (obrigatórias)
    public void devolveCompInserir(int idComponente) {
        
        Componente c = this.listaComponentes.get(idComponente);
       
        for(Integer idC: c.getListaIdCompObrigatorios())
            if(!listaCompConfig.contains(idC)) 
                componentesInserir.add(idC);
             
    }
    
    //Determina lista de componentes temp
    public void atualizaListaCompTemp(int novoComponente) {
        
        if(!listaCompConfigTemp.contains((Integer) novoComponente))
            listaCompConfigTemp.add(novoComponente);
        
        for(Integer idR: componentesRemover)
            if(listaCompConfigTemp.contains(idR))
                listaCompConfigTemp.remove(idR);
            
        for(Integer idI: componentesInserir)
            if(!listaCompConfigTemp.contains(idI))
                listaCompConfigTemp.add(idI);
        
        this.setChanged();
        this.notifyObservers();
    }
    
    public void atualizaListaComp() {
        
        listaCompConfig.clear();
        for (Integer i: listaCompConfigTemp)
            listaCompConfig.add(i);
        
        this.setChanged();
        this.notifyObservers();
    }
    
    //Determina lista de packs temp
    public void atualizaListaPacksTemp () {
        
        //mantem o modelo (pacote) base 1 ou 2 - e obrigatorio manter sempre um destes dois
        for(int i: listaPackConfig) {
            if((i<=2) && !listaPackConfigTemp.contains((Integer) i))
                listaPackConfigTemp.add(i);
        }
        
        for(Pacote p: this.listaPacotes.values()) {
            boolean existem = true;
            for(Componente c: p.getListaCompPack().values()) {
                existem = existem && (listaCompConfigTemp.contains(c.getIdComponente()));
            }
            if(existem && !listaPackConfigTemp.contains((Integer) p.getIdPack()))
                listaPackConfigTemp.add(p.getIdPack());
        }
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    public void atualizaListaPacks () {
        
        listaPackConfig.clear();
        
        for(Integer i: this.listaPackConfigTemp)
            listaPackConfig.add(i);
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    //Verifica packs a remover (com algum componente incompatível)
    public void devolvePacksRemover() {
        
        for(int id: listaPackConfig) {
            if(!listaPackConfigTemp.contains(id) && (id>2))
                packsRemover.add(id);
        }
        
        this.setChanged();
        this.notifyObservers();
           
    }
    
    //Verifica packs a inserir 
    public void devolvePacksAdicionar() {
        
        for(int id: listaPackConfigTemp) {
            if(!listaPackConfig.contains(id))
                packsInserir.add(id);
        }
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    public void escolheModeloBase(int idPacote) {        
        //Adiciona pack base
        //Nao pode mudar de base uma vez selecionada - se quiser mudar tem de cancelar e iniciar uma nova configuracao
        listaCompConfig.clear();
        listaPackConfig.clear();
        
        listaPackConfig.add(idPacote);
        for(Componente c: this.listaPacotes.get(idPacote).getListaCompPack().values()) {
           // if(!c.getCategoria().equals("Motor"))
                listaCompConfig.add(c.getIdComponente());
        }
        
        for(int i: this.listaCompConfig) {
            System.out.println(i+"   "+this.listaComponentes.get(i).getDesignacaoComp());   
        }
        this.setChanged();
        this.notifyObservers();
    }
    
    public void escolhePacotes(int idPack) {
        
        componentesRemover.clear();
        componentesInserir.clear();
        packsRemover.clear();
        packsInserir.clear();
        listaCompConfigTemp.clear();
        listaCompConfigTemp.addAll(listaCompConfig);
        listaPackConfigTemp.clear();
        
        for(Componente c: this.listaPacotes.get(idPack).getListaCompPack().values()) {
            //Verificar componentes a remover (incompatíveis)
            devolveCompRemover(c.getIdComponente());
            //Verificar componentes a inserir (obrigatórias)
            devolveCompInserir(c.getIdComponente());

            for(int i:componentesInserir) {
                devolveCompRemover(i);
            }
            //Determina lista de componentes temp
            atualizaListaCompTemp(c.getIdComponente());        
            //Determina lista de packs temp
            atualizaListaPacksTemp();
        }
        
        //Verifica packs a remover (com algum componente incompatível)
        devolvePacksRemover();
        //Verifica packs a inserir 
        devolvePacksAdicionar();
              
    }


    public void atualizaListaCompPack(int novoPack) {
     
        Pacote p = this.listaPacotes.get(novoPack);
        
        for(Componente c: p.getListaCompPack().values())
            if(!listaCompConfig.contains(c.getIdComponente()))
                listaCompConfig.add(c.getIdComponente());
        for(Integer idR: componentesRemover)
            if(listaCompConfigTemp.contains(idR))
                listaCompConfig.remove(idR);
        for(Integer idI: componentesInserir)
            if(!listaCompConfigTemp.contains(idI))
                listaCompConfig.add(idI);
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    
    public void confirmaConfiguracao(String nifCliente) {
        
        Map<Integer,Componente> mapCompConfig = new HashMap<>();
        Map<Integer,Pacote> mapPackConfig = new HashMap<>();
        
            for(int id: listaCompConfig)
                mapCompConfig.put(id, this.listaComponentes.get(id));
            for(int id: listaPackConfig)
                mapPackConfig.put(id, this.listaPacotes.get(id));
                
            config = new Configuracao(idProximaConfig(), nifCliente, mapCompConfig, mapPackConfig);
            try {
                ConfiguracaoDAO.guardarConfiguracao(config);
                listaConfiguracoes.put(idProximaConfig(), config);  
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        
        this.setChanged();
        this.notifyObservers();
        
    }
    
    private int idProximaConfig() {
        
        int res = 0;
        for(Configuracao c: listaConfiguracoes.values())
            if(c.getIdConfig()>res)
                res = c.getIdConfig();
        
        return (res+1);
        
    }
    
    
    public void removeComponente (int idComponente) {
        
        Integer idPackRemover = 0;
        for(int i: this.listaPackConfig)
            if((this.listaPacotes.get(i).getListaCompPack().containsKey(idComponente)) && (i>2))
                idPackRemover = i;

        if(idPackRemover!=0)
            this.listaPackConfig.remove(idPackRemover);
        
        this.listaCompConfig.remove((Integer) idComponente);
        
    }
    
    //Devolve o valor a pagar por um determinado componente (Se fizer parte de um pacote selecionado = 0.0)
    
    public Double valorPagarComponente(int idC) {
        
        Double valor = listaComponentes.get(idC).getPrecoComp();
        
        for(int i: listaPackConfig) {
            if(listaPacotes.get(i).getListaCompPack().containsKey(idC))  //Se for de um pack base
                valor = 0.0;
        }
        return valor;
        
    }

    public String getCompQueObriga(Integer comp) {
        
        String res = "";
        
        for(Componente c: this.listaComponentes.values()) {
            if(c.getListaIdCompObrigatorios().contains(comp) && this.listaCompConfig.contains(c.getIdComponente())) {
                res = c.getDesignacaoComp();
                break;
            }
        }
        return res;
    }
    
    //Método a desenvolver
    public void geraConfigOtima() {
        
        System.out.println("Método geraConfigOtima() em construção...");
        
    }
    
    //  Métodos Administrador
    public void addFuncionario(String username, String password, String nome) throws SQLException {
        
        Funcionario f = new Funcionario (username, password, nome);
        
        try {
            
            FuncionarioDAO.guardarFuncionario(f);
            this.listaFuncionarios.put(username, f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setChanged();
        this.notifyObservers();
    }
        
    
    public void removeFuncionario(String username) throws SQLException {
  
        try {
            FuncionarioDAO.removerFuncionario(this.listaFuncionarios.get(username));
            this.listaFuncionarios.remove(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setChanged();
        this.notifyObservers();
    }
    
    public void atualizaStock(int idComponente, int nStock) {
        
        try {
            ComponenteDAO.atualizaStock(idComponente, nStock);
            this.listaComponentes.get(idComponente).setStock(nStock);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        //recalcula a lista temporaria de carros que podem ser produzidos
        fabricoConfig();
        
        this.setChanged();
        this.notifyObservers();      
    } 
    
    //calcula a lista temporaria de carros que podem ser produzidos
    public void fabricoConfig() {
        listaProducaoTemp.clear();  
        Map<Integer,Componente> listaCompTemp = new HashMap<>();
        listaCompTemp=this.getComponentes();
        Componente compL;
        int stockACT;
        
        boolean temStockProducao;
        for(Configuracao config: this.listaConfiguracoes.values()) {
            temStockProducao = true;
            for(Componente c: config.getListaComponentes().values()) {
                temStockProducao = (temStockProducao && (listaCompTemp.get(c.getIdComponente()).getStock()>0));
            }
            if(temStockProducao) {
                listaProducaoTemp.add(config.getIdConfig());
                for(Componente c: config.getListaComponentes().values()) {
                    compL = listaCompTemp.get(c.getIdComponente());
                    System.out.println(listaCompTemp.get(c.getIdComponente()).getIdComponente() + "  " + listaCompTemp.get(c.getIdComponente()).getDesignacaoComp()+ "   "+ listaCompTemp.get(c.getIdComponente()).getStock());
                    stockACT = ((compL.getStock()) - 1);
                    compL.setStock(stockACT);
                    
                    System.out.println(listaCompTemp.get(c.getIdComponente()).getIdComponente() + "  " + listaCompTemp.get(c.getIdComponente()).getDesignacaoComp()+ "   "+ listaCompTemp.get(c.getIdComponente()).getStock());
                }  
            }
        }
        //this.setChanged();
        //this.notifyObservers();     
    }
                
    public void confirmaProducao() {
        
        int idComponente, nStock;
        
        ArrayList<Integer> nova = new ArrayList<>();
        nova.addAll(listaProducaoTemp);
        
        for(Integer i: nova)
        {
            for(Componente c: this.listaConfiguracoes.get(i).getListaComponentes().values()) {
                idComponente = c.getIdComponente();
                //componente = this.listaComponentes.get(idComponente).
                nStock = ((this.listaComponentes.get(idComponente).getStock())-1);
                atualizaStock(idComponente, nStock);
            }
            try {
                ConfiguracaoDAO.removerConfiguracao(this.listaConfiguracoes.get(i));
                this.listaConfiguracoes.remove(i);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
                              
        }
        //recalcula a lista temporaria de carros que podem ser produzidos
        fabricoConfig();
        
        //this.setChanged();
        //this.notifyObservers();    
    }
    
                
    public void carregaFuncionarios()
    {
        
        try {
            for(Funcionario f: FuncionarioDAO.list().values())
                listaFuncionarios.put(f.getUsername(),f);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
       this.setChanged();
       this.notifyObservers();
    }
   
    public void carregaComponentes()
    {
        
        try {
            for(Componente c: ComponenteDAO.list().values())
                listaComponentes.put(c.getIdComponente(),c);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       this.setChanged();
       this.notifyObservers();
    }  
    
    public void carregaPacotes()
    {
        
        try {
            for(Pacote p: PacoteDAO.list().values())
                listaPacotes.put(p.getIdPack(),p);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    public void carregaConfiguracoes()
    {
        
         try {
             for(Configuracao c: ConfiguracaoDAO.list().values())
                 listaConfiguracoes.put(c.getIdConfig(),c);
            
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
        
    }

    
     public Componente getComponente(int idComponente)
    {

        Componente c = this.listaComponentes.get(idComponente);
        return (Componente)c.clone();

    }

    public int getIdComponenteBase(int modelo, int componente) {
        int res=0;
        
        String cat = listaComponentes.get(componente).getCategoria();
        
        for(Componente c: listaPacotes.get(modelo).getListaCompPack().values())
            if((c.getCategoria().equals(cat)) && (c.getIdComponente()<=12))
                res = c.getIdComponente();
        
        return res;
    }
    
    public Map<String, Funcionario> getFuncionarios()
    {
        Map<String,Funcionario> nl = new HashMap <>();
        
        if(this.listaFuncionarios.isEmpty())
            System.out.println("Não existem funcionários registados");
        else
            this.listaFuncionarios.values().forEach((f) -> nl.put(f.getUsername(), (Funcionario)f.clone()));
          
        return nl;
    }
    
    public Map<Integer, Componente> getComponentes()
    {
        Map<Integer,Componente> nl = new HashMap <>();
        
        if(this.listaComponentes.isEmpty())
            System.out.println("Não existem componentes registados");
        else
            this.listaComponentes.values().forEach((c) -> nl.put(c.getIdComponente(), (Componente)c.clone()));
          
        return nl;
    }
    
    public Map<Integer, Pacote> getPacotes()
    {
        Map<Integer,Pacote> nl = new HashMap <>();
        
        if(this.listaPacotes.isEmpty())
            System.out.println("Não existem pacotes registados");
        else
            this.listaPacotes.values().forEach((p) -> nl.put(p.getIdPack(), (Pacote) p.clone()));
          
        return nl;
    }
    
    public Map<Integer, Configuracao> getConfiguracoes()
    {
        Map<Integer,Configuracao> nl = new HashMap <>();
        
        if(this.listaConfiguracoes.isEmpty())
            System.out.println("Não existem configurações registados");
        else
            this.listaConfiguracoes.values().forEach((c) -> nl.put(c.getIdConfig(), (Configuracao)c.clone()));
          
        return nl;
    }
    
    public ArrayList<Integer> getListProducao()
    {
        ArrayList<Integer> nl = new ArrayList <>();
                
        if(this.configProduzir.isEmpty())
            System.out.println("Não existem configurações registados");
        else {
        
        for(int i: this.configProduzir)
            nl.add(i);
          }
        return nl;
    }
    
    public Configuracao getConfig() {
        return config;
    }

    public ArrayList<Integer> getConfigProduzirTemp() {
        return configProduzirTemp;
    }

    public ArrayList<Integer> getListaCompConfig() {
        
        
        
        return listaCompConfig;
    }

    public ArrayList<Integer> getListaPackConfig() {
        return listaPackConfig;
    }

    public ArrayList<Integer> getListaCompConfigTemp() {
        return listaCompConfigTemp;
    }

    public ArrayList<Integer> getListaPackConfigTemp() {
        return listaPackConfigTemp;
    }

    public ArrayList<Integer> getComponentesRemover() {
        return componentesRemover;
    }

    public ArrayList<Integer> getComponentesInserir() {
        return componentesInserir;
    }

    public ArrayList<Integer> getPacksRemover() {
        return packsRemover;
    }

    public ArrayList<Integer> getPacksInserir() {
        return packsInserir;  
    }
    
    public ArrayList<Integer> getListaProducaoTemp() {
        return listaProducaoTemp;
    }
     
}
