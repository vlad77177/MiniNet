/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author vlad7
 */
public class XMLWorker { 
    
    private Document _docxml;
    
    public XMLWorker(){
        
    }
    
    public boolean Save(NeuralNetwork nw,NetworkDescription nd){
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            addNeironNetwork(document,nw,nd);
        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        }
        return true;
    }
    private static void addNeironNetwork(Document document,NeuralNetwork nw,NetworkDescription nd)throws TransformerFactoryConfigurationError, DOMException{
        
        Element neuralnetwork=document.createElement("neuralnetwork");
        document.appendChild(neuralnetwork);
        
        //Описание сети
        Element desc=document.createElement("description");
        neuralnetwork.appendChild(desc);
        
        desc.setAttribute("netname", nd.getName());
        desc.setAttribute("description", nd.getDescription());
        desc.setAttribute("level", String.valueOf(nd.getLevel()));
        
        Element inputs=document.createElement("inputs");
        desc.appendChild(inputs);
        
        for(int i=0;i<nd.getInputs().size();i++){
            Element input=document.createElement("input");
            input.setAttribute("desc", nd.getInputs().get(i).getDesc());
            input.setAttribute("unit", nd.getInputs().get(i).getUnit());
            inputs.appendChild(input);
        }
        
        Element layers=document.createElement("layers");
        desc.appendChild(layers);
        
        for(int i=0;i<nd.getLayers().size();i++){
            Element layer=document.createElement("layer");
            layer.setAttribute("count", nd.getLayers().get(i).toString());
            layers.appendChild(layer);
        }
        
        Element outputs=document.createElement("outputs");
        desc.appendChild(outputs);
        
        for(int i=0;i<nd.getOutputs().size();i++){
            Element output=document.createElement("output");
            output.setAttribute("desc", nd.getOutputs().get(i).getAnsver());
            outputs.appendChild(output);
        }
        
        //Запись информации нейронов и синапсов
        Element network=document.createElement("network");
        //network.setAttribute("xmlns", "www.google.com");
        neuralnetwork.appendChild(network);
        
        Element nlayers=document.createElement("neyronlayers");
        network.appendChild(nlayers);
        Element slayers=document.createElement("synapselayers");
        network.appendChild(slayers);
        
        for(int i=0;i<nw.getNeironLayers().size();i++){
            Element nlayer=document.createElement("neironlayer");
            nlayers.appendChild(nlayer);
            
            int end=nw.getNeironLayer(i).getNeirons().size();
            for(int j=0;j<end;j++){
                Neiron neiron=nw.getNeironLayers().get(i).getNeirons().get(j);
                Element neironname=document.createElement(neiron.getName());
                neironname.setAttribute("level", Integer.toString(neiron.getLevel()));
                neironname.setAttribute("number", Integer.toString(neiron.getNumber()));
                nlayer.appendChild(neironname);
            }
        }
        
        int snumber=0;
        for(int i=0;i<nw.getSynapseLayers().size();i++){
            Element slayer=document.createElement("synapselayer"+i);
            slayers.appendChild(slayer);
            
            int end=nw.getSynapseLayer(i).getSynapses().size();
            for(int j=0;j<end;j++){
                Synapse synapse=nw.getSynapseLayers().get(i).getSynapses().get(j);
                Element synapsename=document.createElement("synapse");
                synapsename.setAttribute("level", Integer.toString(synapse.getLevel()));
                synapsename.setAttribute("number", Integer.toString(synapse.getNumber()));
                synapsename.setAttribute("weight", Double.toString(synapse.getWeight()));
                slayer.appendChild(synapsename);
                snumber++;
            }
        }
        
        writeXML(document);
    }
    
    private static void writeXML(Document document)throws TransformerFactoryConfigurationError{
        try{
            File file=new File("NeuralNetwork.xml");
            Transformer tr=TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(document), new StreamResult(file));
        }
        catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public void Download(NeuralNetwork nw,NetworkDescription nd){
        try{
            DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
            db.setValidating(false);
            DocumentBuilder builder = db.newDocumentBuilder();
            Document document=builder.parse(new File("NeuralNetwork.xml"));
            _docxml=document;
            netInstallation(nw,nd);
        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        }
        catch (SAXException ex) {
            ex.printStackTrace(System.out);
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    private void netInstallation(NeuralNetwork nw,NetworkDescription nd){
        
        NodeList desc=_docxml.getElementsByTagName("description");
        Node description=desc.item(0);
        NamedNodeMap attr=description.getAttributes();
        Node name=attr.getNamedItem("netname");
        Node descr=attr.getNamedItem("description");
        Node level=attr.getNamedItem("level");
        nd.setName(name.getNodeValue());
        nd.setDescription(descr.getNodeValue());
        nd.setLevel(Integer.valueOf(level.getNodeValue()));
        
        NodeList inputs=_docxml.getElementsByTagName("input");
        ArrayList<String> descs=new ArrayList();
        ArrayList<String> units=new ArrayList();
        for(int i=0;i<inputs.getLength();i++){
            Node in=inputs.item(i);
            NamedNodeMap nm=in.getAttributes();
            Node d=nm.getNamedItem("desc");
            Node u=nm.getNamedItem("unit");
            descs.add(d.getNodeValue());
            units.add(u.getNodeValue());
        }
        nd.setInputs(descs, units);
        
        NodeList outputs=_docxml.getElementsByTagName("output");
        ArrayList<String> odescs=new ArrayList();
        for(int i=0;i<outputs.getLength();i++){
            Node out=outputs.item(i);
            NamedNodeMap nm=out.getAttributes();
            Node d=nm.getNamedItem("desc");
            odescs.add(d.getNodeValue());
        }
        nd.setOutputs(odescs);
        
        NodeList layers=_docxml.getElementsByTagName("layer");
        ArrayList<Integer> layer=new ArrayList();
        for(int i=0;i<layers.getLength();i++){
            Node l=layers.item(i);
            NamedNodeMap nm=l.getAttributes();
            Node c=nm.getNamedItem("count");
            layer.add(Integer.valueOf(c.getNodeValue()));
        }
        
        nd.setLayers(layer);
    }
    
    public float findWeight(Synapse s){

        NodeList syn=_docxml.getElementsByTagName("synapse");
        for(int i=0;i<syn.getLength();i++){
            Node synapse=syn.item(i);
            NamedNodeMap attr=synapse.getAttributes();
            Node level=attr.getNamedItem("level");
            Node number=attr.getNamedItem("number");
            Node weight=attr.getNamedItem("weight");
            int l=Integer.valueOf(level.getNodeValue());
            int n=Integer.valueOf(number.getNodeValue());
            float f=Float.valueOf(weight.getNodeValue());
            if(s.getLevel()==l && s.getNumber()==n)
                return f;
        }
        return 0.0f;
    }
}
