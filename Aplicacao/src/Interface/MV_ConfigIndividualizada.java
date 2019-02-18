/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Interface.*;
import BusinessLayer.Componente;
import BusinessLayer.ConfiguraFacil;
import BusinessLayer.Configuracao;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.CheckBox;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;


public class MV_ConfigIndividualizada extends JFrame implements Observer {

    private ConfiguraFacil cfacil;
    
    /**
     * Creates new form Configuracao_Indiv
     */
    public MV_ConfigIndividualizada(ConfiguraFacil cfacil) {
        initComponents();
        this.cfacil=cfacil; 
        
        preencheTabelaPack(1,(DefaultTableModel) caract1Tab.getModel());
        preencheTabelaPack(2,(DefaultTableModel) caract2Tab.getModel());
        preencheTabelaPack(3,(DefaultTableModel) tblPackSport.getModel());
        preencheTabelaPack(4,(DefaultTableModel) tblPackConfort.getModel());
        pvpModelo1.setText(Double.toString(this.cfacil.getPacotes().get(1).getPrecoPack()));
        pvpModelo2.setText(Double.toString(this.cfacil.getPacotes().get(2).getPrecoPack()));
        txtPrecoSport.setText(Double.toString(this.cfacil.getPacotes().get(3).getPrecoPack()));
        txtPrecoConfort.setText(Double.toString(this.cfacil.getPacotes().get(4).getPrecoPack()));
        
        carregaEstado();
    }
    
    private void preencheTabelaPack(int idPack, DefaultTableModel tbl) {
        
        for(Componente c: this.cfacil.getPacotes().get(idPack).getListaCompPack().values()) {
            Object rowData[] = new Object[1];
            rowData[0] = c.getDesignacaoComp();
            tbl.addRow(rowData); 
        } 
    }
    
    public ConfiguraFacil getCFacil() {
        return this.cfacil;
    }
    
    public void carregaEstado() {
        
        clearOptions();
        
        for(int i: this.cfacil.getListaCompConfig()) {
            switch (i) {
                case 3: pintura3.setSelected(true);
                        break;
                case 4: pintura4.setSelected(true);
                        break;
                case 5: pintura5.setSelected(true);
                        break;
                case 6: pintura6.setSelected(true);
                        break;
                case 7: jantes7.setSelected(true);
                        break;
                case 8: jantes8.setSelected(true);
                        break;
                case 9: jantes9.setSelected(true);
                        break;
                case 10: estofos10.setSelected(true);
                         break;
                case 11: estofos11.setSelected(true);
                         break;
                case 12: estofos12.setSelected(true);
                         break;
                case 13: di13.setSelected(true);
                         break;
                case 14: de14.setSelected(true);
                         break;
                case 15: di15.setSelected(true);
                         break;
                case 16: di16.setSelected(true);
                         break;
                case 17: di17.setSelected(true);
                         break;
                case 18: di18.setSelected(true);
                         break;
                case 19: de19.setSelected(true);
                         break;
                case 20: de20.setSelected(true);
                         break;
                default: break;
            }
        }
        
        for(int i: this.cfacil.getListaPackConfig()) {
            switch (i) {
                case 1: base1.setSelected(true);
                        break;
                case 2: base2.setSelected(true);
                        break;
                case 3: pack3.setSelected(true);
                        break;
                case 4: pack4.setSelected(true);
                        break;
                default: break;
            }
        }
    }
    
    private void clearOptions() {
        pintura3.setSelected(false);
        pintura4.setSelected(false);
        pintura5.setSelected(false);
        pintura6.setSelected(false);
        jantes7.setSelected(false);
        jantes8.setSelected(false);
        jantes9.setSelected(false);
        estofos10.setSelected(false);
        estofos11.setSelected(false);
        estofos12.setSelected(false);
        
        di13.setSelected(false);
        de14.setSelected(false);
        di15.setSelected(false);
        di16.setSelected(false);
        di17.setSelected(false);
        di18.setSelected(false);
        de19.setSelected(false);
        de20.setSelected(false);

        base1.setSelected(false);
        base2.setSelected(false);
        pack3.setSelected(false);
        pack4.setSelected(false);   
    }
    
