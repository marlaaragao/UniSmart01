/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.professores;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import entidade.AlunoTurma;
import entidade.Turma;
import java.sql.SQLException;
import java.util.List;
import persistencia.AlunoTurmaDao;
import persistencia.DisciplinaDao;
import persistencia.ProfessorDao;
import persistencia.TurmaDao;

/**
 *
 * @author Marla Aragão
 */
public class LayoutTurmasLecionadas extends VerticalLayout {
    
    private VerticalLayout content;
    private boolean confirmation = false;
    
    public LayoutTurmasLecionadas(VerticalLayout content) {
        this.content = content;
        
        setImmediate(true);
        setMargin(true);
        setSpacing(true);
        
        this.content.setStyleName(Reindeer.LAYOUT_WHITE);
        
        init();
    }
    
    private void init() {
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSpacing(true);
        hLayout.setImmediate(true);
        hLayout.setWidth("100%");
        hLayout.setHeight("100%");
        hLayout.setMargin(new MarginInfo(false, true, false, true));
                
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Descrição</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Disciplina</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Professor</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Dias da Semana</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Período</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Horário</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Máx. Alunos</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Alunos Inscritos</b></font>", ContentMode.HTML));
        hLayout.addComponent(new Label("<font size=\"2\" color=\"#287ece\"><b>Ação</b></font>", ContentMode.HTML));
        
        content.addComponent(hLayout);

        content.addComponent(gerarLayoutInscricoes());
    }
    
    private VerticalLayout gerarLayoutInscricoes() {
        
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setSpacing(true);
        vLayout.setImmediate(true);
        vLayout.setStyleName(Reindeer.LAYOUT_WHITE);
        
        vLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        
        List<Turma> listaTurmas = null;
        try {
            listaTurmas = new TurmaDao().selectAll("ativa = 1 and professor = " 
                    + VaadinSession.getCurrent().getAttribute("professor"));
        } catch (SQLException ex) {
            
        }

        for (Turma t : listaTurmas) {
            HorizontalLayout h = new HorizontalLayout();
            h.setSpacing(true);
            h.setImmediate(true);
            h.setWidth("100%");
            h.setStyleName(Reindeer.LAYOUT_WHITE);
            h.setMargin(new MarginInfo(false, true, false, true));
            
            Button b = new Button("Lançar Notas");
            
            b.setData(t.getId());
            b.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    
                    try {
                        WindowLancamentoNotas w = new WindowLancamentoNotas((int) event.getButton().getData()
                                , (int)VaadinSession.getCurrent().getAttribute("professor"));
                        
                        UI.getCurrent().addWindow(w);
                        
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            
            h.addComponent(new Label(t.getDescricao()));
            h.addComponent(new Label(new DisciplinaDao().select(t.getDisciplina()).getDescricao()));
            try {
                h.addComponent(new Label(new ProfessorDao().select(t.getProfessor()).getNome()));
            } catch (SQLException ex) {
                h.addComponent(new Label(""));
            }
            h.addComponent(new Label(t.getDias_semana()));
            h.addComponent(new Label(t.getPeriodo()));
            h.addComponent(new Label(String.valueOf(t.getHorario())));
            h.addComponent(new Label(String.valueOf(t.getMax_alunos())));
            h.addComponent(new Label(String.valueOf(t.getAlunos_inscritos())));
            
            h.addComponent(b);
            
            vLayout.addComponent(h);
            vLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        }
        
        return vLayout;
    }
    
    private Boolean efetuarInscricao(int turma) {

        Turma t = null;

        try {
            t = new TurmaDao().select(turma);
        } catch (SQLException ex) {
            Notification.show("Ocorreu um erro ao tentar recuperar a turma.", Notification.Type.ERROR_MESSAGE);  
            return false;
        }

        if (t.getAlunos_inscritos() == t.getMax_alunos()) {
            Notification.show("A turma atingiu o máximo de alunos. Não é possível efetuar inscrição.", Notification.Type.WARNING_MESSAGE);
            return false;
        }

        t.setAlunos_inscritos(t.getAlunos_inscritos() + 1);
        new TurmaDao().update(t);

        try {
            new AlunoTurmaDao().insert(new AlunoTurma((int) VaadinSession.getCurrent().getAttribute("aluno")
                    , t.getId(), 0, 0));
        } catch (SQLException ex) {
            Notification.show("Ocorreu um erro ao tentar gravar a inscrição.", Notification.Type.ERROR_MESSAGE);
            return false;
        }

        return true;    
    }
    
    
    
}
