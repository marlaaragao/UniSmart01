/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Professor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ("insert into professor  "
                + "values (?, ?, ?, ?)");
        
        ps.setInt(1, professor.getId());


        ps.execute();
    }


    public List<Professor> select() throws SQLException {
        
        List<Professor> listaProfessors = new ArrayList<>();
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("Select * from Professor");
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Professor professor = new Professor();
            professor.setId(rs.getInt("id"));


            listaProfessors.add(professor);
        }
        return listaProfessors;

    }
    
    public List<Professor> selectAll() throws SQLException {
        List<Professor> listaProfessors = new ArrayList<>();
        
        String sql = "Select * from professor ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Professor professor = new Professor();
            professor.setId(rs.getInt("id"));

            listaProfessors.add(professor);
        }
        return listaProfessors;

    }
    
    public Boolean professorExiste(Professor professor) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select * from professor where  ");
        
        ps.setInt(1, professor.getId());

        
        ResultSet rs = ps.executeQuery();
        
        return rs.next();

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
}
