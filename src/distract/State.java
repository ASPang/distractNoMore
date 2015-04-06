/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distract;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Angela
 */
public class State extends JFrame implements MouseListener, MouseMotionListener{
    /*Mouse Variable*/
    private Point mDownCord;
    
    Animate animate;
    Report report;
    
    //Mouse mouse;
    /*
    Audio sound;
    Computer device;    
    */
    
    JFrame f;
    
    /*State value*/
    private int stateVal = 0;   //Default State is 0
    
    State() {
        animate = new Animate();
        report = new Report();
        //mouse = new Mouse(f);
        //this.addMouseMotionListener();
    }
    
    public void initialize(){
        /*Determine current state*/
        
        
        /*Set current state*/
        setState();
        
        /*Create frame for the animation*/
        f = new JFrame("Distract No More");
        
        /*Initializing Mouse events*/
        f.addMouseListener(this);
        f.addMouseMotionListener(this);
        
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
        f.add(animate);
        f.pack();
        f.setVisible(true);        
    }   
    
    /**
     * 
     */
    private void setState() {
        animate.setImage("enemy");
    }
    
    /*** Mouse Event Methods ***/
    /**
     * Handles events when the mouse clicks on the program.
     * Depending on the state of the program it'll trigger different events.
     * This includes opening and displaying the report window of the user's
     * progress on their computer.
     * 
     * @param evt Mouse event object
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        System.out.println("MOUSE CLICKED");
        /*Determine appropriate action*/
    }
    
    /**
     * Saves the mouse current location on the screen when 
     * the user holds down the button.
     * 
     * @param evt Mouse event object 
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        mDownCord = evt.getPoint();
    }    
    
    /**
     * Sets the mouse coordinate to null when the button
     * is not held down.
     * 
     * @param evt Mouse event object 
     */
    @Override
    public void mouseReleased(MouseEvent evt) {
        mDownCord = null;
    }

    public void mouseEntered(MouseEvent me) {}

    public void mouseExited(MouseEvent me) {}

    /*** Mouse Motion Listener Methods ***/
    /**
     * Allows the user to drag the program frame window by its client area.
     * It takes in the mouse current location and apply it to the 
     * frame window location.
     * 
     * @param evt Mouse event object
     */
    @Override
    public void mouseDragged(MouseEvent evt) {
        //System.out.println("MOUSE Dragging frame");
        Point cord = evt.getLocationOnScreen();
        f.setLocation(cord.x - mDownCord.x, cord.y - mDownCord.y);
    }
    

    public void mouseMoved(MouseEvent me) {}
}
