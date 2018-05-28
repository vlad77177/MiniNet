/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.util.ArrayList;
import java.awt.event.*;

/**
 *
 * @author vlad7
 */

public class NetworkDescription {
    
    private boolean _ok;
    private int _level;
    private ArrayList<Integer> _params;
    private String _networkname;
    private String _description;
    private ArrayList<NetInput> _inputs;
    private ArrayList<Integer> _layers;
    private ArrayList<NetOutput> _outputs;
    
    public NetworkDescription(){
    }
    public void setLevel(int l){
        _level=l;
    }
    public int getLevel(){
        return _level;
    }
    public void setName(String n){
        _networkname=n;
    }
    public String getName(){
        return _networkname;
    }
    public void setDescription(String d){
        _description=d;
    }
    public String getDescription(){
        return _description;
    }
    public ArrayList<Integer> getParams(){
        _params=new ArrayList();
        _params.add(_inputs.size());
        for(int i=0;i<_layers.size();i++){
            _params.add(_layers.get(i));
        }
        System.out.println("Err:"+_outputs.size());
        _params.add(_outputs.size());
        return _params;
    }
    public void setInputs(ArrayList<String> desc,ArrayList<String> units){
        _inputs=new ArrayList();
        for(int i=0;i<desc.size();i++){
            _inputs.add(new NetInput(desc.get(i),units.get(i)));
        }
    }
    public ArrayList<NetInput> getInputs(){
        return _inputs;
    }
    public void setLayers(ArrayList<Integer> l){
        _layers=new ArrayList();
        for(int i=0;i<l.size();i++){
            _layers.add(l.get(i));
        }
    }
    public ArrayList<Integer> getLayers(){
        return _layers;
    }
    public void setOutputs(ArrayList<String> out){
        _outputs=new ArrayList();
        for(int i=0;i<out.size();i++){
            _outputs.add(new NetOutput(out.get(i)));
        }
    }
    public ArrayList<NetOutput> getOutputs(){
        return _outputs;
    }
    public void setOK(boolean b){
        _ok=b;
    }
    public boolean getOK(){
        return _ok;
    }
}
