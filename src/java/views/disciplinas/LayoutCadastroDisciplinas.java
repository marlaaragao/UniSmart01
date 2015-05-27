/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views.disciplinas;

import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import entidade.Atuacao;
import entidade.Disciplina;
import java.util.Collection;
import persistencia.AtuacaoDao;
import persistencia.DisciplinaDao;
import views.LayoutPrincipalTecnico;

/**
 *
 * @author Marla Aragão
 */
public class LayoutCadastroDisciplinas extends VerticalLayout {

    private TextField id;
    private TextField descricao;
    private Button btnSalvar;
    private Button btnVoltar;
    private Operacao operacao;
    private TextField ano;
    private CheckBox ativa;
    private ComboBox atuacao;

    public LayoutCadastroDisciplinas(Operacao operacao) {
        
        this.operacao = operacao;
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        Label label = new Label("<font size=\"2\" color=\"#287ece\"><b>Cadastro Disciplina</b></font>"
        , ContentMode.HTML);
        
        addComponent(label);
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        init();
        
    }
    
    private void init() {
        
        id = new TextField("Codigo:");
        id.setImmediate(true);
        id.setEnabled(false);
        
        descricao = new TextField("Descricao:");
        descricao.setWidth("60%");
        descricao.setImmediate(true);
        
        ano = new TextField("Ano:");
        ano.setWidth("60%");
        ano.setImmediate(true);
        
        Collection<Atuacao> atuacoes = new AtuacaoDao().selectAll();
        
        atuacao = new ComboBox("Áreas de atuação:");
        atuacao.setWidth("30%");
        atuacao.setImmediate(true);
        atuacao.setNullSelectionAllowed(false);
        atuacao.setTextInputAllowed(false);
        atuacao.setInputPrompt("Selecione");
        
        for (Atuacao a : atuacoes) {
            atuacao.addItem(a.getId());
            atuacao.setItemCaption(a.getId(), a.getDescricao());
        }
        
        atuacao.select(atuacao.getItemIds().iterator().next());
        
        ativa = new CheckBox("Ativa", true);
        ativa.setWidth("60%");
        ativa.setImmediate(true);
        
        this.addComponent(id);
        this.addComponent(descricao);
        this.addComponent(ano);
        this.addComponent(atuacao);
        this.addComponent(ativa);
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setImmediate(true);
        hLayout.setSpacing(true);
        
        btnSalvar = new Button("Salvar");
        btnSalvar.setImmediate(true);
        btnSalvar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                salvarDados();
            }
        });
        
        btnVoltar = new Button("Voltar");
        btnVoltar.setImmediate(true);
        btnVoltar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                LayoutPrincipalTecnico l = new LayoutPrincipalTecnico();
                UI.getCurrent().setContent(l);
            }
        });
        
        hLayout.addComponent(btnSalvar);
        hLayout.addComponent(btnVoltar);
        
        this.addComponent(hLayout);
        
    }
    
    public void loadDados(Disciplina p) {
        if (p == null) {
            return;
        }
        
        id.setValue(String.valueOf(p.getId()));
        descricao.setValue(p.getDescricao());
        ano.setValue(String.valueOf(p.getAno()));
        atuacao.setValue(p.getAtuacao());
        ativa.setValue(p.isAtiva());
    }
    
    private void salvarDados() {
        
        try {
            
            Disciplina disciplina = new Disciplina();
            
            int codigoP = -1;
            
            try {
                codigoP = Integer.parseInt(id.getValue());
            } catch (NumberFormatException e) {
//                Notification.show("", null, Notification.Type.ERROR_MESSAGE);
            }
            
            disciplina.setId(codigoP);
            disciplina.setDescricao(descricao.getValue());
            
            try {
                disciplina.setAno(Integer.parseInt(ano.getValue()));
            } catch (Exception e) {
                ano.setComponentError(new UserError("Ano inválido."));
                return;
            }
            
            disciplina.setAtuacao((int) atuacao.getValue());
            disciplina.setAtiva(ativa.getValue());

            if (operacao.equals(Operacao.ALTERAR)) {
                new DisciplinaDao().update(disciplina);
                
            } else {
                new DisciplinaDao().insert(disciplina);
            }
            
            Notification.show("", "Dados gravados com sucesso", Notification.Type.HUMANIZED_MESSAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("", "Erro ao salvar no banco. Tente novamente.", Notification.Type.ERROR_MESSAGE);
        }
        
        btnVoltar.click();
        
    }
    
    public enum Operacao {
        ALTERAR, INCLUIR;
    }
    
}
