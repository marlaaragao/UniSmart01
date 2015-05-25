/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marla Aragão
 */
public class CursoDao {
    public void insert(Usuario usuario) throws SQLException {
        
        usuario.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into usuario (id, nome_usuario, senha, perfil) "
                + "values (?, ?, ?, ?)");
        
        ps.setInt(1, usuario.getId());
        ps.setString(2, usuario.getNomeUsuario());
        ps.setString(3, usuario.getSenha());
        ps.setInt(4, usuario.getPerfil());

        ps.execute();
    }


    public List<Usuario> select() throws SQLException {
        
        List<Usuario> listaUsuarios = new ArrayList<>();
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("Select * from Usuarios");
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNomeUsuario(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setPerfil(rs.getInt("perfil"));

            listaUsuarios.add(usuario);
        }
        return listaUsuarios;

    }
    
    public Boolean usuarioExiste(Usuario usuario) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select * from usuario where nome_usuario = ? and senha = ? ");
        
        ps.setString(1, usuario.getNomeUsuario());
        ps.setString(2, usuario.getSenha());
        
        ResultSet rs = ps.executeQuery();
        
        return rs.next();

    }

    
    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from usuario ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
}
