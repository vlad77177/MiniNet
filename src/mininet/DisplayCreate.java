/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author vlad7
 */
public class DisplayCreate extends javax.swing.JFrame {
    
    private DinamicPanel _dinamicPanel;
    private ArrayList<Integer> _params;
    private NetworkDescription _nd;
    
    private class DinamicPanel extends JScrollPane{
        
        public InnerPanel _innerpanel;
        
        DinamicPanel(ArrayList<Integer> p){
            setLayout(new ScrollPaneLayout());
            setSize(600,300);
            setLocation(25, 200);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            for(int i=0;i<p.size();i++){
                System.out.print(p.get(i));
            }
            _innerpanel=new InnerPanel(p);
            this.setPreferredSize(new Dimension(600,1000));
            add(_innerpanel);
            this.getViewport().add(_innerpanel);
            revalidate();
        }
    }
    
    private class JEmptyPanel extends JPanel{       
        public JEmptyPanel(){
            super();
            this.setPreferredSize(new Dimension(295,30));
        }
        public JEmptyPanel(int width,int height){
            super();
            this.setPreferredSize(new Dimension(width,height));
        }
    }
    
    private class InnerPanel extends JPanel{
        
        public JComboBox _inputs;
        public JComboBox _outputs;
        public JLabel _inputlab;
        public JLabel _outputlab;
        public ArrayList<JLabel> _hidelab;
        public ArrayList<JComboBox> _hidelayers;
        public ArrayList<JTextField> _inputdescs;
        public ArrayList<JTextField> _inputunits;
        public ArrayList<JTextField> _outputdescs;
        public JLabel _description;
        public JLabel _unit;
        
        public int _elementindex;
        
        public InnerPanel(ArrayList<Integer> p){
            _elementindex=0;
            //Инициализация элементов
            _inputs=new JComboBox();
            _outputs=new JComboBox();
            _inputlab=new JLabel();
            _outputlab=new JLabel();
            _hidelab=new ArrayList();
            _hidelayers=new ArrayList();
            _inputdescs=new ArrayList();
            _inputunits=new ArrayList();
            _outputdescs=new ArrayList();
            _description=new JLabel();
            _unit=new JLabel();
            //Слушатель событий
            ActionListener al=new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    int size=_params.size();
                    _params=new ArrayList();
                    _params.add(Integer.valueOf(_inputs.getSelectedItem().toString()));
                    for(int i=0;i<size-2;i++){
                        _params.add(Integer.valueOf(_hidelayers.get(i).getSelectedItem().toString()));
                    }
                    _params.add(Integer.valueOf(_outputs.getSelectedItem().toString()));
                    drawInterface();
                }
            };
            //Установка шаблона
            FlowLayout fl=new FlowLayout();
            this.setLayout(fl);
            //Сколько скрытых слоев
            int hidelayouts=0;
            for(int i=1;i<p.size()-1;i++){
                hidelayouts++;
            }
            //Начальные значения ComboBox'сов
            for(int i=1;i<10;i++){
                _inputs.addItem(i);
            }
            int inputs=_params.get(0);
            _inputs.setSelectedItem(inputs);
            for(int j=0;j<hidelayouts;j++){
                    _hidelab.add(new JLabel("Скрытый слой-"+(j+1)));
                    JElementConventer.ConvertToMyLabel(_hidelab.get(j), false);
                    _hidelayers.add(new JComboBox());
                    for(int i=3;i<10;i++)
                        _hidelayers.get(j).addItem(i);               
            }
            for(int i=1;i<10;i++){
                _outputs.addItem(i);
            }
            int outputs=_params.get(_params.size()-1);
            _outputs.setSelectedItem(outputs);
            JElementConventer.ConvertToMyLabel(_description, false);
            JElementConventer.ConvertToMyLabel(_unit, false);
            _description.setText("Описание:");
            _description.setPreferredSize(new Dimension(145,30));
            _unit.setText("Ед.изм:"); 
            _unit.setPreferredSize(new Dimension(145,30));
            
