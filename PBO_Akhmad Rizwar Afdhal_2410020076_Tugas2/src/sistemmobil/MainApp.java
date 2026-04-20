package sistemmobil;

public class MainApp {
    public static void main(String[] args) {

        // 1. Object dari constructor kosong
        Mobil mobil1 = new Mobil();

        mobil1.tampilInfo();
        System.out.println("Umur: " + mobil1.hitungUmur());
        System.out.println("Kategori: " + mobil1.kategoriHarga());

        mobil1.nyalakanMobil();
        mobil1.tampilInfo();

        System.out.println("======================");

        // 2. Object dari constructor parameter
        Mobil mobil2 = new Mobil("Honda Civic", 2022, 450000000);

        mobil2.tampilInfo();
        System.out.println("Umur: " + mobil2.hitungUmur());
        System.out.println("Kategori: " + mobil2.kategoriHarga());

        mobil2.nyalakanMobil();
        mobil2.matikanMobil();
    }
}