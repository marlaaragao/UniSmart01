/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views.tecnicos.professores;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import entidade.Atuacao;
import entidade.Professor;
import java.util.Collection;
import persistencia.AtuacaoDao;
import persistencia.ProfessorDao;

/**
 * Layout que permite cadastro de professores
 * Referencia ao caso de uso CU7 - Cadastrar Professores
 * Referencia o Requisito R5 - Efetuar cadastros de disciplinas, turmas, professores, alunos, áreas.
 * 
 * @author Marla Aragão
 */
public class LayoutCadastroProfessores extends VerticalLayout {

    private TextField id;
    private TextField nome;
    private Button btnSalvar;
    private Button btnVoltar;
    private Operacao operacao;
    private TextField ano;
    private TextField cpf;
    private ComboBox atuacao;
    private VerticalLayout content;

    public LayoutCadastroProfessores(Operacao operacao, VerticalLayout content) {
        
        this.operacao = operacao;
        this.content = content;
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        Label label = new Label("<font size=\"2\" color=\"#287ece\"><b>Cadastro Professor</b></font>"
        , ContentMode.HTML);
        
        addComponent(label);
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        init();
        
    }
    
    private void init() {
        
        id = new TextField("Codigo:");
        id.setImmediate(true);
        id.setEnabled(false);
        
        nome = new TextField("Descricao:");
        nome.setWidth("60%");
        nome.setImmediate(true);
        
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
        
        cpf = new TextField("Cpf");
        cpf.setWidth("60%");
        cpf.setImmediate(true);
        
        this.addComponent(id);
        this.addComponent(nome);
        this.addComponent(cpf);
        this.addComponent(atuacao);
        
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
                LayoutProfessores l = new LayoutProfessores(content);
                content.removeAllComponents();
                content.addComponent(l);
            }
        });
        
        hLayout.addComponent(btnSalvar);
        hLayout.addComponent(btnVoltar);
        
        this.addComponent(hLayout);
        
    }
    
    public void loadDados(Professor p) {
        if (p == null) {
            return;
        }
        
        id.setValue(String.valueOf(p.getId()));
        nome.setValue(p.getNome());
        atuacao.setValue(p.getAtuacao());
        cpf.setValue(p.getCpf());
    }
    
    private void salvarDados() {
        
        try {
            
            Professor professor = new Professor();
            
            int codigoP = -1;
            
            try {
                codigoP = Integer.parseInt(id.getValue());
            } catch (NumberFormatException e) {
//                Notification.show("", null, Notification.Type.ERROR_MESSAGE);
            }
            
            professor.setId(codigoP);
            professor.setNome(nome.getValue());
            
            professor.setAtuacao((int) atuacao.getValue());
            professor.setCpf(cpf.getValue());
            
            if (operacao.equals(Operacao.ALTERAR)) {
                new ProfessorDao().update(professor);
                
            } else {
                new ProfessorDao().insert(professor);
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
