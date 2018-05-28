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
public class Neiron extends NetElement {
    
    private double _value;
    private double _error;
    private ArrayList<Synapse> _inputs;
    private ArrayList<Synapse> _outputs;
    
    public Neiron(int number,int level){
        super(number,level);
        _value=0.0d;
        _error=0.0d;
        _inputs=new ArrayList();
        _outputs=new ArrayList();
        _name="neiron-"+_level+"-"+_number;
        System.out.println(_name);
    }
    
    public void AddSynapse(Synapse syn,int flag){
        if(flag==0){
            _inputs.add(syn);
        }
        if(flag==1){
            _outputs.add(syn);
        }
    }
    public ArrayList<Synapse> getOutSynapses(){
        return _outputs;
    }
    public ArrayList<Synapse> getInputSynapses(){
        return _inputs;
    }
    public void setValue(Double d){
        _value=d;
    }
    public Double getValue(){
        return _value;
    }
    public void addValue(Double d){
        _value+=d;
    }
    public void setErrorValue(Double e){
        _error=e;
    }
    public Double getErrorValue(){
        return _error;
    }
}