    private void checkComponente(int comp) {
         if(!base1.isSelected() && !base2.isSelected()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor comece por escolher um modelo base.", "Modelo Base", 0);
            clearOptions();
        }
        else {
            cfacil.escolheComponentes(comp);
            if((cfacil.getComponentesInserir().size() + cfacil.getComponentesRemover().size()) > 0) {
                MV_AlteracoesConfig alteracoesConfig = new MV_AlteracoesConfig(this);
                alteracoesConfig.setTitle("Alterações a Efetuar");
                alteracoesConfig.setVisible(true);
            }
            else {
                cfacil.atualizaListaComp();
                cfacil.atualizaListaPacks();
                carregaEstado();
            }
        }
    }
    
    private void uncheckComponente(int comp) {
        if(!base1.isSelected() && !base2.isSelected()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor comece por escolher um modelo base.", "Modelo Base", 0);
            clearOptions();
        }
        else {
            String compQueObriga = cfacil.getCompQueObriga(comp);
            if(!compQueObriga.equals("")){
                javax.swing.JOptionPane.showMessageDialog(this, "Este é um componente obrigatório do componente " + compQueObriga + ".", "Remoção interrompida.", 0);
            }
            else {
                //se e um componente base
                if(comp<=12) {
                    //devolve o id do componente que pertence a categoria daquele que está a ser desselecionado
                    //e faz parte dos componentes do Pacote Base selecionado
                    int idCompBase;
                    if(cfacil.getListaPackConfig().contains(1)) {
                        idCompBase = cfacil.getIdComponenteBase(1,comp);
                    }
                    else{
                        idCompBase = cfacil.getIdComponenteBase(2,comp);
                    }

                    cfacil.escolheComponentes(idCompBase);
                    cfacil.atualizaListaComp();
                }
                else {
                    cfacil.removeComponente(comp);
                }
            }
            carregaEstado();
        }
    }
    
    private void checkPacote(int pack) {
        if(!base1.isSelected() && !base2.isSelected()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor comece por escolher um modelo base.", "Modelo Base", 0);
            clearOptions();
        }
        else {
            cfacil.escolhePacotes(pack);
            if((cfacil.getComponentesInserir().size() + cfacil.getComponentesRemover().size()) > 0) {
                MV_AlteracoesConfig alteracoesConfig = new MV_AlteracoesConfig(this);
                alteracoesConfig.setTitle("Alterações a Efetuar");
                alteracoesConfig.setVisible(true);
            }
            else {
                cfacil.atualizaListaComp();
                cfacil.atualizaListaPacks();
                carregaEstado();
            }
        }
    }
    
