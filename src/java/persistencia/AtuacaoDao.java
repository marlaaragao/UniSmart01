/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Atuacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marla Aragão
 */
public class AtuacaoDao {
    public void insert(Atuacao area) throws SQLException {
        
        area.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
        ("insert into atuacao (id, descricao) "
                + "values (?, ?)");
        
        ps.setInt(1, getProximoCodigo());
        ps.setString(2, area.getDescricao());

        ps.execute();
    }


    public Atuacao select(int id) {
        
        Atuacao area = null;
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement
                ("Select * from atuacao where id = ? ");
            
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                area = new Atuacao();
                area.setId(rs.getInt("id"));
                area.setDescricao(rs.getString("descricao"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return area;

    }
    
    public List<Atuacao> selectAll() {
        List<Atuacao> listaAreas = new ArrayList<>();
        
        String sql = "Select * from atuacao ";
        
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Atuacao area = new Atuacao();
                area.setId(rs.getInt("id"));
                area.setDescricao(rs.getString("descricao"));
                
                listaAreas.add(area);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return listaAreas;

    }
    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from area ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
    
    public void update(Atuacao atuacao)  {
        PreparedStatement ps;
        try {
            ps = DBConnection.getInstance().prepareStatement("Update atuacao set "
                    + "descricao = ? where id = ?");

            ps.setString(1, atuacao.getDescricao());
            ps.setInt(4, atuacao.getId());
            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}
