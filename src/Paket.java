package src;
import java.util.UUID;

public class Paket {
    protected String noTracking;
    protected String namaPenerima;
    protected String metodePengiriman;
    protected String status;
    protected double fee;
    protected int eta;

    public Paket(String nama, String metode) {
        this.noTracking = UUID.randomUUID().toString();
        this.namaPenerima = nama;
        this.metodePengiriman = metode;
        this.status = "Menunggu Kurir";
    }

    // Getter dan Setter
    public String getNoTracking() { return noTracking; }
    public String getNamaPenerima() { return namaPenerima; }
    public String getMetodePengiriman() { return metodePengiriman; }
    public String getStatus() { return status; }
    public double getFee() { return fee; }
    public int getEta() { return eta; }

    public void setStatus(String status) { this.status = status; }

    public void ubahStatus(String newStatus) {
        this.status = newStatus;
    }

    // Detail paket
    public void detailPaket() {
        System.out.println("=== Info Paket ===");
        System.out.println("Nomor Tracking: " + noTracking);
        System.out.println("Nama Penerima: " + namaPenerima);
        System.out.println("Metode Pengiriman: " + metodePengiriman);
        System.out.println("Status Paket: " + status);
    }
}
