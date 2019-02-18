package DataLayer;

import BusinessLayer.Componente;
import BusinessLayer.Pacote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PacoteDAO {

    public static HashMap<Integer, Pacote> list () throws SQLException {
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Pacote");
        ResultSet rs1;
        PreparedStatement ps1;
        HashMap<Integer,Pacote> list = new HashMap<Integer,Pacote>();
        HashMap<Integer,Componente> componentes = new HashMap<Integer,Componente>();
        
        while (rs.next()) {
            int id = rs.getInt("idPacote");
            String designacao = rs.getString("Designacao");
            String categoria = rs.getString("Categoria");
            double preco = rs.getDouble("Preco");
            componentes = (HashMap<Integer, Componente>) ComponenteDAO.list(id);

            Pacote p = new Pacote(id,designacao,componentes,preco,categoria);
            list.put(id,p);
        }
        
        return list;
    }
    
    public static Pacote devolvePacote (int idPacote) throws SQLException {
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Pacote WHERE Pacote.idPacote = " + idPacote);
        HashMap<Integer,Componente> componentes = new HashMap<Integer,Componente>();
        
        rs.next(); 
        int id = rs.getInt("idPacote");
        String designacao = rs.getString("Designacao");
        String categoria = rs.getString("Categoria");
        double preco = rs.getDouble("Preco");
        componentes = (HashMap<Integer, Componente>) ComponenteDAO.list(id);

        Pacote p = new Pacote(id,designacao,componentes,preco,categoria);
        
        return p;
    }
}