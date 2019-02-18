/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;


public class Funcionario {
    
    private String username;
    private String password;
    private String nome;
    
    // Construtores (por omissão, parametrizado, de cópia)
    
    public Funcionario() {
        this.username = "";
        this.password = "";
        this.nome="";
    }
    
    public Funcionario(String username, String password, String nome) {
        this.username = username;
        this.password = password;  
        this.nome = nome;
    }

    public Funcionario(Funcionario umFuncionario) {
        this.username = umFuncionario.getUsername();
        this.password = umFuncionario.getPassword();
        this.nome = umFuncionario.getNome();
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }
    
    //Setters
    
    public void setUsername(String idFuncionario) {
        this.username = idFuncionario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Object clone() 
    {
        return new Funcionario(this);
    }
    
    
    
}
