/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mininet;

import java.util.Random;

/**
 *
 * @author vlad7
 */
public class MyRandom {
    private Random rand;
    
    public MyRandom(){
        rand=new Random();
    }
    
    public float getRandom(){
        return 0.0f+rand.nextFloat()%100.0f;
    }
    
}
