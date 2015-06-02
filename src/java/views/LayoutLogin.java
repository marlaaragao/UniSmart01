/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import entidade.Usuario;
import java.sql.SQLException;
import persistencia.AlunoDao;
import persistencia.ProfessorDao;
import persistencia.UsuarioDao;

/**
 *
 * @author Marla Aragão
 */
public class LayoutLogin extends VerticalLayout {

    private VerticalLayout content;
    private TextField usuario;
    private PasswordField senha;
    private Button btnEntrar;
    
    public LayoutLogin() {
        setImmediate(true);
        setSpacing(true);
        setMargin(true);
        
        Label label = new Label("<font size=\"3\" color=\"#287ece\"><b>UNISMART 1.0</b></font>"
        , ContentMode.HTML);
//        label.setStyleName("center");
        
        addComponent(label);
        addComponent(new Label("<hr />", ContentMode.HTML));
        init();
    }
    
    private void init() {
        
        usuario = new TextField("Usuario");
        usuario.setImmediate(true);
        usuario.setWidth("300px");
        usuario.focus();
        usuario.addShortcutListener(new ShortcutListener(null, ShortcutAction.KeyCode.ENTER, null) {
            
            @Override
            public void handleAction(Object sender, Object target) {
                senha.focus();
            }
        });
        
        this.addComponent(usuario);
        
        btnEntrar = new Button("Entrar");
        btnEntrar.addStyleName("default");
        btnEntrar.setImmediate(true);
        btnEntrar.setWidth("300px");
        btnEntrar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (efetuarLogin(usuario.getValue(), senha.getValue())) {
                    Usuario user = new UsuarioDao().select(usuario.getValue());
                    
                    switch(user.getPerfil()) {
                        case 1:
                            LayoutPrincipalTecnico lt = new LayoutPrincipalTecnico();
                            UI.getCurrent().setContent(lt);
                            VaadinSession.getCurrent().setAttribute("usuario", user.getId());
                            break;
                        case 2:
                            LayoutPrincipalProfessor lp = new LayoutPrincipalProfessor();
                            UI.getCurrent().setContent(lp);
                            VaadinSession.getCurrent().setAttribute("aluno", null);
                            {
                                try {
                                    VaadinSession.getCurrent().setAttribute("professor", new ProfessorDao().selectByUser(user.getId()).getId());
                                    VaadinSession.getCurrent().setAttribute("usuario", user.getId());
                                } catch (SQLException ex) {
                                }
                            }
                            break;
                        case 3:
                            LayoutPrincipalAluno la = new LayoutPrincipalAluno();
                            UI.getCurrent().setContent(la);
                            VaadinSession.getCurrent().setAttribute("professor", null);
                            {
                                try {
                                    VaadinSession.getCurrent().setAttribute("aluno", new AlunoDao().selectByUser(user.getId()).getId());
                                    VaadinSession.getCurrent().setAttribute("usuario", user.getId());
                                } catch (SQLException ex) {
                                }
                            }
                            break;
                    }
                } else {
                    usuario.setComponentError(new UserError("Usuario e/ou senha incorreto(s)"));
                }
            }
        });
        
        senha = new PasswordField("Senha");
        senha.setImmediate(true);
        senha.setWidth("300px");
        senha.addShortcutListener(new ShortcutListener(null, ShortcutAction.KeyCode.ENTER, null) {
            
            @Override
            public void handleAction(Object sender, Object target) {
                btnEntrar.focus();
            }
        });
        
        this.addComponent(senha);
        this.addComponent(btnEntrar);
        
        Label label = new Label("*Digite usuario e senha e clique em Entrar.");
        label.setWidth("40%");
                
        this.addComponent(label);

    }
    
    private boolean efetuarLogin(String usuario, String senha) {
        Usuario user = new Usuario();
        user.setNomeUsuario(usuario);
        user.setSenha(senha);
        
        UsuarioDao dao = new UsuarioDao();
        try {
            if (dao.usuarioExiste(user)) {
                return true; 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
