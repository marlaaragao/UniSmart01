/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.professores;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import entidade.Aluno;
import entidade.AlunoTurma;
import java.sql.SQLException;
import java.util.List;
import persistencia.AlunoDao;
import persistencia.AlunoTurmaDao;
import persistencia.TurmaDao;

/**
 *
 * @author Marla Aragão
 */
public class WindowLancamentoNotas extends Window {

    private VerticalLayout content;
    private int turma;
    private int professor;
    private Button btnSalvar;
    private Button btnVoltar;
    private VerticalLayout layoutLancamentosNotas;
    
    public WindowLancamentoNotas(int turma, int professor) throws SQLException {
        
        this.turma = turma;
        this.professor = professor;
        
        center();
        setImmediate(true);
        setModal(true);
        setCaption("Lançamentos de Nota - " + new TurmaDao().select(turma).getDescricao());
        setWidth("60%");
        setHeight("70%");
        
        setStyleName(Reindeer.WINDOW_LIGHT);
        
        content = new VerticalLayout();
        content.setMargin(new MarginInfo(true, false, false, true));
        content.setSizeFull();
        content.setSpacing(true);
        content.setImmediate(true);
        
        setContent(content);
        
        init();
        
    }
    
    private void init() {

        //Headers
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSpacing(true);
        hLayout.setImmediate(true);
        hLayout.setWidth("100%");
        hLayout.setHeight("100%");
        hLayout.setMargin(new MarginInfo(false, true, false, true));
                
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Nome do Aluno</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Matrícula</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Nota Final</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Total de Faltas</b></font>", ContentMode.HTML));
        
        content.addComponent(hLayout);

        VerticalLayout layoutLancamentos = gerarLayoutLancamentos();
        content.addComponent(layoutLancamentos);
        
        //Botoes
        HorizontalLayout layoutBotoes = new HorizontalLayout();
        layoutBotoes.setSpacing(true);
        layoutBotoes.setImmediate(true);
        layoutBotoes.setMargin(true);
        
        btnSalvar = new Button("Salvar");
        btnSalvar.setImmediate(true);
        btnSalvar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (salvarDados()) {
                    Notification.show("Dados gravados com sucesso.", Notification.Type.HUMANIZED_MESSAGE);
                    WindowLancamentoNotas.this.close();
                }
            }
 
        });
        
        btnVoltar = new Button("Voltar");
        btnVoltar.setImmediate(true);
        btnVoltar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                WindowLancamentoNotas.this.close();
            }
        });
        
        layoutBotoes.addComponent(btnSalvar);
        layoutBotoes.addComponent(btnVoltar);
        
        content.addComponent(layoutBotoes);
        content.setComponentAlignment(hLayout, Alignment.TOP_CENTER);
        content.setComponentAlignment(layoutBotoes, Alignment.TOP_CENTER);
        content.setComponentAlignment(layoutBotoes, Alignment.BOTTOM_CENTER);

    }
    
    private VerticalLayout gerarLayoutLancamentos() {
        
        layoutLancamentosNotas = new VerticalLayout();
        layoutLancamentosNotas.setSpacing(true);
        layoutLancamentosNotas.setImmediate(true);
        layoutLancamentosNotas.setStyleName(Reindeer.LAYOUT_WHITE);
        
        layoutLancamentosNotas.addComponent(new Label("<hr />", ContentMode.HTML));
        
        List<Aluno> listaAlunos = null;
        try {
            listaAlunos = new AlunoDao().selectAllTurma(turma);
        } catch (SQLException ex) {
        }

        for (Aluno a : listaAlunos) {
            
            AlunoTurma aluno_turma = null;
            try {
                aluno_turma = new AlunoTurmaDao().select(turma, a.getId());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            HorizontalLayout h = new HorizontalLayout();
            h.setSpacing(true);
            h.setImmediate(true);
            h.setWidth("100%");
            h.setStyleName(Reindeer.LAYOUT_WHITE);
            h.setMargin(new MarginInfo(false, true, false, true));
            h.setData(a.getId());
            
            Label lNome = new Label(a.getNome());
            
            Label lMatricula = new Label(String.valueOf(a.getMatricula()));
            
            TextField tNota = new TextField();
            tNota.setData("nota");
            if (aluno_turma != null && aluno_turma.getNota_aluno() != 0) {
                tNota.setValue(String.valueOf(aluno_turma.getNota_aluno()));
            }
            
            TextField tFaltas = new TextField();
            tFaltas.setData("faltas");
            if (aluno_turma != null && aluno_turma.getNum_faltas() != 0) {
                tFaltas.setValue(String.valueOf(aluno_turma.getNum_faltas()));
            }

            h.addComponent(lNome);
            h.addComponent(lMatricula);
            h.addComponent(tNota);
            h.addComponent(tFaltas);
            
            layoutLancamentosNotas.addComponent(h);
            layoutLancamentosNotas.addComponent(new Label("<hr />", ContentMode.HTML));
        }
        
        return layoutLancamentosNotas;
    }
    
    private boolean salvarDados() {
        
        AlunoTurma aluno_turma = new AlunoTurma();
        AlunoTurmaDao alunoTurmaDao = new AlunoTurmaDao();
        
        for (Component c : layoutLancamentosNotas) {
            if (c instanceof HorizontalLayout) {
                for (Component c1 : (HorizontalLayout) c) {
                    aluno_turma.setId_aluno((int) ((HorizontalLayout) c).getData());
                    aluno_turma.setId_turma(turma);
                    
                    if (c1 instanceof TextField) {
                        if (((TextField) c1).getData().equals("nota")) {
                            
                            double nota = 0;
                            try {
                                nota = Double.parseDouble(((TextField) c1).getValue());
                            } catch (Exception e) {
                                Notification.show("Um ou mais valores não estão de acordo com o padrão. "
                                        + "Lembre-se: para notas, números reais com notação 0.00 e para faltas inteiro.", Notification.Type.ERROR_MESSAGE);
                                return false;
                            }
                            
                            aluno_turma.setNota_aluno(nota);
                            
                        } else if (((TextField) c1).getData().equals("faltas")) {
                            
                            int faltas = 0;
                            try {
                                faltas = Integer.parseInt(((TextField) c1).getValue());
                            } catch (Exception e) {
                                Notification.show("Um ou mais valores não estão de acordo com o padrão. "
                                        + "Lembre-se: para notas, números reais com notação 0.00 e para faltas inteiro.", Notification.Type.ERROR_MESSAGE);
                                return false;
                            }
                            
                            aluno_turma.setNum_faltas(faltas);
                            
                        } 
                    }
                }
                try {
                    
                    if (!alunoTurmaDao.alunoInscrito(aluno_turma.getId_turma(), aluno_turma.getId_aluno())) {
                        alunoTurmaDao.insert(aluno_turma);
                        return true;
                    } else {
                        alunoTurmaDao.update(aluno_turma);
                        return true;
                    }                
                
                } catch (SQLException ex) {
                    Notification.show("Ocorreu um erro ao gravar os dados no banco de dados. "
                            + "Tente novamente ou entre em contato com o administrador do sistema.", Notification.Type.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return false;
        
    }
}
