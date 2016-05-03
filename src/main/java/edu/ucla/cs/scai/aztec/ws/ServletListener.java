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

        System.out.println("Wordnet path system property: "+System.getProperty("wordnet.path"));
        System.out.println("TF/IDF path system property: "+System.getProperty("tfidf.path"));
        System.out.println("Entries path system property: "+System.getProperty("entries.path"));
        System.out.println("Publications path system property: "+System.getProperty("publications.path"));
        System.out.println("Representatives words path system property: "+System.getProperty("representativeWords.path"));
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