            for(int i=0;i<p.get(0);i++){
                _inputdescs.add(new JTextField());
                JElementConventer.ConvertToMyTextField(_inputdescs.get(i), 145, 30);
                _inputunits.add(new JTextField());
                JElementConventer.ConvertToMyTextField(_inputunits.get(i), 145, 30);
            }
            for(int i=0;i<p.get(p.size()-1);i++){
                _outputdescs.add(new JTextField());
                JElementConventer.ConvertToMyTextField(_outputdescs.get(i), 145, 30);
            }
            
            
            this.add(new JEmptyPanel());
            this.add(_description);
            this.add(_unit);
            
            _inputlab.setText("Входной слой:");
            JElementConventer.ConvertToMyLabel(_inputlab, false);
            this.add(_inputlab);
            JElementConventer.ConvertToMyComboBox(_inputs);
            _inputs.addActionListener(al);
            this.add(_inputs);
            this.add(_inputdescs.get(0));
            this.add(_inputunits.get(0));
            for(int i=1;i<p.get(0);i++){
                this.add(new JEmptyPanel());
                this.add(_inputdescs.get(i));
                this.add(_inputunits.get(i));
            }           
            
            for(int j=0;j<hidelayouts;j++){
                JElementConventer.ConvertToMyLabel(_hidelab.get(j), false);
                this.add(_hidelab.get(j));
                JElementConventer.ConvertToMyComboBox(_hidelayers.get(j));
                this.add(_hidelayers.get(j));
                this.add(new JEmptyPanel());
            }
            
            _outputlab.setText("Выходной слой:");
            JElementConventer.ConvertToMyLabel(_outputlab, false);
            this.add(_outputlab);
            JElementConventer.ConvertToMyComboBox(_outputs);
            _outputs.addActionListener(al);
            this.add(_outputs);
            this.add(_outputdescs.get(0));
            this.add(new JEmptyPanel(145,30));

            for(int i=1;i<p.get(p.size()-1);i++){
                this.add(new JEmptyPanel());
                this.add(_outputdescs.get(i));
                this.add(new JEmptyPanel(145,30));
            }
            
            int height=0;
            for(int i=0;i<p.size();i++){
                for(int j=0;j<p.get(i);j++){
                    height+=35;
                }
            }            
            
