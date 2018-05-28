/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.util.ArrayList;

/**
 *
 * @author vlad7
 */
public abstract class Layer {
    int _level;
    String _name;
    
    public Layer(int level){

    }
    
    public Layer(int level,String nametype){
        _level=level;
        _name=nametype+"-"+level;
        System.out.println(_name);
    }
}
