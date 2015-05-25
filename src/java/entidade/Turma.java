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
public class Turma {
    
    private int id;
    private String descricao;
    private int disciplina;
    private int max_alunos;
    private int professor;
    private String dias_semana;
    private String periodo;
    private int horario;

    public Turma() {
    }

    public Turma(int id, String descricao, int disciplina, int max_alunos, int professor, String dias_semana, String periodo, int horario) {
        this.id = id;
        this.descricao = descricao;
        this.disciplina = disciplina;
        this.max_alunos = max_alunos;
        this.professor = professor;
        this.dias_semana = dias_semana;
        this.periodo = periodo;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(int disciplina) {
        this.disciplina = disciplina;
    }

    public int getMax_alunos() {
        return max_alunos;
    }

    public void setMax_alunos(int max_alunos) {
        this.max_alunos = max_alunos;
    }

    public String getDias_semana() {
        return dias_semana;
    }

    public void setDias_semana(String dias_semana) {
        this.dias_semana = dias_semana;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getProfessor() {
        return professor;
    }

    public void setProfessor(int professor) {
        this.professor = professor;
    }
    
    
    
}
