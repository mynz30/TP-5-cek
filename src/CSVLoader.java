import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVLoader {
    
    // Load kurir dari CSV
    public static ArrayList<Kurir> loadKurirDariCSV(String filePath) {
        ArrayList<Kurir> listKurir = new ArrayList<>();
        int barisDitolak = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int nomorBaris = 0;
            
            while ((line = reader.readLine()) != null) {
                nomorBaris++;
                
                // Skip header jika ada
                if (nomorBaris == 1 && line.contains("namaKurir")) {
                    continue;
                }
                
                String[] data = line.split(",");
                
                // Validasi format
                if (data.length < 2) {
                    System.out.println("[WARN] Baris " + nomorBaris + ": Format tidak valid");
                    barisDitolak++;
                    continue;
                }
                
                String namaKurir = data[0].trim();
                String tipe = data[1].trim();
                
                // Validasi tipe kurir
                if (!tipe.equals("TETAP") && !tipe.equals("FREELANCE")) {
                    System.out.println("[WARN] Baris " + nomorBaris + ": Tipe kurir '" + tipe + "' tidak valid");
                    barisDitolak++;
                    continue;
                }
                
                // Buat objek kurir sesuai tipe
                Kurir kurir;
                if (tipe.equals("TETAP")) {
                    kurir = new KurirTetap(namaKurir, 10);
                } else {
                    kurir = new KurirFreelance(namaKurir, 10);
                }
                
                // Set gaji pokok
                kurir.gajiPokok = 1000000;
                
                listKurir.add(kurir);
            }
            
            System.out.println("Total kurir berhasil: " + listKurir.size());
            if (barisDitolak > 0) {
                System.out.println("Total baris ditolak: " + barisDitolak);
            }
            
        } catch (IOException e) {
            System.out.println("[ERROR] File data tidak ditemukan: " + filePath);
        }
        
        return listKurir;
    }
    
    // Load paket dari CSV
    public static ArrayList<Paket> loadPaketDariCSV(String filePath) {
        ArrayList<Paket> listPaket = new ArrayList<>();
        int barisDitolak = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int nomorBaris = 0;
            
            while ((line = reader.readLine()) != null) {
                nomorBaris++;
                
                // Skip header jika ada
                if (nomorBaris == 1 && line.contains("noTracking")) {
                    continue;
                }
                
                String[] data = line.split(",");
                
                // Validasi format
                if (data.length < 3) {
                    System.out.println("[WARN] Baris " + nomorBaris + ": Format tidak valid");
                    barisDitolak++;
                    continue;
                }
                
                String noTracking = data[0].trim();
                String namaPenerima = data[1].trim();
                String metode = data[2].trim();
                
                // Validasi metode pengiriman
                if (!metode.equals("Regular") && !metode.equals("Same Day") && !metode.equals("Next Day")) {
                    System.out.println("[WARN] Baris " + nomorBaris + ": Metode pengiriman '" + metode + "' tidak valid");
                    barisDitolak++;
                    continue;
                }
                
                // Buat objek paket sesuai metode
                Paket paket;
                if (metode.equals("Same Day")) {
                    paket = new PaketSameDay(namaPenerima);
                } else if (metode.equals("Next Day")) {
                    paket = new PaketNextDay(namaPenerima);
                } else {
                    // Regular - gunakan PaketNextDay sebagai default
                    paket = new PaketNextDay(namaPenerima);
                }
                
                // Set noTracking manual
                paket.noTracking = noTracking;
                
                listPaket.add(paket);
            }
            
            System.out.println("Total paket berhasil: " + listPaket.size());
            if (barisDitolak > 0) {
                System.out.println("Total baris ditolak: " + barisDitolak);
            }
            
        } catch (IOException e) {
            System.out.println("[ERROR] File data tidak ditemukan: " + filePath);
        }
        
        return listPaket;
    }
}