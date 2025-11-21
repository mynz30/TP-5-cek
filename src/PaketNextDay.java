public class PaketNextDay extends Paket {
    public PaketNextDay(String nama) {
        super(nama, "NextDay");
        this.fee = 15000;
        this.eta = 24;
    }

    @Override
    public void detailPaket() {
        super.detailPaket();
        System.out.println("Estimasi Waktu: " + eta + " Jam");
        System.out.println("Biaya tambahan: " + fee);
        System.out.println();
    }
}