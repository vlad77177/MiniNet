/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author vlad7
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column){
        if(value instanceof JLabel){
            return (JLabel)value;
        }
        if(value instanceof JComboBox){
            return (JComboBox)value;
        }
        if(value instanceof JTextField){
            return (JTextField)value;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
