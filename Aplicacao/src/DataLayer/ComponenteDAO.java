package DataLayer;

import BusinessLayer.Componente;
import BusinessLayer.Pacote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponenteDAO {

    public static Map<Integer,Componente> list () throws SQLException {
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Componente");
        ResultSet rs1;
        ResultSet rs2;
        HashMap<Integer,Componente> list = new HashMap<Integer,Componente>();
        
        
        while (rs.next()) {
            ArrayList<Integer> incomp = new ArrayList<Integer>();
            ArrayList<Integer> obrig = new ArrayList<Integer>();
            
            int id = rs.getInt("idComponente");
            String designacao = rs.getString("Designacao");
            double preco = rs.getDouble("Preco");
            String categoria = rs.getString("Categoria");
            int stock = rs.getInt("Stock");

            rs1 = c.createStatement().executeQuery("SELECT * FROM ComponenteObrigatorio WHERE ComponenteObrigatorio.Componente_id = " + id); //ps1.executeQuery();
            while(rs1.next()){
            	int idO = rs1.getInt("idCompObrig");
            	obrig.add(idO);
            }

            rs2 = c.createStatement().executeQuery("SELECT * FROM ComponenteIncompativel WHERE ComponenteIncompativel.Componente_idComponente = " + id);//ps2.executeQuery();
            while(rs2.next()){
            	int idI = rs2.getInt("idCompIncomp");
            	incomp.add(idI);
            }

            Componente a = new Componente(id, designacao, preco, stock, categoria, incomp, obrig);
            list.put(id,a);

        }
        
        return list;
    }

    
    public static Map<Integer,Componente> list (Integer idP) throws SQLException {
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Componente WHERE Componente.Pacote_id = ?");
        ps.setInt(1,idP);
        ResultSet rs = ps.executeQuery();
        ResultSet rs1;
        ResultSet rs2;
        PreparedStatement ps1;
        PreparedStatement ps2;
        HashMap<Integer,Componente> list = new HashMap<Integer,Componente>();
        ArrayList<Integer> incomp = new ArrayList<Integer>();
        ArrayList<Integer> obrig = new ArrayList<Integer>();
        
        while (rs.next()) {
            int id = rs.getInt("idComponente");
            String designacao = rs.getString("Designacao");
            double preco = rs.getDouble("Preco");
            String categoria = rs.getString("Categoria");
            int stock = rs.getInt("Stock");

            rs1 = c.createStatement().executeQuery("SELECT * FROM ComponenteObrigatorio WHERE ComponenteObrigatorio.Componente_id = " + id);//ps1.executeQuery();
            while(rs1.next()){
            	int idO = rs1.getInt("idCompObrig");
            	obrig.add(idO);
            }

            rs2 = c.createStatement().executeQuery("SELECT * FROM ComponenteIncompativel WHERE ComponenteIncompativel.Componente_idComponente = " + id);//ps2.executeQuery();
            while(rs2.next()){
            	int idI = rs2.getInt("idCompIncomp");
            	incomp.add(idI);
            }

            Componente a = new Componente(id, designacao, preco, stock, categoria, incomp, obrig);
            list.put(id,a);
        }
        
        return list;
    }
    
    public static Componente devolveComponente (int id) throws SQLException {
        
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Componente WHERE Componente.idComponente = " + id);
        ArrayList<Integer> incomp = new ArrayList<Integer>();
        ArrayList<Integer> obrig = new ArrayList<Integer>();
        
        rs.next();
        int idComp = id;
        String designacao = rs.getString("Designacao");
        double preco = rs.getDouble("Preco");
        String categoria = rs.getString("Categoria");
        int stock = rs.getInt("Stock");

        ResultSet rs1 = c.createStatement().executeQuery("SELECT * FROM ComponenteObrigatorio WHERE ComponenteObrigatorio.Componente_id = " + id);
        while(rs1.next()){
            int idO = rs1.getInt("idCompObrig");
            obrig.add(idO);
        }

        ResultSet rs2 = c.createStatement().executeQuery("SELECT * FROM ComponenteIncompativel WHERE ComponenteIncompativel.Componente_idComponente = " + id);//ps2.executeQuery();
        while(rs2.next()){
            int idI = rs2.getInt("idCompIncomp");
            incomp.add(idI);
        }

        Componente a = new Componente(idComp, designacao, preco, stock, categoria, incomp, obrig);
    
        
        return a;
        
    }
    
    public static void atualizaStock (int idComponente, int nStock) throws SQLException {
        
        Connection c = Connect.connect();
        PreparedStatement ps = c
                .prepareStatement("UPDATE Componente SET stock = " 
                                  + "(?)"
                                  + " WHERE idComponente = " + "(?)");

        
        ps.setInt(1, nStock);
        ps.setInt(2, idComponente);

        ps.executeUpdate();
        c.close();
        
    }

}     