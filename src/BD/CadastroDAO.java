package BD;

import Classes.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.omg.CORBA.SetOverrideType;

public class CadastroDAO {
    public void salvarCadastro(Login l){
        String sql = "INSERT INTO login (nome, data_nascimento, endereco, login, senha) values (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, l.nome);
            pstm.setString(2, l.data_nascimento);
            pstm.setString(3, l.endereco);
            pstm.setString(4, l.login);
            pstm.setString(5, l.senha);

            pstm.execute();  
            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro cadastrar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro cadastrar, encerrar conexão: "+e);
            }
        }

    }

    public boolean autenticaUsuarioBoleano(Login login) throws Exception{
        String sql = "Select * from login where login = ?  and senha = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        boolean resp = false;

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.login);
            pstm.setString(2, login.senha);

            ResultSet result = pstm.executeQuery();
            if ( result.next() )
                resp = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }   

    public Login autenticaUsuario(Login login) throws Exception{
        String sql = "Select * from login where login = ?  and senha = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        Login resp = new Login();

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.login);
            pstm.setString(2, login.senha);

            ResultSet result = pstm.executeQuery();
            while ( result.next()){
                resp.nome = result.getString("nome");
                resp.login = result.getString("login");
                resp.senha = result.getString("senha");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }        
    
    public Login buscaUsuario(Login login) throws Exception{
        String sql = "Select * from login where login = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        Login resp = new Login();

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.login);

            ResultSet result = pstm.executeQuery();
            while ( result.next()){
                resp.nome = result.getString("nome");
                resp.data_nascimento = result.getString("data_nascimento");
                resp.endereco = result.getString("endereco");
                resp.login = result.getString("login");
                resp.senha = result.getString("senha");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }     
    
    public void atualizaLogin(Login login){
        String sql = "UPDATE login SET nome = ?, data_nascimento = ?, endereco = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.criarConexao();

            if ( login.senha == null  ) 
                sql += " Where login = ? ";
            else 
                sql += ", senha = ? Where login = ? ";                    
            
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.nome);
            pstm.setString(2, login.data_nascimento);
            pstm.setString(3, login.endereco);
            
            if ( login.senha == null ) {
                pstm.setString(4, login.login);            
            } else {
                pstm.setString(4, login.senha);
                pstm.setString(5, login.login);                                                        
            }          
            
            pstm.execute();  
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro atualizar, encerrar conexão: "+e);
            }
        }    
    }
    
    public void deleteLogin(Login l){
        String sql = "DELETE FROM login WHERE login = ?";
        
        Connection  conn = null;
        PreparedStatement pstm = null;
      
        try {
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, l.login);
            
            pstm.execute();
           
            JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso !!");
        }  catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao deletar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro deletar, encerrar conexão: "+e);
            }
        }
    }    
}
