/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.alunos;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import entidade.Disciplina;
import entidade.Turma;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.DisciplinaDao;
import persistencia.ProfessorDao;
import persistencia.TurmaDao;

/**
 *
 * @author Marla Aragão
 */
public class LayoutInscricaoTurma extends VerticalLayout {
    
    private VerticalLayout content;
    
    public LayoutInscricaoTurma(VerticalLayout content) {
        this.content = content;
        
        setImmediate(true);
        setMargin(true);
        setSpacing(true);
        
        init();
    }
    
    private void init() {
        addComponent(gerarLayoutInscricoes());
        content.addComponent(this);
    }
    
    private VerticalLayout gerarLayoutInscricoes() {
        
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setSpacing(true);
        vLayout.setImmediate(true);
        
        List<Turma> listaTurmas = null;
        try {
            listaTurmas = new TurmaDao().selectAll("ativa = 1");
        } catch (SQLException ex) {
            
        }
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSpacing(true);
        hLayout.setImmediate(true);
        hLayout.setWidth("100%");
                
        hLayout.addComponent(new Label("Descrição"));
        hLayout.addComponent(new Label("Disciplina"));
        hLayout.addComponent(new Label("Professor"));
        hLayout.addComponent(new Label("Dias da Semana"));
        hLayout.addComponent(new Label("Período"));
        hLayout.addComponent(new Label("Horário"));
        hLayout.addComponent(new Label("Ação"));
        
        vLayout.addComponent(hLayout);
        
        for (Turma t : listaTurmas) {
            HorizontalLayout h = new HorizontalLayout();
            h.setSpacing(true);
            h.setImmediate(true);
            h.setWidth("100%");
            h.setStyleName(Reindeer.LAYOUT_WHITE);
            
            Label l = new Label(t.getDescricao() + " - " + t.getDisciplina() + " - " + t.getProfessor() 
                    + " - " + t.getDias_semana() + " - " + t.getPeriodo() + " - " + t.getHorario());
            Button b = new Button("Inscrever");
            b.setData(t.getId());
            
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
            
            h.addComponent(b);
            
            vLayout.addComponent(h);
            vLayout.addComponent(new Label("<hr />", ContentMode.HTML));
        }
        
        return vLayout;
    }
}
