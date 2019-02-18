package DataLayer;

import BusinessLayer.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuncionarioDAO {
    
    public static void guardarFuncionario (Funcionario a) throws SQLException {
        
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Funcionario VALUES" + "( ?,?,?)");
        ps.setString(1, a.getUsername());
        ps.setString(2, a.getNome());
        ps.setString(3, a.getPassword());
        ps.executeUpdate();
        c.close();
    }
    
    public static void removerFuncionario (Funcionario a) throws SQLException {
        
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("DELETE FROM Funcionario WHERE Funcionario.Username = " + "(?)");
        ps.setString(1, a.getUsername());
        ps.executeUpdate();
        c.close();
    }
   
    public static HashMap<String, Funcionario> list () throws SQLException {
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Funcionario");
        HashMap<String, Funcionario> list = new HashMap<String, Funcionario>();
        
        while (rs.next()) {
            String Username = rs.getString("Username");
            String Nome = rs.getString("Nome");
            String Password = rs.getString("Password");
            Funcionario a = new Funcionario(Username, Password, Nome);
            list.put(Username,a);
        }
        
        return list;
    }

    public static boolean verificaLogin (String user, String pass) throws SQLException {
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Funcionario WHERE Funcionario.Username = ? AND Funcionario.Password = ? ");
        ps.setString(1, user);
        ps.setString(2, pass);
        
        ResultSet rs = null;
        rs = ps.executeQuery();

        boolean check = false;
        
        if (rs.next()) check = true;
        
        return check;
    }
}
