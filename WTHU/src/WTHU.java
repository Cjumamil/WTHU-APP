import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;


public class WTHU {

    private static final int DEFAULT_DELAY = 5000; // Default delay in milliseconds (5 seconds)
    private static Timer timer;
    private static TimerTask task;
    private static JFrame frame;
    
    static boolean isAlarmAM;
    
    

    public static void main(String[] args) {
    	
    	//set up frame
        frame = new JFrame("WTHU - Wake The Hell Up"); // Create a new frame
        frame.setSize(700, 1000); // Set the size of the frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton button = new JButton("Set Alarm");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTimerDuration();
            }
        });
        

    	

        panel.add(button);
        frame.add(panel);

        frame.setVisible(true); // Make the frame visible
    }

    private static void setTimerDuration() {

    	
    	//user input alarm time
        String input = JOptionPane.showInputDialog(frame, "Set Alarm (HH:MM:AM/PM)");
        String[] time = input.split(":");
        int alarmHour = Integer.parseInt(time[0]); // 6:30 7:28
        int alarmMinute = Integer.parseInt(time[1]);
        String amPm = time[2];
        
        if(amPm.equals("PM")) {
        	alarmHour +=12; // convert back to military
        	
        }
        
        if(alarmHour >= 12) { // if noon or past 
        	isAlarmAM = false; // then it's PM
        	alarmHour -= 12;
        	
        }else { // is hour is < 12 
        	isAlarmAM = true; // then it's AM
        }
                
        
        System.out.println("Alarm set for: " + alarmHour + ":" + alarmMinute + " " + amPm);

        // localTime will be startTime of timer
        
        LocalTime currentTime = LocalTime.now(); // grab local time in military time
       // String localTime = currentTime.getHour() + ":" + currentTime.getMinute(); // should return ex: 8:30
        int currHour = currentTime.getHour();
        int currMin = currentTime.getMinute();
        
        boolean isCurrAM; // determine AM (True) or PM (False)      
        
        // convert CURRENT military time to standard time
        if(currHour >=12) { // if noon or past 
        	isCurrAM = false; // then it's PM
        	currHour -= 12;
        	
        }else { // is hour is < 12 
        	isCurrAM = true; // then it's AM
        	// no need to subtract 12
        }
        
        
        try {
        	// time to do the math
             int durationH; //*1000*60*60; // MS > secs > mins > hours
             int durationM = Math.abs(alarmMinute - currMin) ; //*1000*60; // MS > secs > mins   
            
        	// if both AM or both PM
        	if(isCurrAM && isAlarmAM || !isCurrAM && !isAlarmAM) {
        		durationH = Math.abs( alarmHour - currHour);
        	}else { // AM PM diff
        		durationH = Math.abs(currHour - alarmHour);
        	}
        	
        	durationH *= 1000*60*60; //in *1000 = secs * 60 = min *60 = hr
        	durationM *= 1000*60;
        	
        	int duration = durationH + durationM; // duration in MS
        	System.out.println(currHour + ":" + currMin);
        	System.out.println(duration + " in MS");
            
            
            
            System.out.println("Timer set for: " + duration/1000 + " seconds");
            if (duration > 0) {
                startTimer(duration);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid duration entered!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid number.");
        }
    }

    private static void startTimer(int duration) {
        if (timer != null) {
            timer.cancel(); // Cancel any previously running timer
        }

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                // Code to execute when the alarm goes off
                JOptionPane.showMessageDialog(frame, "Wake The Hell Up!");
            }
        };

        // Schedule the task to run after the specified duration
        timer.schedule(task, duration);
    }
}
