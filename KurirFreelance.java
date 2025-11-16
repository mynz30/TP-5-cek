public class KurirFreelance extends Kurir {
    private int totalJamTerbang;

    public KurirFreelance(String nama, int kapasitas) {
        super(nama, kapasitas);
        this.totalJamTerbang = 0;
    }

    public int getTotalJamTerbang() {
        return totalJamTerbang;
    }

    public void tambahJamTerbang(int jam) {
        this.totalJamTerbang += jam;
    }

    // Implementasi method abstract hitungGaji
    @Override
    public void hitungGaji(Paket p) {
        if (p instanceof PaketSameDay) {
            gajiPokok += 24300;
        } else if (p instanceof PaketNextDay) {
            gajiPokok += 22950;
        }
        // Tambahkan jam terbang berdasarkan ETA paket
        tambahJamTerbang(p.getEta());
    }
    
    // Override detailKurir untuk menampilkan info spesifik termasuk jam terbang
    @Override
    public void detailKurir() {
        System.out.println("=== Info Kurir Freelance ===");
        System.out.println("Nama Kurir: " + namaKurir);
        System.out.println("Gaji Kurir: " + (int)gajiPokok);
        System.out.println("Kapasitas Kurir: " + countPaket + " dari " + kapasitas + " paket");
        System.out.println("Total Jam Terbang: " + totalJamTerbang + " jam");
        System.out.println();
    }
}