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

public class NetInput {
    private String _desc;
    private String _unit;
    public NetInput(String desc,String unit){
        _desc=desc;
        _unit=unit;
    }
    public String getDesc(){
        return _desc;
    }
    public String getUnit(){
        return _unit;
    }
}
