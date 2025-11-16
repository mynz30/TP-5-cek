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

    // Menambahkan kurir ke perusahaan
    public void tambahKurir(Kurir kurir) {
        // Validasi nama kurir tidak duplikat
        for (Kurir k : listKurir) {
            if (k.getNamaKurir().equalsIgnoreCase(kurir.getNamaKurir())) {
                System.out.println("Kurir dengan nama " + kurir.getNamaKurir() + " sudah ada!");
                return;
            }
        }
        listKurir.add(kurir);
        System.out.println("Kurir " + kurir.getNamaKurir() + " bergabung ke " + namaPerusahaan);
    }

    // Menambahkan paket ke sistem
    public void tambahPaket(Paket paket) {
        listPaket.add(paket);
        System.out.println("Paket baru untuk " + paket.getNamaPenerima() + " dimasukkan ke sistem " + namaPerusahaan);
    }

    // Menampilkan semua kurir
    public void tampilkanSemuaKurir() {
        System.out.println("=== Daftar Kurir di " + namaPerusahaan + " ===");
        for (Kurir kurir : listKurir) {
            kurir.detailKurir();
        }
    }

    // Menampilkan semua paket
    public void tampilkanSemuaPaket() {
        System.out.println("=== Daftar Paket di " + namaPerusahaan + " ===");
        for (Paket paket : listPaket) {
            paket.detailPaket();
        }
    }

    // Assign paket ke kurir (Task 5: menggunakan nama kurir dan nomor tracking)
    public void assignPaketKeKurir(Scanner scanner) {
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

        // Cari kurir berdasarkan nama
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

        // Cari paket berdasarkan nomor tracking
        Paket paketDipilih = null;
        for (Paket p : listPaket) {
            if (p.getNoTracking().equals(noTracking)) {
                paketDipilih = p;
                break;
            }
        }

        if (paketDipilih == null) {
            System.out.println("Paket dengan nomor tracking " + noTracking + " tidak ditemukan!");
            return;
        }

        // Cek apakah kurir sudah penuh
        if (kurirDipilih.getCountPaket() >= kurirDipilih.getKapasitas()) {
            System.out.println("Kapasitas kurir " + namaKurir + " sudah penuh");
            System.out.println("Paket telah diambil oleh kurir lain!");
            return;
        }

        // Assign paket ke kurir
        kurirDipilih.addJob(paketDipilih);
    }

    // Menyelesaikan paket (Task 5: menggunakan nama kurir dan nomor tracking)
    public void selesaikanPaket(Scanner scanner) {
        if (listKurir.isEmpty()) {
            System.out.println("Belum ada kurir di sistem!");
            return;
        }

        System.out.print("Masukkan nama kurir: ");
        String namaKurir = scanner.nextLine();

        // Cari kurir berdasarkan nama
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

        // Cari paket di list paket kurir
        Paket paketDitemukan = null;
        for (int i = 0; i < kurirDipilih.getCountPaket(); i++) {
            if (kurirDipilih.listPaket[i].getNoTracking().equals(noTracking)) {
                paketDitemukan = kurirDipilih.listPaket[i];
                break;
            }
        }

        if (paketDitemukan != null) {
            System.out.println("Paket dengan nomor tracking:");
            
            // Ubah status paket menjadi "Diterima"
            String namaPenerima = paketDitemukan.getNamaPenerima();
            paketDitemukan.ubahStatus("Diterima");
            
            // Hitung gaji kurir berdasarkan tipe paket
            kurirDipilih.hitungGaji(paketDitemukan);
            
            System.out.println("=> " + noTracking + " sudah diterima oleh " + namaPenerima);
        } else {
            System.out.println("Paket dengan nomor tracking:");
            System.out.println("=> " + noTracking + " sudah diterima");
        }
        System.out.println();
    }

    // Melihat laporan keuangan (Task 5: dengan profit perusahaan 10% dari gaji kurir)
    public void lihatLaporanKeuangan() {
        System.out.println("=== Laporan Keuangan " + namaPerusahaan + " ===");
        
        double totalGajiKurir = 0;
        
        // Hitung total gaji semua kurir
        for (Kurir kurir : listKurir) {
            totalGajiKurir += kurir.getGajiPokok();
        }
        
        // Profit perusahaan = 10% dari total gaji kurir
        double profitPerusahaan = totalGajiKurir * 0.10;
        
        System.out.println("Total Proffit: Rp" + (int)profitPerusahaan);
        System.out.println();
    }

    // Menu utama
    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        // ASCII Art Banner
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
            System.out.println("3. Tampilkan Semua Kurir");
            System.out.println("4. Tampilkan Semua Paket");
            System.out.println("5. Assign Paket ke Kurir");
            System.out.println("6. Selesaikan Paket");
            System.out.println("7. Lihat Laporan Keuangan");
            System.out.println("8. Keluar dari PTBurhan");
            System.out.print("Pilih menu: ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            // Menggunakan if-else untuk memilih menu
            if (pilihan == 1) {
                // Menu 1: Tambah Kurir
                System.out.print("Masukkan tipe kurir (Freelance/Tetap): ");
                String tipe = scanner.nextLine().toLowerCase();
                System.out.print("Masukkan nama kurir: ");
                String namaKurir = scanner.nextLine();
                System.out.print("Masukkan kapasitas kurir: ");
                int kapasitas = scanner.nextInt();
                scanner.nextLine();

                Kurir kurir;
                if (tipe.equals("freelance")) {
                    kurir = new KurirFreelance(namaKurir, kapasitas);
                } else {
                    kurir = new KurirTetap(namaKurir, kapasitas);
                }
                tambahKurir(kurir);
                System.out.println();
                
            } else if (pilihan == 2) {
                // Menu 2: Tambah Paket
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
                // Menu 3: Tampilkan Semua Kurir
                tampilkanSemuaKurir();
                
            } else if (pilihan == 4) {
                // Menu 4: Tampilkan Semua Paket
                tampilkanSemuaPaket();
                
            } else if (pilihan == 5) {
                // Menu 5: Assign Paket ke Kurir
                assignPaketKeKurir(scanner);
                System.out.println();
                
            } else if (pilihan == 6) {
                // Menu 6: Selesaikan Paket
                selesaikanPaket(scanner);
                
            } else if (pilihan == 7) {
                // Menu 7: Lihat Laporan Keuangan
                lihatLaporanKeuangan();
                
            } else if (pilihan == 8) {
                // Menu 8: Keluar
                System.out.println("Keluar dari PTBurhan...");
                System.out.println();
                System.out.println("Terima kasih telah menggunakan layanan BurhanExpress!");
                running = false;
                
            } else {
                // Pilihan tidak valid
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