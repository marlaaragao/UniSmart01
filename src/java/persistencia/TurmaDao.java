/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Turma;
import entidade.Turma;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marla Aragão
 */
public class TurmaDao {
    public void insert(Turma turma) throws SQLException {
        
        turma.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into turma () "
                + "values (?, ?, ?, ?)");
        
        ps.setInt(1, turma.getId());


        ps.execute();
    }


    public List<Turma> select() throws SQLException {
        
        List<Turma> listaTurmas = new ArrayList<>();
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("Select * from Turmas");
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Turma turma = new Turma();
            turma.setId(rs.getInt("id"));
            

            listaTurmas.add(turma);
        }
        return listaTurmas;

    }
    
    public List<Turma> selectAll() throws SQLException {
        List<Turma> listaTurmas = new ArrayList<>();
        
        String sql = "Select * from turma ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Turma turma = new Turma();
            turma.setId(rs.getInt("id"));

            listaTurmas.add(turma);
        }
        return listaTurmas;

    }
    
    public Boolean turmaExiste(Turma turma) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select * from turma where id = ? ");
        
        ps.setInt(1, turma.getId());
        
        ResultSet rs = ps.executeQuery();
        
        return rs.next();

    }

    
    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from turma ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
}
