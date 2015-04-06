/*
 * Project: Distract No More
 * Coder: Angela Pang
 *
 * Date Created: 2015/04/01
 * Date Modified: 2015/04/01
 *
 */
package distract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author Angela
 */
public class Report implements ActionListener {
    private Timer timer;
    
    private int updateSpeed = 10000;
    private int upTime = 0; //Total run time of the program
    
    private File reportFile;
    
    private String reportName = "log.txt";
    private String[] programs;  //Tempoarily list of programs running
    
    /**
     * Class construct that will set all global variables to default values.
     */
    Report() {
        /*Start reading and inputting the data*/
        startReport();
    }
    
    /**
     * 
     */
    public void startReport() {
        /*Determine if file exist*/
        checkFileExist();
        
        /*Record system uptime*/
        
        /*Update the program log file*/
        updateFile();
        
        /*Start the animation*/
        timer = new Timer(updateSpeed, this);
        timer.start();
    }

    /**
     * Determine if file exists if not create a new file
     * 
     * @return 1/0  Returns a value of 1 if the file exist or created successfully.
     *              Returns a value of 0 if the file does not exist and program was
     *              not successful in creating the file.
     */
    private int checkFileExist() {
        String fName = reportName;
        String path = System.getProperty("user.dir") + "\\src\\distract\\report\\" + fName; //Directory of the images
        File file;
        FileOutputStream openFile;
        
        try {
            /*Create file object*/
            file = new File(path);

            /*Determine if file exists*/
            if(!file.exists()) {
                /*Create file*/
                file.createNewFile();
            } 

            openFile = new FileOutputStream(file, false);     //Open the file
            openFile.close();  //Close the file
            return 1;   //Return true that file now exists
        }
        catch (IOException error) {
            System.out.println("ERROR - Could not create Distract Report File at: " + path);
        }
        
        return 0;   //Return false that file does not exist
    }
    
    /**
     * Updates the programs log file with the current time stamp and 
     * the programs that are running at the time.
     */
    public void updateFile() {
        String timeStamp;
        
        /*Get current time*/
        timeStamp = getCurTime();
        
        /*Put in the date and time stamp*/
        writeToFile(timeStamp, programs);
        
        /*Determine the OS*/
        getOS();
        System.out.println(getOS());
        
        /*Determine the programs running*/
        /*Determine if any web browsers open*/
        /*Determine if any video players are open*/
    }
    
    /**
     * Write to file with the current time that the program was launched
     * along with the programs opened at the time.
     * 
     * @param time      The time stamp which the program logged the information.
     * @param programs  List of programs running on the computer at the 
     *                  time the program was updating.
     */
    private void writeToFile(String timeStamp, String[] programs) {
        String fName = reportName;
        String path = System.getProperty("user.dir") + "\\src\\distract\\report\\" + fName; //Directory of the images
        File file;
        FileWriter openFile;
        BufferedWriter writeBuf;

        /*Open file*/
        try {
            /*Determine if log file still exists*/
            if (checkFileExist() == 1) {
                String data = timeStamp; 
                
                file = new File(path);
                openFile = new FileWriter(file, true); //true = append file
                writeBuf = new BufferedWriter(openFile);

                writeBuf.write(data);
                writeBuf.close();

                System.out.println("Done " + data);
            } else {
                System.out.println("ERROR - Could not create Distract Report File at: " + path);
            }
        } catch (IOException error) {
            System.out.println("ERROR - Could not create Distract Report File at: " + path);
        }     
    }
    
    /**
     * Determines the current date and time and formats it as a string.
     * 
     * @return curTime  Returns the current date and time as a string.
     */
    private String getCurTime() {
        Date date;
        DateFormat format;
        
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //Set up format
        date = new Date();   //Get the current date time
	
        //System.out.println(format.format(date) + " string conversion " + date.toString());
        
        return format.format(date);
    }
    
    /**
     * Determine the OS that the program is running on. 
     * This will determine some of the expected programs that 
     * user might be running and key commands.
     * 
     * @return osName   Name of the operating system
     */
    private String getOS() {
        /*Get the OS name*/
        String osName = System.getProperty("os.name");
        
        return osName;
    }
    
    /**
     * Updates the program state file with new information
     * 
     * @param evt   Action event object 
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        /*Update the file*/
        updateFile();
    }   
    
    /*** Global Variable Updates ***/
    /**
     * Updates the timer to when the report should be updated.
     * 
     * @param newTime   An integer value
     */
    public void updateSpeed(int newTime) {
        updateSpeed = newTime;
    }
}
