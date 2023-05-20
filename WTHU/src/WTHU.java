import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class WTHU {

    private static final int DEFAULT_DELAY = 5000; // Default delay in milliseconds (5 seconds)
    private static Timer timer;
    private static TimerTask task;
    private static JFrame frame;
    
    

    public static void main(String[] args) {
        frame = new JFrame("WTHU - Wake The Hell Up"); // Create a new frame
        frame.setSize(700, 1000); // Set the size of the frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton button = new JButton("Set Timer");
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
        String input = JOptionPane.showInputDialog(frame, "Enter duration in secs:");
        try {
            int duration = Integer.parseInt(input);
            duration = duration*1000;
            
            System.out.println("Timer set for: " + duration/1000 + " Seconds");
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