            System.out.println("indexes:"+_elementindex);
            this.setPreferredSize(new Dimension(650,height));
            revalidate();
        }
        
        public void removeElements(){
            int i=this.getComponentCount();
            System.out.println(i);
            Component[] comp=this.getComponents();
            System.out.println(comp.length);
            for(int j=0;j<comp.length;j++)
                this.remove(comp[j]);
            this.repaint();
        }
    }
    
    
    
    /**
     * Creates new form DisplayCreate
     */
    
    public DisplayCreate() {
        initComponents();
        _params=new ArrayList();
        _params.add(4);
        _params.add(3);
        _params.add(4);
        for(int i=3;i<11;i++){
            this.jComboBoxLevels.addItem(String.valueOf(i));
        }
        ActionListener al=new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                int size=_params.size();
                int input=_params.get(0);
                int output=_params.get(size-1);
                int newsize=Integer.valueOf(jComboBoxLevels.getSelectedItem().toString());
                ArrayList<Integer> hl=new ArrayList();
                for(int i=1;i<newsize-1;i++){
                    if(i<size-1){
                        hl.add(_params.get(i));
                    }
                    else
                        hl.add(3);
                }
                _params=new ArrayList();
                _params.add(input);
                _params.addAll(hl);
                _params.add(output);
                System.out.println(_params.toString());
                drawInterface();
            }
        };
        this.jComboBoxLevels.addActionListener(al);
        drawInterface();
    }
    
    public void drawInterface(){
        if(_dinamicPanel!=null){
            _dinamicPanel._innerpanel.removeAll();
            _dinamicPanel._innerpanel.repaint();
            _dinamicPanel.remove(_dinamicPanel._innerpanel);
            this.remove(_dinamicPanel);
            this.revalidate();
        }
        int size=_params.size();
        this.jComboBoxLevels.setSelectedItem(size);
        _dinamicPanel=new DinamicPanel(_params);
        this.add(_dinamicPanel);
        this.getContentPane().add(_dinamicPanel);
        this.pack();
        this.repaint();
        this.validate(); 
        this.setSize(650,550);
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
        jComboBoxLevels = new javax.swing.JComboBox<>();
        jButtonLevelOK = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelDesc = new javax.swing.JLabel();
        jTextFieldDesc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(650, 60));
        setResizable(false);

        jLabel1.setText("Уровней сети:");

        jComboBoxLevels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLevelsChange(evt);
            }
        });

        jButtonLevelOK.setText("OK");
        jButtonLevelOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLevelOKActionPerformed(evt);
            }
        });

        jLabelName.setText("Название сети:");

        jLabelDesc.setText("Описание:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxLevels, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                        .addComponent(jButtonLevelOK))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelName)
                            .addComponent(jLabelDesc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldName)
                            .addComponent(jTextFieldDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLevelOK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDesc)
                    .addComponent(jTextFieldDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxLevelsChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLevelsChange
        // TODO add your handling code here:
        System.out.println(this.jComboBoxLevels.getSelectedItem());
    }//GEN-LAST:event_jComboBoxLevelsChange

    private void jButtonLevelOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLevelOKActionPerformed
        // TODO add your handling code here:
        InnerPanel ip=this._dinamicPanel._innerpanel;
        boolean ok=checkFields(ip);
        if(ok==true){
            _nd.setName(this.jTextFieldName.getText());
            _nd.setDescription(this.jTextFieldDesc.getText());
            _nd.setLevel(_params.size());
            ArrayList<String> inputdesc=new ArrayList();
            ArrayList<String> inputunits=new ArrayList();
            for(int i=0;i<_params.get(0);i++){
                inputdesc.add(ip._inputdescs.get(i).getText());
                inputunits.add(ip._inputunits.get(i).getText());
            }
            ArrayList<Integer> hidelayers=new ArrayList();
            for(int i=0;i<_params.size()-2;i++){
                hidelayers.add(Integer.valueOf(ip._hidelayers.get(i).getSelectedItem().toString()));
            }
            ArrayList<String> outputdescs=new ArrayList();
            for(int i=0;i<_params.get(_params.size()-1);i++){
                outputdescs.add(ip._outputdescs.get(i).getText());
            }
            _nd.setInputs(inputdesc, inputunits);
            _nd.setLayers(hidelayers);
            _nd.setOutputs(outputdescs);
            _nd.setOK(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonLevelOKActionPerformed
    private boolean checkFields(InnerPanel ip){
        String s=this.jTextFieldName.getText();
        if(s.trim().length()==0)
            return false;
        s=this.jTextFieldDesc.getText();
        if(s.trim().length()==0)
            return false;
        int inputsize=ip._inputdescs.size();
        for(int i=0;i<inputsize;i++){
            s=ip._inputdescs.get(i).getText();
            if(s.trim().length()==0)
                return false;
            s=ip._inputunits.get(i).getText();
            if(s.trim().length()==0)
                return false;
        }
        int outputsize=ip._outputdescs.size();
        for(int i=0;i<outputsize;i++){
            s=ip._outputdescs.get(i).getText();
            if(s.trim().length()==0)
                return false;
        }
        return true;
    }
    /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DisplayCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayCreate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayCreate().setVisible(true);
            }
        });
    }
    
    public void setNetworkDescription(NetworkDescription nwd){
        this._nd=nwd;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLevelOK;
    private javax.swing.JComboBox<String> jComboBoxLevels;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldDesc;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
