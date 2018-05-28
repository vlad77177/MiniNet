/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author vlad7
 */
public class NetFacade {
    
    private NeuralNetwork _nn;
    private NetworkDescription _nwd;
    private XMLWorker _xml;
    private MyRandom _rand;
    private final MyDelegate _del;
    private Scanner _in;
    private ArrayList<Double> _resultspercent;
    private StudyData _studydata;
    boolean _download;
    boolean _performed;
    boolean _created;
    
    public NetFacade(){
        _nn=new NeuralNetwork();
        _nwd=new NetworkDescription();
        _xml=new XMLWorker();
        _rand=new MyRandom();
        _in=new Scanner(System.in);
        _del=new MyDelegate(){
                @Override
                float invoke(Synapse s){
                    return weightDefinition(s);
                }
            };
    }
    
    public int nextInt(){
        return _in.nextInt();
    }
    
    
    private float weightDefinition(Synapse s){
        if(_download==false)
            return _rand.getRandom();
        else
            return _xml.findWeight(s);
    }
    
    public void SaveNetwork(){
        boolean ok=_xml.Save(_nn,_nwd);
        if(ok==true)
            System.out.println("Нейросеть сохранена успешно!");
    }
    
    public void NetDownload(){
        _xml.Download(_nn,_nwd);
    }
    
    public void performCalculation(ArrayList<Double> val){
        _resultspercent=new ArrayList();
        _resultspercent=_nn.performCalculation(val);
        _performed=true;
    }
    
    public NeuralNetwork getNeuralNetwork(){
        return _nn;
    }
    public NetworkDescription getNetworkDescription(){
        return _nwd;
    }
    public MyDelegate getRandDelegate(){
        return _del;
    }
    public ArrayList<Double> getResults(){
        return _resultspercent;
    }
    public void startStudy(ArrayList<Double> v,ArrayList<Boolean> b){
        setStudyData(v,b);
        this._nn.performStudy(_studydata);
    }
    public void setStudyData(ArrayList<Double> v,ArrayList<Boolean> b){
        this._studydata=new StudyData();
        for(int i=0;i<v.size();i++){
            _studydata.addData(v.get(i), b.get(i));
        }
    }
}
