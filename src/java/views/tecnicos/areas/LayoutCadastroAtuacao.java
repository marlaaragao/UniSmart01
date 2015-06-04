/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views.tecnicos.areas;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import entidade.Atuacao;
import persistencia.AtuacaoDao;
import views.LayoutPrincipalTecnico;

/**
 * Layout que permite cadastro das Áreas de Atuação
 * 
 * Referencia o Requisito R5 - Efetuar cadastros de disciplinas, turmas, professores, alunos, áreas.
 * 
 * @author Marla Aragão
 */
public class LayoutCadastroAtuacao extends VerticalLayout {

    private TextField id;
    private TextField descricao;
    private Button btnSalvar;
    private Button btnVoltar;
    private Operacao operacao;
    private VerticalLayout content;

    public LayoutCadastroAtuacao(Operacao operacao, VerticalLayout content) {
        
        this.operacao = operacao;
        this.content = content;
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        Label label = new Label("<font size=\"2\" color=\"#287ece\"><b>Cadastro Área</b></font>"
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
        
        
        this.addComponent(id);
        this.addComponent(descricao);
        
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
                views.tecnicos.areas.LayoutAtuacao l = new views.tecnicos.areas.LayoutAtuacao(content);
                content.removeAllComponents();
                content.addComponent(l);
            }
        });
        
        hLayout.addComponent(btnSalvar);
        hLayout.addComponent(btnVoltar);
        
        this.addComponent(hLayout);
        
    }
    
    public void loadDados(Atuacao p) {
        if (p == null) {
            return;
        }
        
        id.setValue(String.valueOf(p.getId()));
        descricao.setValue(p.getDescricao());
    }
    
    private void salvarDados() {
        
        try {
            
            Atuacao atuacao = new Atuacao();
            
            int codigoP = -1;
            
            try {
                codigoP = Integer.parseInt(id.getValue());
            } catch (NumberFormatException e) {
//                Notification.show("", null, Notification.Type.ERROR_MESSAGE);
            }
            
            atuacao.setId(codigoP);
            atuacao.setDescricao(descricao.getValue());

            if (operacao.equals(Operacao.ALTERAR)) {
                new AtuacaoDao().update(atuacao);
                
            } else {
                new AtuacaoDao().insert(atuacao);
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
