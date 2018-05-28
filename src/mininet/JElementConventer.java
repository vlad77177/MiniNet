/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.text.MaskFormatter;


/**
 *
 * @author vlad7
 */

public class JElementConventer {
    public static void ConvertToMyButton(JButton b){
        b.setSize(100, 30);
    }
    public static void ConvertToMyLabel(JLabel l,boolean full){
        l.setPreferredSize(new Dimension(145,30));
        if(full==true)
            l.setText("N/A");
    }
    public static void ConvertToMyFTextField(JFormattedTextField ftf){
        ftf.setPreferredSize(new Dimension(150,30));
    }
    public static void ConvertToMyTextField(JTextField tf,int width,int height){
        tf.setPreferredSize(new Dimension(width,height));
    }
    public static void ConvertToMyComboBox(JComboBox cb){
        cb.setPreferredSize(new Dimension(145,30));
    }
}
