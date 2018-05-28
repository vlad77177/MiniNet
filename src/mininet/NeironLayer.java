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
public class NeironLayer extends Layer {
    
    ArrayList<Neiron> _neirons;
    
    public NeironLayer(int level){
        super(level,"neironlayer");
        _neirons=new ArrayList();
    }
    
    public void CreateNeirons(int count){
        for(int i=0;i<count;i++){
            _neirons.add(new Neiron(i,_level));
        }
    }
    
    public int getLeight(){
        return _neirons.size();
    }
    
    public ArrayList<Neiron> getNeirons(){
        return _neirons;
    }
    
    public Neiron getNeiron(int index){
        return _neirons.get(index);
    }
}
