package edu.ucla.cs.scai.aztec.ws;


import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 *
 * @author massimo
 */
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Aztec-WS started!");

        
        Timer timerIndice = new Timer();
        TimerTask taskUpdate = new UpdateDocuments();
        timerIndice.schedule(taskUpdate, 30000l, 60000l * 30); //starts after 30 seconds from deployment is repeater every 30 minutes

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aztec-WS stopped!");
    }

    class UpdateDocuments extends TimerTask {

        boolean firstRun = true;

        @Override
        public void run() {
            if (firstRun) {
                Logger.getLogger(ServletListener.class).info("First run");
            } else {
                Logger.getLogger(ServletListener.class).info("Incremental update");
            }
            firstRun=false;
            
        }
    }
}