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
public class Aluno {
    
    private int id;
    private int matricula;
    private String cpf;
    private String nome;
    private int curso;

    public Aluno(int id, int matricula, String cpf, String nome, int curso) {
        this.id = id;
        this.matricula = matricula;
        this.cpf = cpf;
        this.nome = nome;
        this.curso = curso;
    }

    public Aluno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }
    
    
    
}
