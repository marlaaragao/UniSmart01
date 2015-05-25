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
public class Professor {
    
    private int id;
    private String nome;
    private String cpf;
    private int area;

    public Professor() {
    }

    public Professor(int id, String nome, String cpf, int area) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
    
    
    
}
