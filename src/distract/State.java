/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distract;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Angela
 */
public class State extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
    /*Timer*/
    private Timer timer;
    private int updateSpeed = 1000;
    
    /*Mouse Variable*/
    private Point mDownCord;
    
    Animate animate;
    Report report;
    
    //Mouse mouse;
    /*
    Audio sound;
    Computer device;    
    */
    
    /*JFrames*/
    JFrame aniFrame;    //Animation JFrame
    JFrame repFrame;    //Report JFrame
    JFrame setFrame;    //Setting JFrame
        
    /*State value*/
    private int stateVal = 0;   //Default State is 0
    private int numClick = 0;  //Records the number of clicks made on the program
    
    /**
     * Class constructor. 
     */
    State() {
        animate = new Animate();
        report = new Report();
    }
    
    /**
     * Sets up the state class. This includes mouse listener events and JFrames.
     */
    public void initialize(){
        /*Determine current state*/
                
        /*Set current state*/
        setState();
        
        /*Set up JFrame that'll hold the character*/
        setAniFrame();     
                
        /*Start the animation*/
        timer = new Timer(updateSpeed, this);
        timer.start();
    }   
    
    /**
     * 
     */
    private void setAniFrame() {
        /*Create frame for the animation*/
        aniFrame = new JFrame("Distract No More");
        
        /*Initializing Mouse events*/
        aniFrame.addMouseListener(this);
        aniFrame.addMouseMotionListener(this);
        
        /*Remove frame boarder and buttons*/
        aniFrame.setUndecorated(true);
        aniFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        
        /*Listens to when the program window is closed*/
        aniFrame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        
        /*Add items to the frame*/
        aniFrame.add(animate);
        aniFrame.pack();
        aniFrame.setVisible(true);
    }
    
    /**
     * 
     */
    private void setState() {
        float runTime;
        //float min = 60;
        float min = 1;  //TESTING!!!!!!!!!!!!!!
        
        /*Determine run time and convert it to minutes*/
        runTime = (float)(report.getRunTime())/min;
        System.out.println("Converted runtime " + runTime);
        /*Determine state based on run time*/
        if (runTime < 5.0) { //State of character is content
            animate.setImage("character");
            stateVal = 0;
        }
        else {  //State of the character is extreme anger and lock down
            /*Update Animation*/
            animate.setImage("enemy");
            
            /*Black out screen*/
            
            /*Update State value*/
            stateVal = 10; 
        }
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
        
        numClick += 1;  //Increment mouse click counter
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
        aniFrame.setLocation(cord.x - mDownCord.x, cord.y - mDownCord.y);
    }
    

    public void mouseMoved(MouseEvent me) {}

    @Override
    public void actionPerformed(ActionEvent evt) {
        setState();
    }
}
