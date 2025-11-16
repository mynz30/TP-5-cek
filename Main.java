public class Main {
    public static void main(String[] args) {
        Paket sameDay = new PaketSameDay("Test1");
        Paket nextDay = new PaketNextDay("Test2");
        Paket newPaket = new PaketNextDay("Test3");

        sameDay.detailPaket();
        nextDay.detailPaket();
        newPaket.detailPaket();

        KurirTetap tetap = new KurirTetap("Bonita", 2);
        KurirFreelance freelance = new KurirFreelance("Sofita", 2);

        tetap.addJob(nextDay);
        freelance.addJob(sameDay);
        freelance.addJob(newPaket);
        tetap.addJob(sameDay);

        // Semua paket diterima
        sameDay.paketDiterima();
        nextDay.paketDiterima();
        newPaket.paketDiterima();

        // Tambah jam terbang freelance
        freelance.tambahJamTerbang(5);

        // Hitung gaji
        tetap.hitungGaji();
        freelance.hitungGaji();

        // Detail kurir setelah gaji dihitung
        System.out.println();
        tetap.detailKurir();
        freelance.detailKurir();

        System.out.println("Total Jam Terbang Sofita: " + freelance.getTotalJamTerbang() + " jam\n");

        sameDay.detailPaket();
        nextDay.detailPaket();
        newPaket.detailPaket();
    }
}
