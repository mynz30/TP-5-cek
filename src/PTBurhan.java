import java.util.ArrayList;
import java.util.Scanner;

public class PTBurhan {
    private String namaPerusahaan;
    private ArrayList<Kurir> listKurir;
    private ArrayList<Paket> listPaket;

    public PTBurhan(String nama) {
        this.namaPerusahaan = nama;
        this.listKurir = new ArrayList<>();
        this.listPaket = new ArrayList<>();
    }

    // Menambahkan kurir dengan exception handling
    public void tambahKurir(Kurir kurir) throws DuplikasiKurirException {
        for (Kurir k : listKurir) {
            if (k.getNamaKurir().equalsIgnoreCase(kurir.getNamaKurir())) {
                throw new DuplikasiKurirException("Kurir dengan nama " + kurir.getNamaKurir() + " sudah ada!");
            }
        }
        listKurir.add(kurir);
        System.out.println("Kurir " + kurir.getNamaKurir() + " bergabung ke " + namaPerusahaan);
    }

    public void tambahPaket(Paket paket) {
        listPaket.add(paket);
        System.out.println("Paket baru untuk " + paket.getNamaPenerima() + " dimasukkan ke sistem " + namaPerusahaan);
    }

    // Load kurir dari CSV
    public void loadDataKurirDariCSV(String filePath) {
        ArrayList<Kurir> kurirBaru = CSVLoader.loadKurirDariCSV(filePath);
        
        for (Kurir kurir : kurirBaru) {
            try {
                tambahKurir(kurir);
            } catch (DuplikasiKurirException e) {
                System.out.println("[WARN] " + e.getMessage());
                ErrorLogger.tulisLog(e.getMessage());
            }
        }
        System.out.println();
    }

    // Load paket dari CSV
    public void loadDataPaketDariCSV(String filePath) {
        ArrayList<Paket> paketBaru = CSVLoader.loadPaketDariCSV(filePath);
        
        for (Paket paket : paketBaru) {
            tambahPaket(paket);
        }
        System.out.println();
    }

    public void tampilkanSemuaKurir() {
        System.out.println("=== Daftar Kurir di " + namaPerusahaan + " ===");
        for (Kurir kurir : listKurir) {
            kurir.detailKurir();
        }
    }

    public void tampilkanSemuaPaket() {
        System.out.println("=== Daftar Paket di " + namaPerusahaan + " ===");
        for (Paket paket : listPaket) {
            paket.detailPaket();
        }
    }

    // Assign paket ke kurir dengan exception handling dan logging
    public void assignPaketKeKurir(Scanner scanner) throws PaketTidakDitemukanException, KapasitasPenuhException {
        if (listKurir.isEmpty()) {
            System.out.println("Belum ada kurir di sistem!");
            return;
        }
        if (listPaket.isEmpty()) {
            System.out.println("Belum ada paket di sistem!");
            return;
        }

        System.out.print("Masukkan nama kurir: ");
        String namaKurir = scanner.nextLine();

        Kurir kurirDipilih = null;
        for (Kurir k : listKurir) {
            if (k.getNamaKurir().equalsIgnoreCase(namaKurir)) {
                kurirDipilih = k;
                break;
            }
        }

        if (kurirDipilih == null) {
            System.out.println("Kurir dengan nama " + namaKurir + " tidak ditemukan!");
            return;
        }

        System.out.print("Masukkan nomor tracking: ");
        String noTracking = scanner.nextLine();

        Paket paketDipilih = null;
        for (Paket p : listPaket) {
            if (p.getNoTracking().equals(noTracking)) {
                paketDipilih = p;
                break;
            }
        }

        if (paketDipilih == null) {
            String errorMsg = "Paket dengan nomor " + noTracking + " tidak ditemukan!";
            ErrorLogger.tulisLog(errorMsg);
            throw new PaketTidakDitemukanException(errorMsg);
        }

        if (kurirDipilih.getCountPaket() >= kurirDipilih.getKapasitas()) {
            String errorMsg = "Kapasitas kurir " + namaKurir + " sudah penuh!";
            ErrorLogger.tulisLog(errorMsg);
            throw new KapasitasPenuhException(errorMsg);
        }

        kurirDipilih.ambilPaket(paketDipilih);
    }

    // Selesaikan paket dengan exception handling dan logging
    public void selesaikanPaket(Scanner scanner) throws PaketTidakDitemukanException {
        if (listKurir.isEmpty()) {
            System.out.println("Belum ada kurir di sistem!");
            return;
        }

        System.out.print("Masukkan nama kurir: ");
        String namaKurir = scanner.nextLine();

        Kurir kurirDipilih = null;
        for (Kurir k : listKurir) {
            if (k.getNamaKurir().equalsIgnoreCase(namaKurir)) {
                kurirDipilih = k;
                break;
            }
        }

        if (kurirDipilih == null) {
            System.out.println("Kurir dengan nama " + namaKurir + " tidak ditemukan!");
            return;
        }

        System.out.print("Masukkan nomor tracking paket: ");
        String noTracking = scanner.nextLine();

        try {
            kurirDipilih.kirimPaket(noTracking);
        } catch (PaketTidakDitemukanException e) {
            ErrorLogger.tulisLog(e.getMessage());
            throw e;
        }
    }

