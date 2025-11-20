package src;
public interface Deliverable {
    void ambilPaket(Paket paket) throws KapasitasPenuhException;
    void kirimPaket(String noTracking) throws PaketTidakDitemukanException;
}