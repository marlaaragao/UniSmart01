/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import conexao.DBConnection;
import entidade.AlunoTurma;
import entidade.Turma;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marla Aragão
 */
public class AlunoTurmaDao {
    
    public void insert(AlunoTurma turma) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into aluno_turma (id_aluno, id_turma, nota_aluno, num_faltas) "
                + "values (?, ?, ?, ?)");
        
        ps.setInt(1, turma.getId_aluno());
        ps.setInt(2, turma.getId_turma());
        ps.setDouble(3, turma.getNota_aluno());
        ps.setInt(4, turma.getNum_faltas());
        
        ps.execute();
    }
    
    public boolean alunoInscrito(int id_turma, int id_aluno) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("select * from aluno_turma where id_turma = ? and id_aluno = ? ");
        
        ps.setInt(1, id_turma);
        ps.setInt(2, id_aluno);
        
        ResultSet rs = ps.executeQuery();
        
        return rs.next();
    }
}