    public void lihatLaporanKeuangan() {
        System.out.println("=== Laporan Keuangan " + namaPerusahaan + " ===");
        
        double totalGajiKurir = 0;
        for (Kurir kurir : listKurir) {
            totalGajiKurir += kurir.getGajiPokok();
        }
        
        double profitPerusahaan = totalGajiKurir * 0.10;
        System.out.println("Total Profit: Rp" + (int)profitPerusahaan);
        System.out.println();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(" ____             _                   _____                               ");
        System.out.println("|  _ \\           | |                 |  __|                              ");
        System.out.println("| |_) |_   _ _ __| |__   __ _ _ __   | |__  __  ___ __  _ __ ___  ___ ___ ");
        System.out.println("|  _ <| | | | '__| '_ \\ / _` | '_ \\  |  __| \\ \\/ / '_ \\| '__/ _ \\/ __/ __|");
        System.out.println("| |_) | |_| | |  | | | | (_| | | | | | |___  >  <| |_) | | |  __/\\__ \\__ \\");
        System.out.println("|____/ \\__,_|_|  |_| |_|\\__,_|_| |_| |_____|/_/\\_\\ .__/|_|  \\___||___/___/");
        System.out.println("                                                  | |                      ");
        System.out.println("                                                  |_|                      ");
        
        System.out.print("Masukan nama kamu: ");
        String namaUser = scanner.nextLine();
        System.out.println("Hallo, " + namaUser + "! Selamat datang di Burhan Express");
        System.out.println();
        
        System.out.print("Masukkan nama perusahaan: ");
        String namaPerusahaan = scanner.nextLine();
        this.namaPerusahaan = namaPerusahaan;
        System.out.println("Perusahaan " + namaPerusahaan + " berhasil dibuat!");
        System.out.println();

        boolean running = true;
        while (running) {
            System.out.println("=== MENU " + namaPerusahaan + " ===");
            System.out.println("1. Tambah Kurir");
            System.out.println("2. Tambah Paket");
            System.out.println("3. Load Data Kurir dari CSV");
            System.out.println("4. Load Data Paket dari CSV");
            System.out.println("5. Tampilkan Semua Kurir");
            System.out.println("6. Tampilkan Semua Paket");
            System.out.println("7. Assign Paket ke Kurir");
            System.out.println("8. Selesaikan Paket");
            System.out.println("9. Lihat Laporan Keuangan");
            System.out.println("10. Baca Log Error");
            System.out.println("11. Keluar dari PTBurhan");
            System.out.print("Pilih menu: ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                System.out.print("Masukkan tipe kurir (Freelance/Tetap): ");
                String tipe = scanner.nextLine().toLowerCase();
                System.out.print("Masukkan nama kurir: ");
                String namaKurir = scanner.nextLine();
                System.out.print("Masukkan kapasitas kurir: ");
                int kapasitas = scanner.nextInt();
                scanner.nextLine();

                try {
                    Kurir kurir;
                    if (tipe.equals("freelance")) {
                        kurir = new KurirFreelance(namaKurir, kapasitas);
                    } else {
                        kurir = new KurirTetap(namaKurir, kapasitas);
                    }
                    tambahKurir(kurir);
                } catch (DuplikasiKurirException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                    ErrorLogger.tulisLog(e.getMessage());
                }
                System.out.println();
                
            } else if (pilihan == 2) {
                System.out.print("Masukkan tipe paket (SameDay/NextDay): ");
                String tipePaket = scanner.nextLine().toLowerCase();
                System.out.print("Masukkan nama penerima: ");
                String namaPenerima = scanner.nextLine();

                Paket paket;
                if (tipePaket.equals("sameday")) {
                    paket = new PaketSameDay(namaPenerima);
                } else {
                    paket = new PaketNextDay(namaPenerima);
                }
                tambahPaket(paket);
                System.out.println();
                
            } else if (pilihan == 3) {
                System.out.print("Masukkan path file CSV kurir (default: data/kurir.csv): ");
                String pathKurir = scanner.nextLine();
                if (pathKurir.isEmpty()) {
                    pathKurir = "data/kurir.csv";
                }
                loadDataKurirDariCSV(pathKurir);
                
            } else if (pilihan == 4) {
                System.out.print("Masukkan path file CSV paket (default: data/paket.csv): ");
                String pathPaket = scanner.nextLine();
                if (pathPaket.isEmpty()) {
                    pathPaket = "data/paket.csv";
                }
                loadDataPaketDariCSV(pathPaket);
                
            } else if (pilihan == 5) {
                tampilkanSemuaKurir();
                
            } else if (pilihan == 6) {
                tampilkanSemuaPaket();
                
            } else if (pilihan == 7) {
                try {
                    assignPaketKeKurir(scanner);
                } catch (PaketTidakDitemukanException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                } catch (KapasitasPenuhException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                }
                System.out.println();
                
            } else if (pilihan == 8) {
                try {
                    selesaikanPaket(scanner);
                } catch (PaketTidakDitemukanException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                }
                System.out.println();
                
            } else if (pilihan == 9) {
                lihatLaporanKeuangan();
                
            } else if (pilihan == 10) {
                System.out.println("=== LOG ERROR ===");
                ErrorLogger.bacaLog();
                System.out.println();
                
            } else if (pilihan == 11) {
                System.out.println("Keluar dari PTBurhan...");
                System.out.println();
                System.out.println("Terima kasih telah menggunakan layanan BurhanExpress!");
                running = false;
                
            } else {
                System.out.println("Pilihan tidak valid!");
                System.out.println();
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        PTBurhan perusahaan = new PTBurhan("");
        perusahaan.start();
    }
}