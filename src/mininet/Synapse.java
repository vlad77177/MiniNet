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
public class Synapse extends NetElement {
    
    private Neiron _last;
    private Neiron _next;
    private double _weight;
    
    private MyDelegate WeightDelegate;
    
    public Synapse(int number,int level,MyDelegate del){
        super(number,level); 
        WeightDelegate=del;
        _weight=del.invoke(this);
    }
    
    public void NeironsConnect(Neiron last,Neiron next){
        _last=last;
        _next=next;
        _name="synapse-(n-"+_last.getLevel()+"-"+_last.getNumber()+"):(n-"+_next.getLevel()+"-"+_next.getNumber()+")";
        _last.AddSynapse(this,1);
        _next.AddSynapse(this, 0);
        System.out.println(_name+"w:"+_weight);
    }
    
    public double getWeight(){
        return _weight;
    }
    public void setWeight(double d){
        _weight=d;
    }
    public Neiron getNextNeiron(){
        return _next;
    }
}
