/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import conexao.DBConnection;
import entidade.Aluno;
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
public class AlunoDao {
    
    public void insert(Aluno aluno) throws SQLException {
        
        aluno.setId(getProximoCodigo());
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
         ("insert into aluno (id, nome, cpf, matricula, curso, usuario) "
                + "values (?, ?, ?, ?, ?, ?)");
        
        ps.setInt(1, aluno.getId());
        ps.setString(2, aluno.getNome());
        ps.setString(3, aluno.getCpf());
        ps.setInt(4, aluno.getMatricula());
        ps.setInt(5, aluno.getCurso());
        ps.setInt(6, 0);
        
        ps.executeUpdate();
        
        UsuarioDao dao = new UsuarioDao();
        int usuario = dao.insert(new Usuario(dao.getProximoCodigo()
                , aluno.getNome() + aluno.getCpf().substring(0, 5), aluno.getCpf(), 3));
        
        aluno.setUsuario(usuario);
        this.update(aluno);
    }

    public void delete(Aluno aluno) throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement
            ("Delete from aluno where id = ?");
        ps.setInt(1, aluno.getId());
        ps.execute();

    }

    public void update(Aluno aluno) throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Update aluno set "
                + "nome = ?, cpf = ?, matricula = ?, curso = ?, usuario = ? where id = ?");
        ps.setString(1, aluno.getNome());
        ps.setString(2, aluno.getCpf());
        ps.setInt(3, aluno.getMatricula());
        ps.setInt(4, aluno.getCurso());
        ps.setInt(5, aluno.getUsuario());
        ps.setInt(6, aluno.getId());
        ps.execute();

    }

    public List<Aluno> selectAll() throws SQLException {
        List<Aluno> listaAlunos = new ArrayList<>();
        
        String sql = "Select * from aluno";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setMatricula(rs.getInt("matricula"));
            aluno.setCurso(rs.getInt("curso"));
            aluno.setUsuario(rs.getInt("usuario"));
            listaAlunos.add(aluno);
        }
        return listaAlunos;

    }
    
    public List<Aluno> selectAllTurma(int turma) throws SQLException {
        List<Aluno> listaAlunos = new ArrayList<>();
        
        String sql = "Select * from aluno inner join aluno_turma on (aluno_turma.id_aluno = aluno.id ) "
                + " where id_turma = ? ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        ps.setInt(1, turma);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setMatricula(rs.getInt("matricula"));
            aluno.setCurso(rs.getInt("curso"));
            aluno.setUsuario(rs.getInt("usuario"));
            listaAlunos.add(aluno);
        }
        return listaAlunos;

    }
    
    public List<Aluno> selectAllCurso(int curso) throws SQLException {
        List<Aluno> listaAlunos = new ArrayList<>();
        
        String sql = "Select * from aluno where curso = ? ";
        
        PreparedStatement ps = DBConnection.getInstance().prepareStatement(sql);
        ps.setInt(1, curso);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setMatricula(rs.getInt("matricula"));
            aluno.setCurso(rs.getInt("curso"));
            aluno.setUsuario(rs.getInt("usuario"));
            listaAlunos.add(aluno);
        }
        return listaAlunos;

    }

    public Aluno select(int codigo) throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select * from aluno where id = ?");
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        Aluno aluno = null;
        if(rs.next()){
            aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setMatricula(rs.getInt("matricula"));
            aluno.setCurso(rs.getInt("curso"));
            aluno.setUsuario(rs.getInt("usuario"));
        }
        return aluno;
    }
    
    public Aluno selectByUser(int usuario) throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select * from aluno where usuario = ?");
        ps.setInt(1, usuario);
        ResultSet rs = ps.executeQuery();
        Aluno aluno = null;
        if(rs.next()){
            aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setMatricula(rs.getInt("matricula"));
            aluno.setCurso(rs.getInt("curso"));
            aluno.setUsuario(rs.getInt("usuario"));
        }
        return aluno;
    }
    
    private int getProximoCodigo() throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().prepareStatement("Select max(id) as maximo "
                + " from aluno ");

        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getInt("maximo") + 1;
        }
        return 1;
    }
    
}
