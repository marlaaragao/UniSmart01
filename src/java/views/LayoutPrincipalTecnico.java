/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template disciplinas, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import views.disciplinas.LayoutCadastroDisciplinas;

import views.disciplinas.LayoutDisciplinas;

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
                
                LayoutDisciplinas l = new LayoutDisciplinas();
                content.addComponent(l);
            }
        });
        
        hLayout.addComponent(btnDisciplinas);  
        
        addMenuBar();
    }

    public void addMenuBar() {

        menubar.setWidth("100%");
        final MenuBar.MenuItem disciplinas = menubar.addItem("Disciplinas", null);
        disciplinas.addItem("Mostrar Disciplinas", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutDisciplinas l = new LayoutDisciplinas();
                content.addComponent(l);
            }
        });
        disciplinas.addSeparator();

        disciplinas.addItem("Nova Disciplina", new Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutCadastroDisciplinas l = new LayoutCadastroDisciplinas(LayoutCadastroDisciplinas.Operacao.INCLUIR);
                content.addComponent(l);
            }
        });

        final MenuBar.MenuItem edit = menubar.addItem("Turmas", null);
//        edit.addItem("Undo", menuCommand);
//        edit.addItem("Redo", menuCommand).setEnabled(false);
//        edit.addSeparator();

        edit.addSeparator();

        final MenuBar.MenuItem view = menubar.addItem("Areas", null);
//        view.addItem("Show/Hide Status Bar", menuCommand);
//        view.addItem("Customize Toolbar...", menuCommand);
//        view.addSeparator();
//
//        view.addItem("Actual Size", menuCommand);
//        view.addItem("Zoom In", menuCommand);
//        view.addItem("Zoom Out", menuCommand);

        addComponent(menubar);
        
        final MenuBar.MenuItem professores = menubar.addItem("Professores", null);
//        view.addItem("Show/Hide Status Bar", menuCommand);
//        view.addItem("Customize Toolbar...", menuCommand);
//        view.addSeparator();
//
//        view.addItem("Actual Size", menuCommand);
//        view.addItem("Zoom In", menuCommand);
//        view.addItem("Zoom Out", menuCommand);

        addComponent(menubar);
    }
}
