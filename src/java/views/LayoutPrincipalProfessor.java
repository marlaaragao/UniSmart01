/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import views.professores.LayoutTurmasLecionadas;

/**
 *
 * @author Marla Aragão
 */
public class LayoutPrincipalProfessor extends VerticalLayout {
    
    private VerticalLayout content;
    private MenuBar menubar = new MenuBar();
    private Button logout = new Button("Sair");
    
    public LayoutPrincipalProfessor() {
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setImmediate(true);
        hLayout.setWidth("100%");
        
        Label labelTitulo = new Label("<font size=\"3\" color=\"#287ece\"><b>UNISMART 1.0</b></font>"
        , ContentMode.HTML);
        
        hLayout.addComponent(labelTitulo);
        hLayout.addComponent(logout);
        logout.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().setContent(new LayoutLogin());
            }
        });
        
        hLayout.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
        
        addComponent(hLayout);
        
        addComponent(new Label("<hr />", ContentMode.HTML));
        
        Label label = new Label("<font size=\"2\" color=\"#CC3300\"><b>Perfil: Aluno</b></font>"
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
               
        addMenuBar();
        LayoutTurmasLecionadas l = new LayoutTurmasLecionadas(content);
        content.addComponent(l);
    }

    public void addMenuBar() {

        menubar.setWidth("100%");

        /****************************Turmas*******************************/
        final MenuBar.MenuItem turmas = menubar.addItem("Turmas", null);
        turmas.addItem("Mostrar Turmas Abertas", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                content.removeAllComponents();
                
                LayoutTurmasLecionadas l = new LayoutTurmasLecionadas(content);
                content.addComponent(l);
            }
        });
        turmas.addSeparator();
        
        addComponent(menubar);
        
    }
    
}
