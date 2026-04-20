package sistemmobil;

public class Mobil {

    // 1. Atribut (variabel dalam class)
    String merk;
    int tahun;
    double harga;
    boolean statusNyala;

    // 2. Constructor tanpa parameter
    Mobil() {
        System.out.println("Mobil default dibuat");
        merk = "Unknown";
        tahun = 2000;
        harga = 100000000;
        statusNyala = false;
    }

    // 3. Constructor dengan parameter
    Mobil(String merk, int tahun, double harga) {
        this.merk = merk;
        this.tahun = tahun;
        this.harga = harga;
        this.statusNyala = false;
    }

    // 4. Method tanpa return (void)
    void nyalakanMobil() {
        statusNyala = true;
        System.out.println("Mobil " + merk + " dinyalakan");
    }

    void matikanMobil() {
        statusNyala = false;
        System.out.println("Mobil " + merk + " dimatikan");
    }

    void tampilInfo() {
        System.out.println("Merk   : " + merk);
        System.out.println("Tahun  : " + tahun);
        System.out.println("Harga  : " + harga);
        System.out.println("Status : " + (statusNyala ? "Nyala" : "Mati"));
    }

    // 5. Method dengan return
    int hitungUmur() {
        return 2026 - tahun;
    }

    String kategoriHarga() {
        if (harga > 300000000) {
            return "Mobil Mewah";
        } else if (harga > 150000000) {
            return "Mobil Menengah";
        } else {
            return "Mobil Murah";
        }
    }
}