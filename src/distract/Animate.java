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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;


/**
 *
 * @author Angela
 */
public class Animate {
    private HashMap imageMap;  //Stores all the image names as the key
    private Image[] img;   //List of images
    private Image[] expression;    //List of all the image expressions
    
    private String activeImg;  //Name of the current active image
    
    private int ptr;    //Current image 
    private int show = 1;   //Default to show the image   
    private int disable = 0;    //Default to allow image to be interactive
    private int move = 1;   //Default to have the animation
    private int state = 0;  //Default state to be 0
    
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
        /*Initialize hash map*/
        imageMap = new HashMap();
         
        /*Go through all images in the directory*/
        searchDir();
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
            System.out.println("File " + listOfFiles[i].getName());
            
            /*Parse the file name*/
            imgName = parseImgName(listOfFiles[i].getName());
            
            /*Determine if the file name is the same as previous*/
            if (imgName.equals(oldImage)) {
                countImg += 1;
                System.out.println(countImg);
            }
            else {
                /*Store the oldImage name*/
                if (!oldImage.equals("") || countImg != 0 ) {
                    storeImage(oldImage, countImg);  
                }
                
                oldImage = imgName;
                countImg = 1;
                    
                System.out.println("NEW " + countImg + " " +  i + " " + listOfFiles.length);
            }
          }
        }
        
        /*Save the last sequence of images for that animation*/
        if (!oldImage.equals("") || countImg != 0) {
              storeImage(oldImage, countImg);
          }
        
        System.out.println(imageMap);   //TESTING!!!!!!!!!!!!! - SEEING IF ALL THE VALUES STORED
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
        
        System.out.println(imgName);
        
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
     * Get all the images in the image folder
     *
    private void getImages() {        
        String path = System.getProperty("user.dir") + "\\test\\greenCat2.png";
        File file = new File(path);
        
        try {
            this.img[0] = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("ERROR Loading image " + path);
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
        }
    }*/
    
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
    public void draw() {
    //public void draw(Graphics graphic) {
        //g.drawImage(img, 0, 0, null);
        //graphic.drawImg(this.img[0], 0, 0, null);
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
