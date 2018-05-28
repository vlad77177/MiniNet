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
public class StudyData {
    
    private ArrayList<StudyObject> _so;
    
    public StudyData(){
        _so=new ArrayList();
    }
    
    public void addData(double d,boolean b){
        _so.add(new StudyObject(d,b));
    }
    
    public StudyObject getStudyObject(int i){
        return _so.get(i);
    }
    
    public int getSize(){
        return _so.size();
    }
    
}
