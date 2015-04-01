/*
 * Project: Distract No More
 * Coder: Angela Pang
 *
 * Date Created: 2015/04/01
 * Date Modified: 2015/04/01
 *
 */

package distract;

/*Import drawing libraries*/
import java.awt.*;
import java.awt.image.*;


/**
 *
 * @author Angela
 */
public class Animate {
    private Image[] img;   //List of images
    private Image[] expression;    //List of all the image expressions
    
    private int ptr;    //Current image 
    private int show = 1;   //Default to show the image   
    private int disable = 0;    //Default to allow image to be interactive
    private int move = 1;   //Default to have the animation
    
    private int xPos, yPos; //Image location
    private int dx, dy; //Image movement direction
    
    /**
     * Class construct that will set all global variables to default values
     * and start the animation sequence.
     */
    Animate() {
        /*Initialize image information*/
        this.xPos = 0;
        this.yPos = 0;
        this.dx = 0;
        this.dy = 0;
        
        /*Start the image animation*/
        startAnimation();
    }
    
    /** 
     * Class constructor for the animation and starts the animation sequence.
     * 
     * @param xPos  image x coordinate
     * @param yPos  image y coordinate
     * @param dx    image x direction
     * @param dy    image y direction
     */
    Animate(int xPos, int yPos, int dx, int dy) {
        /*Initialize image information*/
        this.xPos = xPos;
        this.yPos = yPos;
        this.dx = dx;
        this.dy = dy;    
        
        /*Start the image animation*/
        startAnimation();
    }
    
    /**
     * Start animation only called once by the constructor
     */
    private void startAnimation() {
        getImages();        
    }
    
    /**
     * Get all the images in the image folder
     */
    private void getImages() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
        }
    }
    
    /**
     * Animates the image
     *
     * @return  1 if animation drawn and 0 if animation is not drawn
     */
    public int animate() {
        /*Determine if the animation is set to play*/
        if (this.move == 0 || this.disable == 1 || this.show == 0) {
            return 0;   //Return 0 to statue false and animation wasn't played
        }
        
        return 1;   //Return 1 to statue false and animation was played
    }
    
    /**
     * Draw the image to the window
     */
    public void draw(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }    
    
    /**
     * Update the image to the next sequence of the series
     */
    public void updateImage() {

    }
            
    
    /*** Updates animation status ***/
    /**
     * Hide the animation
     */
    public void hide() {
        this.show = 0;  //Set the value to be false
    }
    
    /**
     * Show the animation
     */
    public void show() {
        this.show = 1;  //Set the value to be true
    }
    
    /**
     * Start the image movement
     */
    public void start() {
        this.move = 1;
    }
    
    /**
     * Stop the image movement
     */
    public void stop() {
        this.move = 0;
    }
    
    /**
     * Prevent any interactions the image
     */
    public void disable() {
        this.disable = 1;
    }
    
    /**
     * Enable interaction with the image
     */
    public void enable() {
        this.disable = 0;
    }
}
