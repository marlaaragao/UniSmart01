/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Disciplina;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marla Aragão
 */
public class DisciplinaDao {
    public void insert(Disciplina disciplina) throws SQLException {
        
        disciplina.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into disciplina (id, descricao, ano, atuacao, ativa) values (?, ?, ?, ?, ?)");

        ps.setInt(1, getProximoCodigo());
        ps.setString(2, disciplina.getDescricao());
        ps.setInt(3, disciplina.getAno());
        ps.setInt(4, disciplina.getAtuacao());
        ps.setInt(5, disciplina.isAtiva() ? 1 : 0);

        ps.execute();
    }


    public Disciplina select(int id) {
        
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement
                ("Select * from Disciplinas where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
        
        Disciplina disciplina = null;
        
        if (rs.next()) {
            disciplina = new Disciplina();
            disciplina.setId(rs.getInt("id"));
            disciplina.setDescricao(rs.getString("descricao"));
            disciplina.setAno(rs.getInt("ano"));
            disciplina.setAtuacao(rs.getInt("atuacao"));
            disciplina.setAtiva((rs.getInt("ativa") != 0));

        }
        return disciplina;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    public List<Disciplina> selectAll() {
        List<Disciplina> listaDisciplinas = new ArrayList<>();
        
        String sql = "Select * from disciplina ";
        
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Disciplina disciplina = new Disciplina();
            disciplina.setId(rs.getInt("id"));
            disciplina.setDescricao(rs.getString("descricao"));
            disciplina.setAno(rs.getInt("ano"));
            disciplina.setAtuacao(rs.getInt("atuacao"));
            disciplina.setAtiva((rs.getInt("ativa") != 0));

            listaDisciplinas.add(disciplina);
        }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaDisciplinas;

    }
    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from disciplina ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
    
    public void update(Disciplina disciplina)  {
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement("Update disciplina set "
                    + "descricao = ?, ano = ?, atuacao = ?, ativa = ? where id = ?");

            ps.setString(1, disciplina.getDescricao());
            ps.setDouble(2, disciplina.getAno());
            ps.setDouble(3, disciplina.getAtuacao());
            ps.setBoolean(4, disciplina.isAtiva());
            ps.setInt(4, disciplina.getId());
            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
