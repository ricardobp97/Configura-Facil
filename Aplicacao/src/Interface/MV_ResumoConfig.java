/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import BusinessLayer.Componente;
import BusinessLayer.ConfiguraFacil;
import BusinessLayer.Configuracao;
import BusinessLayer.Pacote;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class MV_ResumoConfig extends javax.swing.JFrame {

    private ConfiguraFacil cfacil;
    /**
     * Creates new form ResumoConfig
     */
    public MV_ResumoConfig(ConfiguraFacil cfacil) {
        Double pt;
        
        initComponents();
        this.cfacil=cfacil;
        tablePacotes();
        tableComponentes();
        precoTotal.setText(valorTotal().toString());
        precoTotal.setEditable(false);
    }
    
    private void tablePacotes() {
        
        DefaultTableModel model = (DefaultTableModel) tablePacotes.getModel();
        model.setRowCount(0);
        for(int i: this.cfacil.getListaPackConfig()) {
            Object rowData[] = new Object[2];
            rowData[0] = this.cfacil.getPacotes().get(i).getDesignacaoPack();
            rowData[1] = this.cfacil.getPacotes().get(i).getPrecoPack();
            model.addRow(rowData);
            if(i>2) { //Se for um pack extra
                for(Componente c: this.cfacil.getPacotes().get(i).getListaCompPack().values()) {
                    rowData[0] = "        ".concat(c.getDesignacaoComp());
                    rowData[1] = "";
                    model.addRow(rowData); 
                }
            }
        }  
    }
    
    private void tableComponentes() {
        
        DefaultTableModel model = (DefaultTableModel) tableComponentes.getModel();
        model.setRowCount(0);
        for(int i: this.cfacil.getListaCompConfig()) {
            Object rowData[] = new Object[2];
            rowData[0] = this.cfacil.getComponentes().get(i).getDesignacaoComp();
            rowData[1] = this.cfacil.valorPagarComponente(i);
            model.addRow(rowData);
        }
    }
    
    private Double valorTotal() {
        
        Double valor = 0.0;
        boolean pertence;
        
        for(int i: this.cfacil.getListaPackConfig())
            valor += this.cfacil.getPacotes().get(i).getPrecoPack();
        
        for(int i: this.cfacil.getListaCompConfig()) {
            pertence = false;
            
            for(int p: this.cfacil.getListaPackConfig())
                if (this.cfacil.getPacotes().get(p).getListaCompPack().containsKey(i))
                    pertence = true;
            
            if(!pertence)
                valor += this.cfacil.getComponente(i).getPrecoComp();
        }
        return valor;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePacotes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableComponentes = new javax.swing.JTable();
        Cancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        precoTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pacotes");

        jLabel2.setText("Resumo Configuração");

        jLabel3.setText("Componentes");

        tablePacotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Designação", "Preço"
            }
        ));
        jScrollPane1.setViewportView(tablePacotes);

        tableComponentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Designação", "Preço"
            }
        ));
        jScrollPane2.setViewportView(tableComponentes);

        Cancelar.setText("Fechar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Preço Total (Euros):");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(Cancelar)
                        .addGap(98, 98, 98))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(precoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelar)
                        .addGap(153, 153, 153))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelarActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField precoTotal;
    private javax.swing.JTable tableComponentes;
    private javax.swing.JTable tablePacotes;
    // End of variables declaration//GEN-END:variables
}
