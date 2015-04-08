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
//import java.lang.management.*;
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
    
    private int updateSpeed = 1000;
    private long startTime;    //Start time of when the program was launched
    //private long upTime = 0; //Total run time of the program in seconds
    
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
        startTime = System.nanoTime();
        
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
                
                openFile = new FileOutputStream(file, false);     //Open the file
                openFile.close();  //Close the file
            } 
            
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
        //System.out.println(getOS());  //TESTING!!!!
        
        /*Determine the programs running*/
        getRunTime();
        
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
        String line = "";
        
        File file;
        FileWriter openFile;
        BufferedWriter writeBuf;

        /*Open file*/
        try {
            /*Determine if log file still exists*/
            if (checkFileExist() == 1) {
                line = timeStamp; 
                line += "\n"; 
                
                file = new File(path);
                openFile = new FileWriter(file, true); //true = append file
                writeBuf = new BufferedWriter(openFile);

                writeBuf.write(line);
                writeBuf.newLine();
                writeBuf.close();

                //System.out.println("Done " + line);
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
     * Provides time of the program in seconds starting when the program
     * was first launched.
     * 
     * @return runTime  Returns the total run time of the program
     */
    public long getRunTime() {
        long curTime;
        long runTime = 0;
        long millisec = 1000000;
        long seconds = 1000000000;
        
        curTime = System.nanoTime();
        runTime = (curTime - startTime) / seconds;  //divide by 1000000 to get milliseconds.
        
        //System.out.println(runTime);  //TESTING!!!!!!!!!!!

        return runTime;
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
    
    /** Get JVM CPU time in milliseconds */
    /*REF: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking*/
    /*
    public long getJVMCpuTime( ) {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean( );
        if ( ! (bean instanceof sun.com.management.OperatingSystemMXBean) )
            return 0L;
        
        return ((sun.com.management.OperatingSystemMXBean)bean).getProcessCpuTime( );
    }*/
    
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
