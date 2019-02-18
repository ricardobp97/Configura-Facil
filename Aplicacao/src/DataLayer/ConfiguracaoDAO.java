package DataLayer;

import BusinessLayer.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ConfiguracaoDAO {

    public static void guardarConfiguracao (Configuracao a) throws SQLException {
        
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Configuracao VALUES" + "(?,?)");
        ps.setInt(1, a.getIdConfig());
        ps.setString(2, a.getNifCliente());
        ps.executeUpdate();
        
        for(Integer i: a.getListaComponentes().keySet()){
        	ps = c.prepareStatement("INSERT INTO ConfigComp VALUES" + "(?,?)");
        	ps.setInt(1, a.getIdConfig());
        	ps.setInt(2, i);
        	ps.executeUpdate();
        }

        for(Integer i: a.getListaPacks().keySet()){
        	ps = c.prepareStatement("INSERT INTO ConfigPack VALUES" + "(?,?)");
        	ps.setInt(1, a.getIdConfig());
        	ps.setInt(2, i);
        	ps.executeUpdate();
        }

        c.close();
    }
    
    public static void removerConfiguracao (Configuracao a) throws SQLException {
        
        Connection c = Connect.connect();
        PreparedStatement ps = c.prepareStatement("DELETE FROM ConfigComp WHERE ConfigComp.Configuracao_id = ?");
        ps.setInt(1, a.getIdConfig());
        ps.executeUpdate();
        
        PreparedStatement ps1 = c.prepareStatement("DELETE FROM ConfigPack WHERE ConfigPack.Configuracao_id = ?");
        ps1.setInt(1, a.getIdConfig());
        ps1.executeUpdate();
        
        PreparedStatement ps2 = c.prepareStatement("DELETE FROM Configuracao WHERE Configuracao.idConfiguracao = ?");
        ps2.setInt(1, a.getIdConfig());
        ps2.executeUpdate();
        c.close();
    }

    public static HashMap<Integer, Configuracao> list () throws SQLException {
        Connection c = Connect.connect();
        ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Configuracao");
        
        
        HashMap<Integer, Configuracao> list = new HashMap<>();
        HashMap<Integer,Componente> componentes;
        HashMap<Integer,Pacote> pacotes;
        
        while (rs.next()) {
            componentes = new HashMap<Integer,Componente>();
            pacotes = new HashMap<Integer,Pacote>();
            
            int id = rs.getInt("idConfiguracao");
            String nif = rs.getString("NIF_Cliente");
            
            ResultSet rs1 = c.createStatement().executeQuery("SELECT * FROM ConfigComp WHERE ConfigComp.Configuracao_id =" + id);
            ResultSet rs2 = c.createStatement().executeQuery("SELECT * FROM ConfigPack WHERE ConfigPack.Configuracao_id =" + id);
            
            while (rs1.next()) {
                int idComp = rs1.getInt("Componente_id");
                Componente comp = ComponenteDAO.devolveComponente(idComp);
                componentes.put(idComp, comp);  
            }
            
            while (rs2.next()) {
                int idPack = rs2.getInt("Pacote_id");
                Pacote pack = PacoteDAO.devolvePacote(idPack);
                pacotes.put(idPack, pack);  
            }
              
            Configuracao a = new Configuracao(id, nif, componentes, pacotes);
            list.put(id,a);
        }
        
        return list;
    }
    
}