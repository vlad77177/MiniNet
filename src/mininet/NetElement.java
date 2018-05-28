/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

/**
 *
 * @author vlad7
 */
public abstract class NetElement {
    
    int _number;
    int _level;
    String _name;
    
    public NetElement(int number,int level){
        _number=number;
        _level=level;
    }   
    
    public NetElement(int number,int level,MyDelegate del){
        
    }
    
    public String getName(){
        return _name;
    }
    
    public int getNumber(){
        return _number;
    }
    
    public int getLevel(){
        return _level;
    }
}
