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
public class NeironMath {
    public static double activateFunction(double arg){
        Double d=1.0d/(1.0d+Math.pow(Math.E, -arg));
        //System.out.println(String.valueOf(d));
        return 1.0d/(1.0d+Math.pow(Math.E, -arg));
    }
    public static double derivatedActivationFunction(double arg){
        return activateFunction(arg)*(1-activateFunction(arg));
    }
}
