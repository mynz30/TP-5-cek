public class KurirTetap extends Kurir {
    public KurirTetap(String nama, int kapasitas) {
        super(nama, kapasitas);
    }

    // Implementasi method abstract hitungGaji
    @Override
    public void hitungGaji(Paket p) {
        if (p instanceof PaketSameDay) {
            gajiPokok += 27000;
        } else if (p instanceof PaketNextDay) {
            gajiPokok += 25500;
        }
    }
    
    // Override detailKurir untuk menampilkan info spesifik
    @Override
    public void detailKurir() {
        System.out.println("=== Info Kurir Tetap ===");
        System.out.println("Nama Kurir: " + namaKurir);
        System.out.println("Gaji Kurir: " + (int)gajiPokok);
        System.out.println("Kapasitas Kurir: " + countPaket + " dari " + kapasitas + " paket");
        System.out.println();
    }
}