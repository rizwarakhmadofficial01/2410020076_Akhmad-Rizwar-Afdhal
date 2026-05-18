public class Mahasiswa {

    // =================================
    // ATRIBUT
    // =================================
    String nim;
    String nama;
    String jurusan;
    int semester;
    double ipk;

    // =================================
    // CONSTRUCTOR 1
    // =================================
    public Mahasiswa() {
        System.out.println("Constructor Kosong Dipanggil");
    }

    // =================================
    // CONSTRUCTOR 2
    // =================================
    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    // =================================
    // CONSTRUCTOR 3
    // =================================
    public Mahasiswa(
            String nim,
            String nama,
            String jurusan,
            int semester,
            double ipk) {

        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.semester = semester;
        this.ipk = ipk;
    }

    // =================================
    // METHOD VOID
    // =================================
    public void tampilData() {

        System.out.println("NIM       : " + nim);
        System.out.println("Nama      : " + nama);
        System.out.println("Jurusan   : " + jurusan);
        System.out.println("Semester  : " + semester);
        System.out.println("IPK       : " + ipk);
    }

    // =================================
    // METHOD RETURN
    // =================================
    public String getStatus() {

        if (ipk >= 3.5) {
            return "Mahasiswa Berprestasi";
        } else {
            return "Mahasiswa Reguler";
        }
    }

    // =================================
    // CREATE
    // =================================
    public void createData() {

        String sql =
        "INSERT INTO mahasiswa VALUES "
        + "('" + nim + "',"
        + "'" + nama + "',"
        + "'" + jurusan + "',"
        + "'" + semester + "',"
        + "'" + ipk + "')";

        System.out.println("\nSQL CREATE:");
        System.out.println(sql);
    }

    // =================================
    // READ
    // =================================
    public void readData() {

        String sql =
        "SELECT * FROM mahasiswa";

        System.out.println("\nSQL READ:");
        System.out.println(sql);
    }

    // =================================
    // UPDATE
    // =================================
    public void updateData() {

        String sql =
        "UPDATE mahasiswa SET "
        + "nama='" + nama + "', "
        + "jurusan='" + jurusan + "', "
        + "semester='" + semester + "', "
        + "ipk='" + ipk + "' "
        + "WHERE nim='" + nim + "'";

        System.out.println("\nSQL UPDATE:");
        System.out.println(sql);
    }

    // =================================
    // DELETE
    // =================================
    public void deleteData() {

        String sql =
        "DELETE FROM mahasiswa "
        + "WHERE nim='" + nim + "'";

        System.out.println("\nSQL DELETE:");
        System.out.println(sql);
    }
}