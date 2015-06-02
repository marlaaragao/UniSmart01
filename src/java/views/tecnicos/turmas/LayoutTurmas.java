/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tecnicos.turmas;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import entidade.Turma;
import java.util.List;
import persistencia.DisciplinaDao;
import persistencia.ProfessorDao;
import persistencia.TurmaDao;
import views.LayoutPrincipalTecnico;

/**
 *
 * @author Marla Aragão
 */
public class LayoutTurmas extends VerticalLayout {
    
    private Table tabela;
    private Button btnMenuPrincipal;
    private Button btnAlterar;
    private Button btnNovo;
    private TextField search;
    private VerticalLayout content;
    
    public LayoutTurmas(VerticalLayout content) {
        
        this.content = content;
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);

        Label label = new Label("<font size=\"2\" color=\"#287ece\"><b>Turmas</b></font>"
        , ContentMode.HTML);
        
        addComponent(label);
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        init();
        
    }
    
    private void init() {
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setImmediate(true);
        hLayout.setSpacing(true);
        
        btnMenuPrincipal = new Button("Menu Principal");
        btnMenuPrincipal.setImmediate(true);
        btnMenuPrincipal.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                LayoutPrincipalTecnico l = new LayoutPrincipalTecnico();
                UI.getCurrent().setContent(l);
            }
        });
        
        btnAlterar = new Button("Alterar");
        btnAlterar.setImmediate(true);
        btnAlterar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Object rowId = tabela.getValue();
                if (rowId == null) {
                    Notification.show("", "Escolha um turma para alterar", Notification.Type.WARNING_MESSAGE);
                } else {
                    
                    Turma turma = (Turma) rowId;
                    
                    LayoutCadastroTurmas l = new LayoutCadastroTurmas(LayoutCadastroTurmas.Operacao.ALTERAR, content);
                    l.loadDados(turma);
                    
                    content.removeAllComponents();
                    content.addComponent(l);
                }
            }
        });
        
        hLayout.addComponent(btnAlterar);
        
        btnNovo = new Button("Nova Turma");
        btnNovo.setImmediate(true);
        btnNovo.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                LayoutCadastroTurmas l = new LayoutCadastroTurmas(LayoutCadastroTurmas.Operacao.INCLUIR, content);
                content.removeAllComponents();
                content.addComponent(l);
            }
        });
        
        hLayout.addComponent(btnNovo);
                
        search = new TextField();
        search.setImmediate(true);
        search.setWidth("100%");
        search.setInputPrompt("Pesquisa");
        search.addTextChangeListener(new FieldEvents.TextChangeListener() {

            Filter filtro = null;
            
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                Container.Filterable f = (Container.Filterable) tabela.getContainerDataSource();
                
                if (filtro != null)
                    f.removeContainerFilter(filtro);
                
                filtro = new SimpleStringFilter("Descricao", event.getText(),
                                                true, false);
                f.addContainerFilter(filtro);
            }
        });
        

        tabela = buildTabela();
        
        this.addComponent(btnMenuPrincipal);
        this.addComponent(hLayout);
        this.addComponent(search);
        this.addComponent(tabela);
        
        carregarTabelaTurmas();
        
    }
    
    private Table buildTabela() {
        Table tabela_ = new Table();

        tabela_.setSelectable(true);
        tabela_.setImmediate(true);
        tabela_.setMultiSelect(false);
        tabela_.setSizeFull();
      
        tabela_.addContainerProperty("Id", Integer.class, null);
        tabela_.addContainerProperty("Descricao", String.class, null);
        tabela_.addContainerProperty("Disciplina", String.class, null);
        tabela_.addContainerProperty("Professor", String.class, null);
        tabela_.addContainerProperty("MaxAlunos", Integer.class, null);
        tabela_.addContainerProperty("DiasSemana", String.class, null);
        tabela_.addContainerProperty("Periodo", String.class, null);
        tabela_.addContainerProperty("Horario", Integer.class, null);
        tabela_.addContainerProperty("Ativa", String.class, null);
        
        tabela_.setColumnHeaders(new String[]{"Id", "Descrição", "Disciplina", "Professor", "Máx. Alunos"
        , "Dias da Semana", "Período", "Horário", "Ativa"});
        tabela_.setColumnWidth("Codigo", 60);

        tabela_.setVisibleColumns(new Object[]{"Id", "Descricao", "Disciplina", "Professor", "MaxAlunos"
        , "DiasSemana", "Periodo", "Horario", "Ativa"});
    
        return tabela_;
    }
    
    private void carregarTabelaTurmas() {
        tabela.removeAllItems();     
        try {
            
            List<Turma> listaTurmas = null;

            listaTurmas = new TurmaDao().selectAll();
            
            for (Turma p : listaTurmas) {
                Item item = tabela.addItem(p);
                item.getItemProperty("Id").setValue(p.getId());
                item.getItemProperty("Descricao").setValue(p.getDescricao());
                item.getItemProperty("Disciplina").setValue(new DisciplinaDao().select(p.getDisciplina()).getDescricao());
                item.getItemProperty("Professor").setValue(new ProfessorDao().select(p.getProfessor()).getNome());
                item.getItemProperty("DiasSemana").setValue(p.getDias_semana());
                item.getItemProperty("Periodo").setValue(p.getPeriodo());
                item.getItemProperty("Horario").setValue(p.getHorario());
                item.getItemProperty("MaxAlunos").setValue(p.getMax_alunos());
                item.getItemProperty("Ativa").setValue(p.isAtiva() ? "Sim" : "Não");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
