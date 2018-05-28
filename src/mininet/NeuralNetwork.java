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

public class NeuralNetwork {
    
    private ArrayList<NeironLayer> _neironlayers;
    private ArrayList<SynapseLayer> _synapselayers;
    
    double _valueinpercent;
    
    public NeuralNetwork(){
        _neironlayers=new ArrayList();
        _synapselayers=new ArrayList();
    }
    
    public void CreateNetwork(ArrayList<Integer> netparams,MyDelegate del){
        int leight=netparams.size();
        for(int i=0;i<leight;i++){
            _neironlayers.add(new NeironLayer(i));
            _neironlayers.get(i).CreateNeirons(netparams.get(i));
        }
        for(int i=0;i<leight-1;i++){           
            int nowleight=_neironlayers.get(i).getLeight();
            int nextleight=_neironlayers.get(i+1).getLeight();
            _synapselayers.add(new SynapseLayer(i));
            _synapselayers.get(i).CreateSynapses(nowleight*nextleight,del);
            int synnumber=0;
            for(int n=0;n<_neironlayers.get(i).getLeight();n++){
                Neiron neir_last=_neironlayers.get(i).getNeiron(n);
                for(int m=0;m<_neironlayers.get(i+1).getLeight();m++){
                    Neiron neir_next=_neironlayers.get(i+1).getNeiron(m);
                    Synapse syn=_synapselayers.get(i).getSynapse(synnumber);
                    CreateConnection(neir_last,syn,neir_next);
                    synnumber++;
                }
            }
        }
    }
    
    public void CreateConnection(Neiron first,Synapse synapse,Neiron last){
        first.AddSynapse(synapse, 0);
        last.AddSynapse(synapse, 1);
        synapse.NeironsConnect(first, last);
    }
    
    public ArrayList<Double> performCalculation(ArrayList<Double> inputsvalue){
       int size=this._neironlayers.size();
       for(int i=0;i<this._neironlayers.size()-1;i++){
           for(int j=0;j<this._neironlayers.get(i).getLeight();j++){
               Neiron n=this._neironlayers.get(i).getNeiron(j);
               if(i==0){
                   n.setValue(inputsvalue.get(j));
                   System.out.println("Входной нейрон "+j+": "+n.getValue());
               }
               else{
                   n.setValue(0.0d);
                   System.out.println("Нейрон "+j+" имеет значение: "+n.getValue());
                   n.setValue(NeironMath.activateFunction(n.getValue()));
                   System.out.println("Нейрон "+j+" активирован: "+n.getValue());
               }
               for(int k=0;k<n.getOutSynapses().size();k++){
                   Neiron next=n.getOutSynapses().get(k).getNextNeiron();
                   Synapse syn=n.getOutSynapses().get(k);
                   next.addValue(n.getValue()*syn.getWeight());
               }
               //n.setValue(0.0d);
           }
       }
       NeironLayer out=this._neironlayers.get(size-1);
       Double summ=0.0d;
       for(int i=0;i<out.getLeight();i++){
           Neiron n=out.getNeiron(i);
           n.setValue(NeironMath.activateFunction(n.getValue()));
           System.out.println("value-"+i+": "+String.valueOf(n.getValue()));
           summ+=n.getValue();
       }
       _valueinpercent=summ/100.0d;
       ArrayList<Double> percents=new ArrayList();
       for(int i=0;i<out.getLeight();i++){
           double p=0;
           p=out.getNeiron(i).getValue()/_valueinpercent;
           percents.add(p);
           //out.getNeiron(i).setValue(0.0d);
           System.out.println(percents.get(i));
       }
       return percents;
    }
    
    public void performStudy(StudyData sd){
        for(int i=0;i<sd.getSize();i++){
            System.out.println("val:"+sd.getStudyObject(i).getValue()+" use:"+sd.getStudyObject(i).getUse());
        }
        
        NeironLayer nlout=getOutputLayer();
        
        for(int i=0;i<nlout.getLeight();i++){
            System.out.println("Значения выходного слоя:"+nlout.getNeiron(i).getValue());
            Neiron n=nlout.getNeiron(i);
            if(sd.getStudyObject(i).getUse()==true)
                n.setErrorValue(sd.getStudyObject(i).getValue()-n.getValue()/_valueinpercent);
            System.out.println("Ошибка : "+n.getErrorValue());
        }
        
        for(int l=this._neironlayers.size()-1;l>=0;l--){
            for(int n=0;n<this._neironlayers.get(l).getLeight();n++){
                Neiron neiron=this._neironlayers.get(l).getNeiron(n);
                double errorsumm=0.0d;
                for(int s=0;s<neiron.getOutSynapses().size();s++){
                    Synapse syn=neiron.getOutSynapses().get(s);
                    Neiron nextneiron=syn.getNextNeiron();
                    errorsumm+=nextneiron.getErrorValue()*syn.getWeight();
                }
                neiron.setErrorValue(errorsumm);
                System.out.println("Ошибка "+neiron.getName()+" : "+neiron.getErrorValue());
            }
        }
        
        for(int s=0;s<_synapselayers.size();s++){
            SynapseLayer layernow=_synapselayers.get(s);
            System.out.println("Размер слоя "+s+" : "+layernow.getSynapses().size());
            for(int n=0;n<layernow.getSynapses().size();n++){
                System.out.println("Расчет слой "+s+" синапс "+n);
                Synapse synnow=layernow.getSynapses().get(n);
                Neiron nextneiron=synnow.getNextNeiron();
                System.out.println("Вес синапса : "+synnow.getWeight()+" Ошибка следующего нейрона : "+nextneiron.getErrorValue());
                try{
                    synnow.setWeight(synnow.getWeight()+0.01*nextneiron.getErrorValue()*NeironMath.derivatedActivationFunction(nextneiron.getValue())*nextneiron.getValue());
                    System.out.println("Синапс "+synnow.getName()+" новое значение :"+synnow.getWeight());
                }
                catch(Exception ex){
                    System.out.println(ex.toString());
                }
            }
        }
        
        System.out.println("Обучение завершено");
    }
    
    public ArrayList<NeironLayer> getNeironLayers(){
        return _neironlayers;
    }
    
    public ArrayList<SynapseLayer> getSynapseLayers(){
        return _synapselayers;
    }
    
    public NeironLayer getNeironLayer(int index){
        return _neironlayers.get(index);
    }
    
    public SynapseLayer getSynapseLayer(int index){
        return _synapselayers.get(index);
    }
    
    public NeironLayer getOutputLayer(){
        return _neironlayers.get(_neironlayers.size()-1);
    }
}
