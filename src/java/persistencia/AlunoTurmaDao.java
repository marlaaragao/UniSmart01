/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import conexao.DBConnection;
import entidade.AlunoTurma;
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
    
    public AlunoTurma select(int id_turma, int id_aluno) throws SQLException {
        
        AlunoTurma alunoTurma = null;
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("select * from aluno_turma where id_turma = ? and id_aluno = ? ");
        
        ps.setInt(1, id_turma);
        ps.setInt(2, id_aluno);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            alunoTurma = new AlunoTurma();
            alunoTurma.setId_aluno(rs.getInt("id_aluno"));
            alunoTurma.setId_turma(rs.getInt("id_turma"));
            alunoTurma.setNota_aluno(rs.getDouble("nota_aluno"));
            alunoTurma.setNum_faltas(rs.getInt("num_faltas"));
            
            return alunoTurma;
        }
        
        return null;
    }

    public void update(AlunoTurma aluno_turma) throws SQLException {
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("update aluno_turma set nota_aluno = ?, num_faltas = ? where id_aluno = ? and id_turma = ? ");
        
       
        ps.setDouble(1, aluno_turma.getNota_aluno());
        ps.setInt(2, aluno_turma.getNum_faltas());
        ps.setInt(3, aluno_turma.getId_aluno());
        ps.setInt(4, aluno_turma.getId_turma());
        
        ps.execute();
    }
}
