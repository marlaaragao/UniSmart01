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
public class AlunoTurma {
    
    
    private int id_aluno;
    private int id_turma;
    private double nota_aluno;
    private int num_faltas;

    public AlunoTurma(int id_aluno, int id_turma, double nota_aluno, int num_faltas) {
        this.id_aluno = id_aluno;
        this.id_turma = id_turma;
        this.nota_aluno = nota_aluno;
        this.num_faltas = num_faltas;
    }

    public int getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public int getId_turma() {
        return id_turma;
    }

    public void setId_turma(int id_turma) {
        this.id_turma = id_turma;
    }

    public double getNota_aluno() {
        return nota_aluno;
    }

    public void setNota_aluno(double nota_aluno) {
        this.nota_aluno = nota_aluno;
    }

    public int getNum_faltas() {
        return num_faltas;
    }

    public void setNum_faltas(int num_faltas) {
        this.num_faltas = num_faltas;
    }
    
    
    
}
