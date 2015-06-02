/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template disciplinas, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import views.tecnicos.areas.LayoutCadastroAtuacao;
import views.tecnicos.areas.LayoutAtuacao;
import views.tecnicos.disciplinas.LayoutCadastroDisciplinas;
import views.tecnicos.disciplinas.LayoutDisciplinas;
import views.tecnicos.professores.LayoutCadastroProfessores;
import views.tecnicos.professores.LayoutProfessores;
import views.tecnicos.turmas.LayoutCadastroTurmas;
import views.tecnicos.turmas.LayoutTurmas;

/**
 *
 * @author Marla Aragão
 */
public class LayoutPrincipalTecnico extends VerticalLayout {
    
    private VerticalLayout content;
    private Button btnDisciplinas;
    private MenuBar menubar = new MenuBar();
    
    public LayoutPrincipalTecnico() {
        
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        Label labelTitulo = new Label("<font size=\"3\" color=\"#287ece\"><b>UNISMART 1.0</b></font>"
        , ContentMode.HTML);
        
        addComponent(labelTitulo);
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        Label label = new Label("<font size=\"2\" color=\"#CC3300\"><b>Perfil: Técnico Administrativo</b></font>"
        , ContentMode.HTML);
        
        addComponent(label);
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        content = new VerticalLayout();
        content.setImmediate(true);
        content.setWidth("100%");
        content.setSpacing(true);
        content.setStyleName(Reindeer.LAYOUT_BLUE);
        
        init();
        
        addComponent(content);
    }

    private void init() {
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setImmediate(true);
        hLayout.setSpacing(true);
        
        btnDisciplinas = new Button("Disciplinas");
        btnDisciplinas.setImmediate(true);
        btnDisciplinas.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) { 
                
                content.removeAllComponents();
                
                LayoutDisciplinas l = new LayoutDisciplinas(content);
                content.addComponent(l);
            }
        });
        
        hLayout.addComponent(btnDisciplinas);  
        
        addMenuBar();
        
        LayoutDisciplinas l = new LayoutDisciplinas(content);
        content.addComponent(l);
    }

    public void addMenuBar() {

        menubar.setWidth("100%");

        /**********************************Disciplinas**************************************/
        final MenuBar.MenuItem disciplinas = menubar.addItem("Disciplinas", null);
        disciplinas.addItem("Mostrar Disciplinas", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutDisciplinas l = new LayoutDisciplinas(content);
                content.addComponent(l);
            }
        });
        disciplinas.addSeparator();

        disciplinas.addItem("Nova Disciplina", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutCadastroDisciplinas l = new LayoutCadastroDisciplinas(LayoutCadastroDisciplinas.Operacao.INCLUIR, content);
                content.addComponent(l);
            }
        });

        /****************************Turmas*******************************/
        final MenuBar.MenuItem turmas = menubar.addItem("Turmas", null);
        turmas.addItem("Mostrar Turmas", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutTurmas l = new LayoutTurmas(content);
                content.addComponent(l);
            }
        });
        turmas.addSeparator();

        turmas.addItem("Nova Turma", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutCadastroTurmas l = new LayoutCadastroTurmas(LayoutCadastroTurmas.Operacao.INCLUIR, content);
                content.addComponent(l);
            }
        });

        /*******************************Areas**********************************/
        final MenuBar.MenuItem areas = menubar.addItem("Areas", null);
        areas.addItem("Mostrar Areas de Atuação", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutAtuacao l = new LayoutAtuacao(content);
                content.addComponent(l);
            }
        });
        areas.addSeparator();

        areas.addItem("Nova Atuação", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutCadastroAtuacao l = new LayoutCadastroAtuacao(LayoutCadastroAtuacao.Operacao.INCLUIR, content);
                content.addComponent(l);
            }
        });

        addComponent(menubar);
        
        /******************************Professores***************************************/
        final MenuBar.MenuItem professores = menubar.addItem("Professores", null);
        professores.addItem("Mostrar Professores", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutProfessores l = new LayoutProfessores(content);
                content.addComponent(l);
            }
        });
        professores.addSeparator();

        professores.addItem("Novo Professor", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutCadastroProfessores l = new LayoutCadastroProfessores(LayoutCadastroProfessores.Operacao.INCLUIR, content);
                content.addComponent(l);
            }
        });


        addComponent(menubar);
    }
}
