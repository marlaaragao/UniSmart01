/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DerbyUtils;

/**
 *
 * @author Marla Aragão
 */
public class CreateTables {

    private Connection connection;

    public CreateTables() throws SQLException {
        this.connection = DBConnection.getInstance();
    }

    public void createTables() throws SQLException {
        
        try {
            createTableCurso();
            createTableAluno();
            createTableAtuacao();
            createTableProfessor();
            createTableDisciplina();
            createTableTurma();
            createTableAlunoTurma();
            createTablePerfil();
            createTableUsuario();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void createTableCurso() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  curso ("
                    + "    id int not null primary key,"
                    + "    nome varchar(100) not null"
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela curso criada com sucesso!");
            
            insertTableCurso();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabela ja existe");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableAluno() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  aluno ("
                    + "    id int not null primary key,"
                    + "    matricula int not null,"
                    + "    cpf varchar(14) not null,"
                    + "    nome varchar(150) not null,"
                    + "    curso int not null,"
                    + "    FOREIGN KEY (curso) "
                    + "    REFERENCES curso(id)"
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela aluno criada com sucesso!");
            
            insertTableAluno();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabela ja existe");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableAtuacao() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  atuacao ("
                    + "    id int not null primary key,"
                    + "    descricao varchar(50) not null"
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela atuacao criada com sucesso!");
            
            insertTableAtuacao();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabelas ja existem");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableProfessor() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  professor ("
                    + "    id int not null primary key,"
                    + "    cpf varchar(14) not null,"
                    + "    nome varchar(150) not null,"
                    + "    atuacao int not null,"
                    + "    FOREIGN KEY (atuacao) "
                    + "    REFERENCES atuacao(id) "
                    + ")ENGINE=INNODB;";
                   
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela professor criada com sucesso!!");
            
            insertTableProfessor();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabela ja existe");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableDisciplina() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  disciplina ("
                    + "    id int not null primary key,"
                    + "    descricao varchar(50) not null,"
                    + "    ano int not null,"
                    + "    ativa bit not null,"
                    + "    atuacao int not null,"
                    + "    FOREIGN KEY (atuacao) REFERENCES atuacao(id) "
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela disciplina criada com sucesso!!");
            
            insertTableDisciplina();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabela ja existe");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableTurma() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  turma ("
                    + "    id int not null primary key,"
                    + "    descricao varchar(50) not null,"
                    + "    disciplina int not null,"
                    + "    professor int not null,"
                    + "    max_alunos int not null,"
                    + "    dias_semana varchar(18) not null, /*SegTerQuaQuiSexSab*/"
                    + "    periodo varchar(6) not null, /*M V N*/"
                    + "    horario int not null,  /*1 2 3 4 5*/ "
                    + "    ativa bit not null,  "
                    + "    FOREIGN KEY (disciplina) "
                    + "    REFERENCES disciplina(id), "
                    + "    FOREIGN KEY (professor) "
                    + "    REFERENCES professor(id) "
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela turma criada com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabelas ja existem");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTableAlunoTurma() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  aluno_turma ("
                    + "    id_aluno int not null,"
                    + "    id_turma int not null,"
                    + "    nota_aluno decimal(2) not null,"
                    + "    num_faltas int not null,"
                    + "    FOREIGN KEY (id_aluno) "
                    + "    REFERENCES aluno(id),"
                    + "    FOREIGN KEY (id_turma) "
                    + "    REFERENCES turma(id) "
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela aluno_turma criada com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabelas ja existem");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void createTablePerfil() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  perfil ("
                    + "    id int not null primary key,"
                    + "    descricao varchar(50) not null"
                    + ")ENGINE=INNODB;";
                    
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela perfil criada com sucesso!");
            
            insertTablePerfil();
        
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabelas ja existem");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void insertTablePerfil() throws SQLException {
        
        String sql1 = "insert into perfil (id, descricao) values (1, \"Tecnico\");";
        
        String sql2 = "insert into perfil (id, descricao) values (2, \"Professor\");";
        
        String sql3 = "insert into perfil (id, descricao) values (3, \"Aluno\");";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de perfil inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void createTableUsuario() throws SQLException {
        
        String sql = "CREATE TABLE IF NOT EXISTS  usuario ("
                    + "    id int not null primary key,"
                    + "    nome_usuario varchar(50) not null unique key,"
                    + "    senha varchar(50) not null,"
                    + "    perfil int not null,"
                    + "    FOREIGN KEY (perfil) REFERENCES perfil(id)"
                    + ")";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            System.out.println("Tabela usuario criada com sucesso!");
            
            insertTableUsuario();
            
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("Tabela ja existe");
            } else {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    private void insertTableUsuario() throws SQLException {
        
        String sql1 = "insert into usuario (id, nome_usuario, senha, perfil) values (1, \"marla\", \"1234\", 3);";
        
        String sql2 = "insert into usuario (id, nome_usuario, senha, perfil) values (2, \"ayrton\", \"1234\", 2);";
        
        String sql3 = "insert into usuario (id, nome_usuario, senha, perfil) values (3, \"adailton\", \"1234\", 1);";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de usuarios inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void insertTableAtuacao() throws SQLException {
        
        String sql1 = "insert into atuacao (id, descricao) values (1, \"redes\");";
        
        String sql2 = "insert into atuacao (id, descricao) values (2, \"processos\");";
        
        String sql3 = "insert into atuacao (id, descricao) values (3, \"programacao\");";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de atuacao inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void insertTableDisciplina() throws SQLException {
        
        String sql1 = "insert into disciplina (id, descricao, ano, ativa, atuacao) values (1, \"Redes de computadores\", 2015, 1, 1);";
        
        String sql2 = "insert into disciplina (id, descricao, ano, ativa, atuacao) values (2, \"Qualidade de software\", 2015, 1, 2);";
        
        String sql3 = "insert into disciplina (id, descricao, ano, ativa, atuacao) values (3, \"Introducao a programacao\", 2014, 0, 3);";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de disciplinas inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void insertTableProfessor() throws SQLException {
        
        String sql1 = "insert into professor (id, cpf, nome, atuacao) values (1, \"02961714176\", \"Ricardo Teles\", 1);";
        
        String sql2 = "insert into professor (id, cpf, nome, atuacao) values (2, \"34324234244\", \"Adailton Araujo\", 2);";
        
        String sql3 = "insert into professor (id, cpf, nome, atuacao) values (3, \"67653535453\", \"Bruno Silvestre\", 3);";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de professores inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void insertTableCurso() throws SQLException {
        
        String sql1 = "insert into curso (id, nome) values (1, \"Ciencias da Computacao\");";
        
        String sql2 = "insert into curso (id, nome) values (2, \"Engenharia de Software\");";
        
        String sql3 = "insert into curso (id, nome) values (3, \"Sistemas de Informacao\");";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de cursos inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    private void insertTableAluno() throws SQLException {
        
        String sql1 = "insert into aluno (id, matricula, cpf, nome, curso) values (1, 123456, \"12312312323\", \"Maria\", 1);";
        
        String sql2 = "insert into aluno (id, matricula, cpf, nome, curso) values (2, 333333, \"34655345534\", \"Joao\", 2);";
        
        String sql3 = "insert into aluno (id, matricula, cpf, nome, curso) values (3, 444444, \"23423423434\", \"Luana\", 3);";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql1);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql2);
            stmt.execute();
            stmt.close();
            
            stmt = connection.prepareStatement(sql3);
            stmt.execute();
            stmt.close();
            
            System.out.println("Dados de alunos inseridos com sucesso!");
        } catch (SQLException e) {
             if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                 System.out.println("");
            } else {
                e.printStackTrace();
            }
        }
    }

}