    private void uncheckPacote(int pack) {
        for(Componente c: cfacil.getPacotes().get(pack).getListaCompPack().values()) {
            int idC = c.getIdComponente();

            //se e um componente base
            if(idC<=12) {
                //devolve o id do componente que pertence a categoria daquele que está a ser desselecionado
                //e faz parte dos componentes do Pacote Base selecionado
                int idCompBase;
                if(cfacil.getListaPackConfig().contains(1)) {
                    idCompBase = cfacil.getIdComponenteBase(1,idC);
                }
                else{
                    idCompBase = cfacil.getIdComponenteBase(2,idC);
                }

                cfacil.escolheComponentes(idCompBase);
                cfacil.atualizaListaComp();
            }
            else {
                cfacil.removeComponente(idC);
            }
            carregaEstado();
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        configuracao = new javax.swing.JTabbedPane();
        tabbed_base = new javax.swing.JPanel();
        modelo1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        caract1Tab = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        pvpModelo1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        caract2Tab = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        pvpModelo2 = new javax.swing.JTextField();
        base1 = new javax.swing.JCheckBox();
        base2 = new javax.swing.JCheckBox();
        tabbed_pack = new javax.swing.JPanel();
        pack3 = new javax.swing.JCheckBox();
        pack4 = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPackSport = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtPrecoSport = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrecoConfort = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPackConfort = new javax.swing.JTable();
        tabbed_compBasicos = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pintura3 = new javax.swing.JCheckBox();
        pintura4 = new javax.swing.JCheckBox();
        pintura5 = new javax.swing.JCheckBox();
        pintura6 = new javax.swing.JCheckBox();
        jantes7 = new javax.swing.JCheckBox();
        jantes8 = new javax.swing.JCheckBox();
        jantes9 = new javax.swing.JCheckBox();
        estofos10 = new javax.swing.JCheckBox();
        estofos11 = new javax.swing.JCheckBox();
        estofos12 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        di13 = new javax.swing.JCheckBox();
        di15 = new javax.swing.JCheckBox();
        di16 = new javax.swing.JCheckBox();
        di17 = new javax.swing.JCheckBox();
        di18 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        de14 = new javax.swing.JCheckBox();
        de19 = new javax.swing.JCheckBox();
        de20 = new javax.swing.JCheckBox();
        resumo1 = new javax.swing.JButton();
        cancelar_Button = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        modelo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Modelo1.png"))); // NOI18N
        modelo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Modelo2.jpg"))); // NOI18N
        jLabel7.setText("       Imagem Modelo B");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        caract1Tab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Características"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(caract1Tab);

        jLabel10.setText("P.V.P. (Euros)");

        pvpModelo1.setEditable(false);

        caract2Tab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Características"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(caract2Tab);

        jLabel5.setText("P.V.P. (Euros");

        pvpModelo2.setEditable(false);

        base1.setText("Modelo 1");
        base1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base1ActionPerformed(evt);
            }
        });

        base2.setText("Modelo 2");
        base2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabbed_baseLayout = new javax.swing.GroupLayout(tabbed_base);
        tabbed_base.setLayout(tabbed_baseLayout);
        tabbed_baseLayout.setHorizontalGroup(
            tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_baseLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabbed_baseLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pvpModelo1))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(base1)
                    .addComponent(modelo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(base2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabbed_baseLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pvpModelo2)))
                .addGap(46, 46, 46))
        );
        tabbed_baseLayout.setVerticalGroup(
            tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modelo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(base2)
                    .addComponent(base1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabbed_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pvpModelo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(pvpModelo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        configuracao.addTab("Modelo Base/Motor", tabbed_base);

        pack3.setText("Sport");
        pack3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack3ActionPerformed(evt);
            }
        });

        pack4.setText("Confort");
        pack4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack4ActionPerformed(evt);
            }
        });

        tblPackSport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componentes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblPackSport);

        jLabel2.setText("P.V.P. (Euros): ");

        txtPrecoSport.setEditable(false);

        jLabel6.setText("P.V.P. (Euros): ");

        txtPrecoConfort.setEditable(false);

        tblPackConfort.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componentes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblPackConfort);

        javax.swing.GroupLayout tabbed_packLayout = new javax.swing.GroupLayout(tabbed_pack);
        tabbed_pack.setLayout(tabbed_packLayout);
        tabbed_packLayout.setHorizontalGroup(
            tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_packLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabbed_packLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecoSport)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecoConfort, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabbed_packLayout.createSequentialGroup()
                        .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pack3))
                        .addGap(71, 71, 71)
                        .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pack4)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(207, 207, 207))
        );
        tabbed_packLayout.setVerticalGroup(
            tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_packLayout.createSequentialGroup()
                .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabbed_packLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPrecoSport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabbed_packLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pack4)
                            .addComponent(pack3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabbed_packLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPrecoConfort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        configuracao.addTab("Pacotes", tabbed_pack);

        jLabel4.setText("PINTURA");

        jLabel8.setText("JANTES");

        jLabel3.setText("ESTOFOS");

        pintura3.setText("Preto");
        pintura3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pintura3ActionPerformed(evt);
            }
        });

        pintura4.setText("Branco");
        pintura4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pintura4ActionPerformed(evt);
            }
        });

        pintura5.setText("Cinzento Mate");
        pintura5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pintura5ActionPerformed(evt);
            }
        });

        pintura6.setText("Azul Brilhante");
        pintura6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pintura6ActionPerformed(evt);
            }
        });

        jantes7.setText("Jantes 17\"");
        jantes7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jantes7ActionPerformed(evt);
            }
        });

        jantes8.setText("Jantes 18\"");
        jantes8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jantes8ActionPerformed(evt);
            }
        });

        jantes9.setText("Jantes Desportivas 19''");
        jantes9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jantes9ActionPerformed(evt);
            }
        });

        estofos10.setText("Estofos em Tecido");
        estofos10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estofos10ActionPerformed(evt);
            }
        });

        estofos11.setText("Estofos em Pele");
        estofos11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estofos11ActionPerformed(evt);
            }
        });

        estofos12.setText("Estofos em Pele Artico");
        estofos12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estofos12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabbed_compBasicosLayout = new javax.swing.GroupLayout(tabbed_compBasicos);
        tabbed_compBasicos.setLayout(tabbed_compBasicosLayout);
        tabbed_compBasicosLayout.setHorizontalGroup(
            tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                .addGroup(tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jantes7)
                        .addGap(18, 18, 18)
                        .addComponent(jantes8)
                        .addGap(18, 18, 18)
                        .addComponent(jantes9))
                    .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                                .addComponent(pintura3)
                                .addGap(18, 18, 18)
                                .addComponent(pintura4)
                                .addGap(18, 18, 18)
                                .addComponent(pintura5)
                                .addGap(18, 18, 18)
                                .addComponent(pintura6))))
                    .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(estofos10)
                        .addGap(18, 18, 18)
                        .addComponent(estofos11)
                        .addGap(18, 18, 18)
                        .addComponent(estofos12))
                    .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8))
                    .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)))
                .addContainerGap(233, Short.MAX_VALUE))
        );
        tabbed_compBasicosLayout.setVerticalGroup(
            tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbed_compBasicosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pintura3)
                    .addComponent(pintura4)
                    .addComponent(pintura5)
                    .addComponent(pintura6))
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jantes7)
                    .addComponent(jantes8)
                    .addComponent(jantes9))
                .addGap(35, 35, 35)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(tabbed_compBasicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estofos10)
                    .addComponent(estofos11)
                    .addComponent(estofos12))
                .addContainerGap(238, Short.MAX_VALUE))
        );

        configuracao.addTab("Componentes Básicos", tabbed_compBasicos);

        di13.setText("Teto de Abrir");
        di13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                di13ActionPerformed(evt);
            }
        });

        di15.setText("Aquecimento Auxiliar");
        di15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                di15ActionPerformed(evt);
            }
        });

        di16.setText("Ar Condicionado Automatico");
        di16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                di16ActionPerformed(evt);
            }
        });

        di17.setText("Head-up Display");
        di17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                di17ActionPerformed(evt);
            }
        });

        di18.setText("Volante Multifuncoes");
        di18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                di18ActionPerformed(evt);
            }
        });

        jLabel9.setText("DETALHES INTERIORES");

        jLabel11.setText("DETALHES EXTERIORES");

        de14.setText("Vidros Escurecidos");
        de14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                de14ActionPerformed(evt);
            }
        });

        de19.setText("Gancho Para Reboque");
        de19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                de19ActionPerformed(evt);
            }
        });

        de20.setText("Alarme");
        de20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                de20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(de14)
                        .addGap(18, 18, 18)
                        .addComponent(de19)
                        .addGap(18, 18, 18)
                        .addComponent(de20))
                    .addComponent(jLabel9)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(di15)
                            .addComponent(di18)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(di13)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(di16)
                                .addGap(18, 18, 18)
                                .addComponent(di17)))))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(di15)
                    .addComponent(di16)
                    .addComponent(di17))
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(di13)
                    .addComponent(di18))
                .addGap(73, 73, 73)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(de14)
                    .addComponent(de19)
                    .addComponent(de20))
                .addContainerGap(224, Short.MAX_VALUE))
        );

        configuracao.addTab("Componentes Opcionais", jPanel7);

        resumo1.setText("Ver Resumo");
        resumo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumo1ActionPerformed(evt);
            }
        });

        cancelar_Button.setText("Cancelar");
        cancelar_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar_ButtonActionPerformed(evt);
            }
        });

        btnConfirmar.setText("Confirmar Configuração");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(configuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resumo1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnConfirmar)
                .addGap(43, 43, 43)
                .addComponent(cancelar_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configuracao, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelar_Button)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resumo1)
                        .addComponent(btnConfirmar)))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pintura5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pintura5ActionPerformed
        if(pintura5.isSelected())
            checkComponente(5);
        else
            uncheckComponente(5);
    }//GEN-LAST:event_pintura5ActionPerformed

    private void base2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base2ActionPerformed
        // TODO add your handling code here:
        cfacil.escolheModeloBase(2);
        carregaEstado();
        base1.setEnabled(false);
        base2.setEnabled(false);
    }//GEN-LAST:event_base2ActionPerformed

    private void cancelar_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelar_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelar_ButtonActionPerformed

    private void resumo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumo1ActionPerformed
        
        MV_ResumoConfig resumo = new MV_ResumoConfig (this.cfacil);
        resumo.setTitle("Resumo Configuração");
        resumo.setVisible(true);
    }//GEN-LAST:event_resumo1ActionPerformed

    private void pintura3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pintura3ActionPerformed
        if(pintura3.isSelected())
            checkComponente(3);
        else
            uncheckComponente(3);
    }//GEN-LAST:event_pintura3ActionPerformed

    private void pintura4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pintura4ActionPerformed
        if(pintura4.isSelected())
            checkComponente(4);
        else
            uncheckComponente(4);
    }//GEN-LAST:event_pintura4ActionPerformed

    private void pintura6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pintura6ActionPerformed
        if(pintura6.isSelected())
            checkComponente(6);
        else
            uncheckComponente(6);
    }//GEN-LAST:event_pintura6ActionPerformed

    private void jantes7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jantes7ActionPerformed
        if(jantes7.isSelected())
            checkComponente(7);
        else
            uncheckComponente(7);
    }//GEN-LAST:event_jantes7ActionPerformed

    private void jantes8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jantes8ActionPerformed
        if(jantes8.isSelected())
            checkComponente(8);
        else
            uncheckComponente(8);
    }//GEN-LAST:event_jantes8ActionPerformed

    private void jantes9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jantes9ActionPerformed
        if(jantes9.isSelected())
            checkComponente(9);
        else
            uncheckComponente(9);
    }//GEN-LAST:event_jantes9ActionPerformed

    private void estofos10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estofos10ActionPerformed
        if(estofos10.isSelected())
            checkComponente(10);
        else
            uncheckComponente(10);
    }//GEN-LAST:event_estofos10ActionPerformed

    private void estofos11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estofos11ActionPerformed
        if(estofos11.isSelected())
            checkComponente(11);
        else
            uncheckComponente(11);
    }//GEN-LAST:event_estofos11ActionPerformed

    private void estofos12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estofos12ActionPerformed
        if(estofos12.isSelected())
            checkComponente(12);
        else
            uncheckComponente(12);
    }//GEN-LAST:event_estofos12ActionPerformed

    private void di15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_di15ActionPerformed
        if(di15.isSelected())
            checkComponente(15);
        else
            uncheckComponente(15);
    }//GEN-LAST:event_di15ActionPerformed

    private void di16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_di16ActionPerformed
        if(di16.isSelected())
            checkComponente(16);
        else
            uncheckComponente(16);
    }//GEN-LAST:event_di16ActionPerformed

    private void di17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_di17ActionPerformed
        if(di17.isSelected())
            checkComponente(17);
        else
            uncheckComponente(17);
    }//GEN-LAST:event_di17ActionPerformed

    private void di18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_di18ActionPerformed
        if(di18.isSelected())
            checkComponente(18);
        else
            uncheckComponente(18);
    }//GEN-LAST:event_di18ActionPerformed

    private void di13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_di13ActionPerformed
        if(di13.isSelected())
            checkComponente(13);
        else
            uncheckComponente(13);
    }//GEN-LAST:event_di13ActionPerformed

    private void de14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_de14ActionPerformed
        if(de14.isSelected())
            checkComponente(14);
        else
            uncheckComponente(14);
    }//GEN-LAST:event_de14ActionPerformed

    private void de19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_de19ActionPerformed
        if(de19.isSelected())
            checkComponente(19);
        else
            uncheckComponente(19);
    }//GEN-LAST:event_de19ActionPerformed

    private void de20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_de20ActionPerformed
        if(de20.isSelected())
            checkComponente(20);
        else
            uncheckComponente(20);
    }//GEN-LAST:event_de20ActionPerformed

    private void base1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base1ActionPerformed
        cfacil.escolheModeloBase(1);
        carregaEstado();
        base1.setEnabled(false);
        base2.setEnabled(false);
    }//GEN-LAST:event_base1ActionPerformed

    private void pack3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pack3ActionPerformed
            if(pack3.isSelected())
                checkPacote(3);
            else
                uncheckPacote(3);
    }//GEN-LAST:event_pack3ActionPerformed

    private void pack4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pack4ActionPerformed
        if(pack4.isSelected())
                checkPacote(4);
            else
                uncheckPacote(4);
    }//GEN-LAST:event_pack4ActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        MV_NifCliente nif = new MV_NifCliente(cfacil);
        nif.setTitle("NIF Cliente");
        nif.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnConfirmarActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox base1;
    private javax.swing.JCheckBox base2;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JButton cancelar_Button;
    private javax.swing.JTable caract1Tab;
    private javax.swing.JTable caract2Tab;
    private javax.swing.JTabbedPane configuracao;
    private javax.swing.JCheckBox de14;
    private javax.swing.JCheckBox de19;
    private javax.swing.JCheckBox de20;
    private javax.swing.JCheckBox di13;
    private javax.swing.JCheckBox di15;
    private javax.swing.JCheckBox di16;
    private javax.swing.JCheckBox di17;
    private javax.swing.JCheckBox di18;
    private javax.swing.JCheckBox estofos10;
    private javax.swing.JCheckBox estofos11;
    private javax.swing.JCheckBox estofos12;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JCheckBox jantes7;
    private javax.swing.JCheckBox jantes8;
    private javax.swing.JCheckBox jantes9;
    private javax.swing.JLabel modelo1;
    private javax.swing.JCheckBox pack3;
    private javax.swing.JCheckBox pack4;
    private javax.swing.JCheckBox pintura3;
    private javax.swing.JCheckBox pintura4;
    private javax.swing.JCheckBox pintura5;
    private javax.swing.JCheckBox pintura6;
    private javax.swing.JTextField pvpModelo1;
    private javax.swing.JTextField pvpModelo2;
    private javax.swing.JButton resumo1;
    private javax.swing.JPanel tabbed_base;
    private javax.swing.JPanel tabbed_compBasicos;
    private javax.swing.JPanel tabbed_pack;
    private javax.swing.JTable tblPackConfort;
    private javax.swing.JTable tblPackSport;
    private javax.swing.JTextField txtPrecoConfort;
    private javax.swing.JTextField txtPrecoSport;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        carregaEstado();
    }
}
