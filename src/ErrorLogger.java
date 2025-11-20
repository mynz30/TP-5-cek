package src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {
    private static final String LOG_FILE = "error_log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Method untuk menulis error ke file
    public static void tulisLog(String pesanError) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            String logEntry = "[" + timestamp + "] [ERROR] " + pesanError;
            writer.write(logEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Gagal menulis ke file log: " + e.getMessage());
        }
    }

    // Method untuk membaca isi log
    public static void bacaLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            boolean adaLog = false;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                adaLog = true;
            }
            
            if (!adaLog) {
                System.out.println("Tidak ada log error yang tercatat");
            }
        } catch (IOException e) {
            System.out.println("Tidak ada log error yang tercatat");
        }
    }
}