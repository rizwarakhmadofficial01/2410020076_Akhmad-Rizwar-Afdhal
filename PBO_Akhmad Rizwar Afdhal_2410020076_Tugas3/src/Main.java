public class Main {

    public static void main(String[] args) {

        // =================================
        // OBJECT 1
        // =================================
        System.out.println("===== OBJECT 1 =====");

        Mahasiswa mhs1 = new Mahasiswa();

        // =================================
        // OBJECT 2
        // =================================
        System.out.println("\n===== OBJECT 2 =====");

        Mahasiswa mhs2 =
        new Mahasiswa("22001", "Akhmad");

        mhs2.tampilData();

        // =================================
        // OBJECT 3
        // =================================
        System.out.println("\n===== OBJECT 3 =====");

        Mahasiswa mhs3 =
        new Mahasiswa(
                "22002",
                "Rizwar",
                "Informatika",
                4,
                3.80);

        mhs3.tampilData();

        System.out.println("\nStatus:");
        System.out.println(mhs3.getStatus());

        // =================================
        // CRUD
        // =================================
        mhs3.createData();
        mhs3.readData();
        mhs3.updateData();
        mhs3.deleteData();

        // =================================
        // CLASS TURUNAN
        // =================================
        System.out.println("\n===== CLASS TURUNAN =====");

        MahasiswaAktif aktif =
        new MahasiswaAktif(
                "22003",
                "Afdhal",
                "Sistem Informasi",
                6,
                3.60,
                "BEM",
                24);

        aktif.tampilData();

        aktif.tampilOrganisasi();

        System.out.println("\nStatus:");
        System.out.println(aktif.getStatus());

        System.out.println("\nStatus SKS:");
        System.out.println(aktif.statusSKS());
    }
}