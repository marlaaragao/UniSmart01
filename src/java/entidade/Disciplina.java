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
public class Disciplina {
    
    private int id;
    private String descricao;
    private int ano;
    private int atuacao;
    private boolean ativa;

    public Disciplina() {
    }

    public Disciplina(int id, String descricao, int ano, int area, boolean ativa) {
        this.id = id;
        this.descricao = descricao;
        this.ano = ano;
        this.atuacao = area;
        this.ativa = ativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(int area) {
        this.atuacao = area;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    
    
    
}
