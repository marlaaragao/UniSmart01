/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
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
        ("insert into turma (id, descricao, max_alunos, professor, dias_semana, professor, periodo, horario, ativa) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        ps.setInt(1, getProximoCodigo());
        ps.setString(2, turma.getDescricao());
        ps.setInt(3, turma.getMax_alunos());
        ps.setInt(4, turma.getProfessor());
        ps.setString(5, turma.getDias_semana());
        ps.setInt(6, turma.getProfessor());
        ps.setString(7, turma.getPeriodo());
        ps.setInt(8, turma.getHorario());
        ps.setInt(9, turma.isAtiva() ? 1 : 0);

        ps.execute();
    }


    public Turma select(int id) throws SQLException {
        
        Turma turma = null;
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
            ("Select * from Turmas where id = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            turma = new Turma();
            turma.setId(rs.getInt("id"));
            turma.setDescricao(rs.getString("descricao"));
            turma.setMax_alunos(rs.getInt("max_alunos"));
            turma.setDisciplina(rs.getInt("disciplina"));
            turma.setProfessor(rs.getInt("professor"));
            turma.setDias_semana(rs.getString("dias_semana"));
            turma.setPeriodo(rs.getString("periodo"));
            turma.setHorario(rs.getInt("horario"));
            turma.setAtiva((rs.getInt("ativa") == 1));

        }
        return turma;

    }
    
    public List<Turma> selectAll() throws SQLException {
        List<Turma> listaTurmas = new ArrayList<>();
        
        String sql = "Select * from turma ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Turma turma = new Turma();
            turma.setId(rs.getInt("id"));
            turma.setDescricao(rs.getString("descricao"));
            turma.setMax_alunos(rs.getInt("max_alunos"));
            turma.setDisciplina(rs.getInt("disciplina"));
            turma.setProfessor(rs.getInt("professor"));
            turma.setDias_semana(rs.getString("dias_semana"));
            turma.setPeriodo(rs.getString("periodo"));
            turma.setHorario(rs.getInt("horario"));
            turma.setAtiva((rs.getInt("ativa") == 1));

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
    
    public void update(Turma turma)  {
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement("Update turma set "
                    + "descricao = ?, max_alunos = ?, disciplina = ?, professor = ?, dias_semana = ?"
                    + ", periodo = ?, , horario = ?, ativa = ? where id = ?");

            ps.setString(1, turma.getDescricao());
            ps.setInt(2, turma.getMax_alunos());
            ps.setInt(3, turma.getDisciplina());
            ps.setInt(4, turma.getProfessor());
            ps.setString(5, turma.getDias_semana());
            ps.setString(6, turma.getPeriodo());
            ps.setInt(7, turma.getHorario());
            ps.setInt(8, turma.getId());
            ps.setInt(9, turma.isAtiva() ? 1 : 0);
            
            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
