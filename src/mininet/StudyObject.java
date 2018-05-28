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
public class StudyObject {
    
    public Double _val;
    public boolean _use;
    
    public StudyObject(double d,boolean b){
        _val=d;
        _use=b;
    }
        
    public void setValue(double v){
        _val=v;
    }
    public Double getValue(){
        return _val;
    }

    public void setUse(boolean b){
        _use=b;
    }
    public boolean getUse(){
        return _use;
    }
}
