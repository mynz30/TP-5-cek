public abstract class Kurir implements Deliverable {
    protected String namaKurir;
    protected double gajiPokok;
    protected int countPaket;
    protected Paket[] listPaket;
    protected int kapasitas;

    public Kurir(String nama, int kapasitas) {
        this.namaKurir = nama;
        this.gajiPokok = 0;
        this.countPaket = 0;
        this.kapasitas = kapasitas;
        this.listPaket = new Paket[kapasitas];
    }

    // Getter
    public String getNamaKurir() { 
        return namaKurir; 
    }
    
    public double getGajiPokok() { 
        return gajiPokok; 
    }
    
    public int getCountPaket() { 
        return countPaket; 
    }
    
    public int getKapasitas() { 
        return kapasitas; 
    }

    // Method abstract untuk menghitung gaji (akan di-override di subclass)
    public abstract void hitungGaji(Paket p);

    // Implementasi interface Deliverable
    @Override
    public void ambilPaket(Paket paket) throws KapasitasPenuhException {
        if (countPaket >= kapasitas) {
            throw new KapasitasPenuhException("Kapasitas kurir " + namaKurir + " sudah penuh!");
        }
        
        listPaket[countPaket] = paket;
        countPaket++;
        paket.ubahStatus("Dikirim oleh Kurir " + namaKurir);
        System.out.println("Paket telah diambil oleh Kurir " + namaKurir);
    }

    @Override
    public void kirimPaket(String noTracking) throws PaketTidakDitemukanException {
        Paket paketDitemukan = null;
        
        for (int i = 0; i < countPaket; i++) {
            if (listPaket[i].getNoTracking().equals(noTracking)) {
                paketDitemukan = listPaket[i];
                break;
            }
        }
        
        if (paketDitemukan == null) {
            throw new PaketTidakDitemukanException("Paket dengan nomor tracking " + noTracking + " tidak ditemukan!");
        }
        
        // Ubah status paket menjadi "Diterima"
        String namaPenerima = paketDitemukan.getNamaPenerima();
        paketDitemukan.ubahStatus("Diterima");
        
        // Hitung gaji kurir berdasarkan tipe paket
        hitungGaji(paketDitemukan);
        
        System.out.println("Paket dengan nomor tracking:");
        System.out.println("=> " + noTracking + " sudah diterima oleh " + namaPenerima);
    }

    // Method untuk backward compatibility dengan kode lama
    public void addJob(Paket paket) {
        try {
            ambilPaket(paket);
        } catch (KapasitasPenuhException e) {
            System.out.println(e.getMessage());
            System.out.println("Paket telah diambil oleh kurir lain!");
        }
    }

    // Menampilkan detail kurir
    public void detailKurir() {
        System.out.println("=== Info Kurir ===");
        System.out.println("Nama Kurir: " + namaKurir);
        System.out.println("Gaji Kurir: " + (int)gajiPokok);
        System.out.println("Kapasitas Kurir: " + countPaket + " dari " + kapasitas + " paket");
        System.out.println();
    }

    // Menampilkan list paket yang dibawa kurir
    public void printListPaket() {
        System.out.println("================== List Penerima ==================");
        for (int i = 0; i < countPaket; i++) {
            System.out.println("Paket " + (i + 1) + ":");
            listPaket[i].detailPaket();
        }
    }
}