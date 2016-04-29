/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

/**
 *
 * @author pablo
 */
public class Test implements Runnable{

    @Override
    public void run() {
        int i = 0;
        while(i<100){
            System.out.println("current: "+i);
            i++;
        }
    }
    
    
    
}
