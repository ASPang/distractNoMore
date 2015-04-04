/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distract;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Angela
 */
public class State {
    Animate animate;
    /*
    Audio sound;
    Report report;
    Computer device;    
    */
    
    State() {
        animate = new Animate();
    }
    
    public void initialize(){
        /*Determine current state*/
        
        /*Set current state*/
        setState();
        
        /*Create frame for the animation*/
        JFrame f = new JFrame("Load Image Sample");
        
        /*Remove frame boarder and buttons*/
        f.setUndecorated(true);
        f.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        
        /*Listens to when the program window is closed*/
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        
        /*Add items to the frame*/
        
        //while(true) {
        f.add(animate);
        f.pack();
        f.setVisible(true);
        //}
        
    }
    
    private void setState() {
        animate.setImage("enemy");
    }
}
