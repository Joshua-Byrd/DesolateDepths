package edu.bu.model.persistence;

import edu.bu.exceptions.LoggerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GameLogger {
    private static GameLogger instance;
    private final String LOG_FILE_PATH;
    private PrintWriter printWriter;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // Private constructor to prevent instantiation
    private GameLogger(String aPlayerName) throws LoggerException{
        File directory = new File("logs");
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }
        this.LOG_FILE_PATH = directory.getPath() + File.separator + sanitizeFileName(aPlayerName) + "_log.txt";
        try {
            // Set up PrintWriter to append to the log file.
            this.printWriter = new PrintWriter(new FileWriter(LOG_FILE_PATH, true), true);
        } catch (IOException e) {
            throw new LoggerException("Error creating PrintWriter or File in GameLogger.");
        }
    }

    /**
     * INTENT: To allow access to the single instance of a GameLogger. If GameLogger instance does not yet
     * exist, it is instantiated. Otherwise, the instance is returned.
     * PRECONDITION: None.
     * POSTCONDITION: Return value is the single instance of GameLogger.
     *
     * @return the instance of the GameLogger
     */
    public static synchronized GameLogger getInstance(String aPlayerName) throws LoggerException {
        if (instance == null) {
            instance = new GameLogger(aPlayerName);
        }
        return instance;
    }

    /**
     * INTENT: Writes the given message to the game log with a timestamp.
     * PRECONDITION: The given message must not be null.
     * POSTCONDITION: The given message has been written to the log.
     * @param message the information to be written to the log
     */
    public void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        printWriter.println("[" + dtf.format(now) + "] " + message);
    }

    /**
     * INTENT: Allows a player to view their logfile.
     * PRECONDITION 1: A GameLogger must be instantiated
     * PRECONDITION 2: A log file for the current player must exist.
     * POSTCONDITION: The contents of the current user's logfile are printed to the screen.
     * @throws LoggerException
     */
    public void printLog() throws LoggerException {
        try {
            Scanner logFile = new Scanner(new File(LOG_FILE_PATH));

            while(logFile.hasNext()) {
                System.out.println(logFile.next());
            }

            logFile.close();
        } catch(IOException e) {
            throw new LoggerException("Error reading from log file in GameLogger.printLog().");
        }
    }

    /**
     * INTENT: To close the PrintWriter stream.
     * PRECONDITIONS: printWriter must not be null
     * POSTCONDITIONS: The PrintWriter stream is closed.
     */
    public void close() {
        if (printWriter != null) {
            printWriter.close();
        }
    }

    /**
     * INTENT: Ensures that the file name consists only of alphanumeric characters to prevent malicious
     * code injection
     * PRECONDITION: fileName is not null
     * POSTCONDITION: return value = fileName with all non-alphnumeric characters removed
     * @param fileName the name of the file to sanitize
     * @return
     */
    public String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
}
