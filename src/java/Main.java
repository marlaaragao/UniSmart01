
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import conexao.CreateTables;
import java.sql.SQLException;
import views.LayoutLogin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marla Aragão
 */
public class Main extends UI {
    
    @Override
    protected void init(final VaadinRequest request) {
        
        LayoutLogin mainContent = new LayoutLogin();
        this.setContent(mainContent);

        try {
            new CreateTables().createTables();
        } catch (SQLException ex) {
        }
        
        UI.getCurrent().getPage().setTitle("UniSmart 1.0");

    }

}
