public class MahasiswaAktif extends Mahasiswa {

    // =================================
    // ATRIBUT TAMBAHAN
    // =================================
    String organisasi;
    int jumlahSKS;

    // =================================
    // CONSTRUCTOR
    // =================================
    public MahasiswaAktif(
            String nim,
            String nama,
            String jurusan,
            int semester,
            double ipk,
            String organisasi,
            int jumlahSKS) {

        super(nim, nama, jurusan, semester, ipk);

        this.organisasi = organisasi;
        this.jumlahSKS = jumlahSKS;
    }

    // =================================
    // METHOD TAMBAHAN 1
    // =================================
    public void tampilOrganisasi() {

        System.out.println("Organisasi : " + organisasi);
    }

    // =================================
    // METHOD TAMBAHAN 2
    // =================================
    public String statusSKS() {

        if (jumlahSKS >= 20) {
            return "SKS Penuh";
        } else {
            return "SKS Belum Penuh";
        }
    }
}