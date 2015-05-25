/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

/**
 *
 * @author Marla Aragão
 */
public class Usuario {
    
    private int id;
    private String nomeUsuario;
    private String senha;
    private int perfil;

    public Usuario() {
    }

    public Usuario(int id, String nomeUsuario, String senha, int perfil) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    
    
}
