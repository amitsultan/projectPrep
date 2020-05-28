package external;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public final class BugLogger {
    private static final BugLogger instance = new BugLogger();

    public String logname = "Football-Log";
    protected String env = ".";
    private static File logFile;

    public static BugLogger getInstance() {
        return instance;
    }

    public static BugLogger getInstance(String withName) {
        instance.logname = withName;
        instance.createLogFile();
        return instance;
    }

    public void createLogFile() {
        File logsFolder = new File("Bug-Logs");
        if (!logsFolder.exists()) {
            System.err.println("INFO: Creating new logs directory in " + env);
            logsFolder.mkdir();

        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        logname = logname + '-' + dateFormat.format(cal.getTime()) + ".log";
        BugLogger.logFile = new File(logsFolder.getName(), logname);
        try {
            if (logFile.createNewFile()) {
                System.err.println("INFO: Creating new log file");
            }
        } catch (IOException e) {
            System.err.println("ERROR: Cannot create log file");
            System.exit(1);
        }
    }

    private BugLogger() {
        if (instance != null) {
            throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
        }
        this.createLogFile();
    }

    public void log(String message) {
        try {
            FileWriter out = new FileWriter(BugLogger.logFile, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String savelog = dtf.format(now) +" : "+ message + "\n";
            out.write(savelog.toCharArray());
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR: Could not write to log file");
        }
    }
}
