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
public class SynapseLayer extends Layer {
    
    ArrayList<Synapse> _synapses;
    
    public SynapseLayer(int level){
        super(level,"synapselayer");
        _synapses=new ArrayList();
    }
    
    public void CreateSynapses(int count,MyDelegate del){
        for(int i=0;i<count;i++){
            _synapses.add(new Synapse(i,_level,del));
        }
    }
    
    public ArrayList<Synapse> getSynapses(){
        return _synapses;
    }
    
    public Synapse getSynapse(int index){
        return _synapses.get(index);
    }
}
