/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views.turmas;

import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import entidade.Atuacao;
import entidade.Disciplina;
import entidade.Professor;
import entidade.Turma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.AtuacaoDao;
import persistencia.DisciplinaDao;
import persistencia.ProfessorDao;
import persistencia.TurmaDao;
import views.LayoutPrincipalTecnico;

/**
 *
 * @author Marla Aragão
 */
public class LayoutCadastroTurmas extends VerticalLayout {

    private Button btnSalvar;
    private Button btnVoltar;
    private Operacao operacao;
    private TextField id;
    private TextField descricao;
    private ComboBox disciplina;
    private TextField max_alunos;
    private ComboBox professor;
    private OptionGroup dias_semana;
    private TextField periodo;
    private TextField horario;
    private CheckBox ativa;
    private String dias;

    public LayoutCadastroTurmas(Operacao operacao) {
        
        this.operacao = operacao;
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        setSizeFull();
        
        Label label = new Label("<font size=\"2\" color=\"#287ece\"><b>Cadastro Turma</b></font>"
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
        
        max_alunos = new TextField("Max. Alunos:");
        max_alunos.setWidth("60%");
        max_alunos.setImmediate(true);
        
        Collection<Professor> professores = null;
        try {
            professores = new ProfessorDao().selectAll();
        } catch (SQLException ex) {
        }
        
        professor = new ComboBox("Professor:");
        professor.setWidth("30%");
        professor.setImmediate(true);
        professor.setNullSelectionAllowed(false);
        professor.setTextInputAllowed(false);
        professor.setInputPrompt("Selecione");
        
        for (Professor p : professores) {
            professor.addItem(p.getId());
            professor.setItemCaption(p.getId(), p.getNome());
        }
        
        professor.select(professor.getItemIds().iterator().next());
        
        Collection<Disciplina> disciplinas = null;
        disciplinas = new DisciplinaDao().selectAll();
        
        disciplina = new ComboBox("Disciplina:");
        disciplina.setWidth("30%");
        disciplina.setImmediate(true);
        disciplina.setNullSelectionAllowed(false);
        disciplina.setTextInputAllowed(false);
        disciplina.setInputPrompt("Selecione");
        
        for (Disciplina d : disciplinas) {
            disciplina.addItem(d.getId());
            disciplina.setItemCaption(d.getId(), d.getDescricao());
        }
        
        disciplina.select(disciplina.getItemIds().iterator().next());
        
        Collection diasSemana = new ArrayList<String>();
        diasSemana.add("Seg");
        diasSemana.add("Ter");
        diasSemana.add("Qua");
        diasSemana.add("Qui");
        diasSemana.add("Sex");
        diasSemana.add("Sab");

        dias_semana = new OptionGroup("Dias da semana:", diasSemana);
        dias_semana.select("Seg");
        dias_semana.setImmediate(true);
        dias_semana.setMultiSelect(true);
        dias_semana.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                dias += event.getProperty();
            }
        });
        
        Label label = new Label("Atenção: Digite os períodos como M para matutino, V para vespertino, e N para noturno.");
        Label label2 = new Label("Atenção: Os peridos devem ser digitados na ordem dos dias da semana. Se escolheu segunda");
        Label label3 = new Label(" e quarta como dias de aula, deverá digitar MN por exemplo, para Manutino na segunda e N na quarta.");
        periodo = new TextField("Periodo");
        periodo.setImmediate(true);
        periodo.setWidth("60%");
        
        Label label4 = new Label("Atenção: Digite os horário de 1 a 5 para primeiro, segundo, terceiro, quarto, e quinto horário.");
        Label label5 = new Label("Atenção: Os horarios devem ser digitados na ordem dos dias da semana e periodo. Se escolheu segunda");
        Label label6 = new Label(" e quarta como dias de aula, com periodos matutino e verpertino, devera digitar 12 para segunda primeiro e segundo horarios"
                + " e 45 para quarta quarto e quinto horarios.");
        horario = new TextField("Horario");
        horario.setImmediate(true);
        horario.setWidth("60%");
        
        ativa = new CheckBox("Ativa", true);
        ativa.setWidth("60%");
        ativa.setImmediate(true);
        
        this.addComponent(id);
        this.addComponent(descricao);
        this.addComponent(disciplina);
        this.addComponent(professor);
        this.addComponent(max_alunos);
        this.addComponent(dias_semana);
        this.addComponent(periodo);
        this.addComponent(horario);
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
    
    public void loadDados(Turma t) {
        if (t == null) {
            return;
        }
        
        id.setValue(String.valueOf(t.getId()));
        descricao.setValue(t.getDescricao());
        horario.setValue(String.valueOf(t.getHorario()));
        periodo.setValue(t.getPeriodo());
        professor.setValue(t.getProfessor());
        disciplina.setValue(t.getDisciplina());
        dias_semana.setValue(t.getDias_semana());
        max_alunos.setValue(String.valueOf(t.getMax_alunos()));
        ativa.setValue(t.isAtiva());
    }
    
    private void salvarDados() {
        
        try {
            
            Turma turma = new Turma();
            
            int codigoP = -1;
            
            try {
                codigoP = Integer.parseInt(id.getValue());
            } catch (NumberFormatException e) {
//                Notification.show("", null, Notification.Type.ERROR_MESSAGE);
            }
            
            turma.setId(codigoP);
            turma.setDescricao(descricao.getValue());
            
            try {
                turma.setMax_alunos(Integer.parseInt(max_alunos.getValue()));
            } catch (Exception e) {
                max_alunos.setComponentError(new UserError("Deve ser digitado numero inteiro valido."));
                return;
            }
            
            try {
                turma.setHorario(Integer.parseInt(horario.getValue()));
            } catch (Exception e) {
                horario.setComponentError(new UserError("Deve ser digitado numero inteiro valido."));
                return;
            }
            
            turma.setProfessor((int) professor.getValue());
            turma.setAtiva(ativa.getValue());
            turma.setDias_semana(dias);
            turma.setPeriodo(periodo.getValue());
            turma.setDisciplina((int)disciplina.getValue());

            if (operacao.equals(Operacao.ALTERAR)) {
                new TurmaDao().update(turma);
                
            } else {
                new TurmaDao().insert(turma);
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
