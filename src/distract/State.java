/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distract;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    
    /*Class Objects*/
    Animate animate;
    Report report;
    
    /*JFrames*/
    JFrame aniFrame;    //Animation JFrame
    JFrame repFrame;    //Report JFrame
    JFrame setFrame;    //Setting JFrame
    
    JFrame curReport;   //Current status display of the program
    JFrame blackScreen; //Set black screen if user is on the computer for over 30 minutes
        
    /*State value*/
    private int stateVal = 0;   //Default State is 0
    private int numClick = 0;  //Records the number of clicks made on the program
    private String curImg = "";
    private float blackVisi = 0.0f;   //Stores the alpha value for the black screen
    
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
        /*Set current state*/
        setState();
        animate.startAnimation();
        
        /*Set up JFrame that'll hold the character*/
        setAniFrame();     
                
        /*Start the animation*/
        timer = new Timer(updateSpeed, this);
        timer.start();
    }   
    
    /**
     * Sets the character frame
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
        aniFrame.setAlwaysOnTop( true );   // Set's the frame to always be on top
        
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
     * Set up the black screen
     */
    private void setBlackScreen() {
        /*Create frame for the animation*/
        blackScreen = aniFrame; //new JFrame("Black Screen");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            blackScreen.setSize(screenSize.width, screenSize.height);
        }
        catch (NullPointerException error) {
            System.out.println("ERROR - Unable to set screen size");
        }
        
        blackScreen.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
    }
    
    /**
     * Show the black screen
     */
    private void showBlackScreen() {
        float alpha = 0.1f;
        if (blackVisi < 1.0f) {
            blackVisi += alpha;
        }
      
        if (blackVisi < 0.9f) {
            blackScreen.setBackground(new Color(0.0f,0.0f,0.0f,blackVisi));
        }
        else {
            blackScreen.getContentPane().removeAll();
            JPanel myPanel = new JPanel();
            blackScreen.setBackground(new Color(0.0f,0.0f,0.0f,1.0f));
            blackScreen.setBackground(Color.black);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            myPanel.setSize(screenSize.width, screenSize.height);
            myPanel.setBackground(Color.black);
            blackScreen.add(myPanel);            
            blackScreen.getContentPane().repaint();
        }
    }
    
    /**
     * Set up the report window
     */
    private void setCurReport() {
        /*Create frame for the animation*/
        curReport = new JFrame("Distract No More - Report");
        
        /*Initializing Mouse events*/
        curReport.addMouseListener(this);
        curReport.addMouseMotionListener(this);
        
        /*Remove frame boarder and buttons*/
        //curReport.setUndecorated(true);
        //curReport.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        curReport.setAlwaysOnTop( true );   // Set's the frame to always be on top
        
        /*Listens to when the program window is closed*/
        curReport.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        
        /*Add items to the frame*/
        //curReport.add(animate);
        curReport.pack();
    }
    
    /**
     * Changes the program's state depending on the time
     */
    private void setState() {
        float runTime;
        float min = 60;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        /*Determine run time and convert it to minutes*/
        runTime = (float)(report.getRunTime())/min;
        //System.out.println("Converted runtime" + runTime);   //TESTING!!!!
        /*Determine state based on run time*/
        if (runTime < 1.0) { //State of character is content
            animate.setImage("circle");
            animate.setAnimationSpeed(100);
            if (runTime > 0.01) { 
                aniFrame.setLocation(15,screenSize.height - 75);
            }
            
            stateVal = 0;
            curImg = "circle";
        }
        else if (runTime < 3.0) { //State of character is content
            animate.setImage("sleep");
            if(!curImg.equals("sleep")) {
                animate.setAnimationSpeed(1);
            }
            /*Set JFrame size to be proper*/
            aniFrame.setSize(animate.getPreferredSize().width, animate.getPreferredSize().height);
            aniFrame.setLocation(15,screenSize.height - 75);
            
            stateVal = 2;
            curImg = "sleep";
        }
        else if (runTime < 75.5) { //State of character is content
            animate.setImage("mprphStand");
            if(!curImg.equals("mprphStand")) {
                animate.setAnimationSpeed(10);
            }
            /*Set JFrame size to be proper*/
            aniFrame.setSize(animate.getPreferredSize().width, animate.getPreferredSize().height);
            aniFrame.setLocation(15,screenSize.height - 75);
            
            stateVal = 4;
            curImg = "mprphStand";
        }
        else if (runTime < 15.0) {
            /*Update Animation*/
            animate.setImage("sitting");
            if(!curImg.equals("sitting")) {
                animate.setAnimationSpeed(1000);
            }
            
            /*Set JFrame size to be proper*/
            aniFrame.setSize(animate.getPreferredSize().width, animate.getPreferredSize().height);
            aniFrame.setLocation(15,screenSize.height - 75);
            
            /*Update State value*/
            stateVal = 8; 
            curImg = "sitting";
        }
        else if (runTime < 25.0) { //State of character is Anger
            animate.setImage("blackMorph");
            if(!curImg.equals("blackMorph")) {
                animate.setAnimationSpeed(1);
            }
            
            /*Set JFrame size to be proper*/
            aniFrame.setSize(animate.getPreferredSize().width, animate.getPreferredSize().height);
            aniFrame.setLocation(0,0);
            
            stateVal = 10;
            curImg = "blackMorph";
        }
        else if (runTime < 25.5 && stateVal == 10) {
            animate.stop();
            setBlackScreen();
            stateVal = 11;
        }
        else if (stateVal == 11) {
            showBlackScreen();
        }
        else {  //Reset
            stateVal = 0;
            report.resetStartTime();
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
        /*Determine appropriate action*/
        if (numClick < 3) { //Display Program report
            //showCurReport;            
            curReport.setVisible(true);
        }
        
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
        if(stateVal < 10) {
            Point cord = evt.getLocationOnScreen();
            aniFrame.setLocation(cord.x - mDownCord.x, cord.y - mDownCord.y);
        }
    }
    

    public void mouseMoved(MouseEvent evt) {
        /*Lock mouse if the program is at highest state*/
        if (stateVal >= 10) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(0,0);
            } catch (AWTException error) {
                error.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        setState();
    }
}
