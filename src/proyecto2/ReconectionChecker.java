/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class ReconectionChecker implements Runnable {
        public Router router;
    
    public ReconectionChecker(Router router){
        this.router = router;
    
    }

    @Override
    public void run() {
            try {
                Thread.sleep(Proyecto2.timeU*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReconectionChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        Timer timer = new Timer();
        
        timer.schedule(new TimerTask() {
        
        @Override
        public void run() { // Function runs every MINUTES minutes.
            router.checkKeepAlive();
            
        
            
            
        }
        }, 0, Proyecto2.timeU*1000); //n segundos
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable. 1 
        }
    }
    

