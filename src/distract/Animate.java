/**
 * Project: Distract No More
 * @FileName: Animate.java
 * @author: Angela Pang
 *
 * Date Created: 2015/04/01
 * Date Modified: 2015/04/01
 *
 * Description:
 * 
 */

package distract;

/*Import drawing libraries*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.swing.JPanel;

/*IO Stream*/
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*Data storage*/
import java.util.HashMap;

/**
 *
 * @author Angela
 */
public class Animate extends Component implements ActionListener {
    private HashMap imageMap;  //Stores all the image names as the key
    //private Image[] img;   //List of images
    
    private String activeImg = "";  //Name of the current active image
    public BufferedImage img;

    private int curImgNum = 1;    //Current image 
    private int show = 1;   //Default to show the image   
    private int disable = 0;    //Default to allow image to be interactive
    private int move = 1;   //Default to have the animation move
    private int state = 0;  //Default state to be 0
    
    private int xPos, yPos; //Image location
    private int dx, dy; //Image movement direction
    
    private int animationSpeed = 100; //speed of the image rotation in milliseconds
    private Timer timer;    //This object will pause the class for 5 seconds
    
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
    }
    
    /**
     * Start animation only called once by the constructor
     */
    public void startAnimation() {
        /*Initialize hash map*/
        imageMap = new HashMap();
         
        /*Go through all images in the directory*/
        searchDir();
                
        /*Start the animation*/
        timer = new Timer(animationSpeed, this);
        timer.start();       
    }
        
    /**
     * Override action event class method so the following image will be set.
     * 
     * @param event Action Even that is played 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String path = System.getProperty("user.dir") + "\\src\\distract\\img\\" + activeImg + curImgNum + ".png";
        try {            
            File file = new File(path);
            img = ImageIO.read(file);
            //System.out.println("IMG = " + activeImg + curImgNum);
            /*Increment to the next image*/
            nextImage();
            repaint();
        } catch (IOException error) { 
            System.out.println("ERROR - path incorrect: " + path + " " + error);
        }
    }
    
    /**
     * Determine the next image number in the sequence.
     */
    private void nextImage() {
        /*Get the last number in the image sequence*/
        int lastImg;
        try {
            lastImg = (int)imageMap.get(activeImg);
        }
        catch (NullPointerException error) {
            lastImg = 0;
        }
        
        if (curImgNum + 1 > lastImg) {
            curImgNum = 1;
        }
        else {
            curImgNum += 1;
        }
    }
    
    
    /**
     * Override original class method of drawing to the window frame
     * 
     * @param graphic   Image being drawn the window frame 
     */
    @Override
    public void paint(Graphics graphic) {
        if (img != null) {
           graphic.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
        }
    }
    
    /**
     * Override original class method so window size to be the same as the image size
     * 
     * @return Dimension   Object that contains the width and the height of the image
     */
    @Override
    public Dimension getPreferredSize() {
        if (img == null) {
           return new Dimension(500,500);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
    
    /**
     * Search through the entire image folder
     */
    private void searchDir() {
        int countImg = 0;   //Number of images with a particular name        
        
        String oldImage = "";   //Stores the previous file name found in the directory
        String imgName = "";   //Image file  name that was in the directory
        String dir = System.getProperty("user.dir") + "\\src\\distract\\img\\"; //Directory of the images
        
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        
        /*Go through every file in the folder*/
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {            
            /*Parse the file name*/
            imgName = parseImgName(listOfFiles[i].getName());
            
            /*Determine if the file name is the same as previous*/
            if (imgName.equals(oldImage)) {
                countImg += 1;
            }
            else {
                /*Store the oldImage name*/
                if (!oldImage.equals("") || countImg != 0 ) {
                    storeImage(oldImage, countImg);  
                }
                
                oldImage = imgName; //Save old image name 
                countImg = 1;   //Reset image count
            }
          }
        }
        
        /*Save the last sequence of images for that animation*/
        if (!oldImage.equals("") || countImg != 0) {
              storeImage(oldImage, countImg);
          }
    }
    
    /**
     * Parse the image name
     * 
     * Regular Expression Source Code:
     * http://stackoverflow.com/questions/924394/how-to-get-file-name-without-the-extension
     * 
     * @param fName String of the file name
     */
    private String parseImgName(String fName) {
        String imgName = "";
        
        /*Remove file extension*/
        imgName = fName.replaceFirst("[.][^.]+$", "");
        
        /*Remove number at end of image name*/
        imgName = removeFileNum(imgName);
        
        return imgName;
    }
    
    /**
     * Remove the file number from its name.
     * 
     * @return imgName  The name of the image without any numbers at the end
     */
    private String removeFileNum(String fName) {
        String imgName = "";
        char letter;
        int i;  //Loop counter
        
        /*Go through end characters in the string till no more numbers*/
        for (i = fName.length(); i > 0; i-- ) {
            letter = fName.charAt(i - 1);
            
            /*Determine if character is a digit*/
            if (!Character.isDigit(letter)) {
                break;
            }
        }
        
        /*Create a substring without numbers*/
        imgName = fName.substring(0,i);
        
        return imgName;
    }
    
    /**
     * Store all the images into a hash map where the key is the filename
     * and the value stored is the number of image iterations
     * 
     * @param fName String of the file name
     * @param num   Total number of images with that file name
     */
    private void storeImage(String imgName, int num) {
        imageMap.put(imgName, num);
    }
    
    /*** Updates animation status ***/  
    /**
     * Set the current image animation
     * 
     * @param img   Name of the image that the program is to displayed.
     */
    public void setImage(String img) {
        if (!activeImg.equals(img)) {
            curImgNum = 1;
        }
        activeImg = img;
    }
    
    /**
     * Update the animation speed in millisecond.
     * 
     * @param speed Speed of the animation for the program
     */
    public void setAnimationSpeed(int speed) {
        /*Save new animation speed*/
        animationSpeed = speed;
        
        /*Restart timer*/
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(animationSpeed, this);
        timer.start();           
    }
    
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
        timer.start();
    }
    
    /**
     * Stop the image movement
     */
    public void stop() {
        timer.stop();
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
