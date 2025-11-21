public class PaketSameDay extends Paket {
    public PaketSameDay(String nama) {
        super(nama, "SameDay");
        this.fee = 25000;
        this.eta = 8;
    }

    @Override
    public void detailPaket() {
        super.detailPaket();
        System.out.println("Estimasi Waktu: " + eta + " Jam");
        System.out.println("Biaya tambahan: " + fee);
        System.out.println();
    }
}