/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Professor;
import entidade.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marla Aragão
 */
public class ProfessorDao {
    public void insert(Professor professor) throws SQLException {

        professor.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into professor (id, nome, cpf, atuacao, usuario) "
                + "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        
        ps.setInt(1, professor.getId());
        ps.setString(2, professor.getNome());
        ps.setString(3, professor.getCpf());
        ps.setInt(4, professor.getAtuacao());
        ps.setInt(5, 0);
        
        ps.executeUpdate();
        
        UsuarioDao dao = new UsuarioDao();
        int usuario = dao.insert(new Usuario(dao.getProximoCodigo()
                , professor.getNome() + professor.getCpf().substring(0, 4), professor.getCpf(), 2));
        
        professor.setUsuario(usuario);
        this.update(professor);
        
    }


    public Professor select(int id) throws SQLException {

        Professor professor = null;
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("Select * from Professor where id = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            professor = new Professor();
            professor.setId(rs.getInt("id"));
            professor.setNome(rs.getString("nome"));
            professor.setCpf(rs.getString("cpf"));
            professor.setAtuacao(rs.getInt("atuacao"));
            professor.setUsuario(rs.getInt("usuario"));
        }
        return professor;

    }
    
    public Professor selectByUser(int usuario) throws SQLException {

        Professor professor = null;
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("Select * from Professor where usuario = ?");
        ps.setInt(1, usuario);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            professor = new Professor();
            professor.setId(rs.getInt("id"));
            professor.setNome(rs.getString("nome"));
            professor.setCpf(rs.getString("cpf"));
            professor.setAtuacao(rs.getInt("atuacao"));
            professor.setUsuario(rs.getInt("usuario"));
        }
        return professor;

    }
    
    public List<Professor> selectAll() throws SQLException {
        List<Professor> listaProfessors = new ArrayList<>();
        
        String sql = "Select * from professor ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Professor professor = new Professor();
            professor.setId(rs.getInt("id"));
            professor.setNome(rs.getString("nome"));
            professor.setCpf(rs.getString("cpf"));
            professor.setAtuacao(rs.getInt("atuacao"));
            professor.setUsuario(rs.getInt("usuario"));

            listaProfessors.add(professor);
        }
        return listaProfessors;

    }

    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from professor ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
    
    public void update(Professor professor)  {
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement("Update professor set "
                    + "nome = ?, cpf = ?, atuacao = ?, usuario = ? where id = ?");

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getCpf());
            ps.setInt(3, professor.getAtuacao());
            ps.setInt(4, professor.getUsuario());
            ps.setInt(5, professor.getId());
            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
