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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marla Aragão
 */
public class UsuarioDao {
    
    public void insert(Usuario usuario) throws SQLException  {
        
        usuario.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
                ("insert into usuario (id, nome_usuario, senha, perfil) "
                        + "values (?, ?, ?, ?)");;
        try {

            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNomeUsuario());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getPerfil());

            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
            }
        }
        
        
    }


    public Usuario select(String nome_usuario) {
        
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement
              ("Select * from Usuario where nome_usuario = ?");

            ps.setString(1, nome_usuario);

            ResultSet rs = ps.executeQuery();

            Usuario usuario = null;

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getInt("perfil"));
            }
            return usuario;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Usuario> selectAll() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        String sql = "Select * from usuario ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));

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
