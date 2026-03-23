import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Ujian etsStrukdat = new UjianDepartemen(
            "ETS Struktur Data",
            "07-04-2026",
            "Struktur Data",
            "TW 2 705", "Mid Project", "14-04-2026"
        );

        Ujian etsKalkulus2 = new UjianSKPB(
            "ETS Kalkulus 2",
            "08-04-2026",
            "Kalkulus 2",
            "TW 1 601",
            5,
            90
        );

        Mahasiswa mahasiswa = new Mahasiswa(
            "Evandra Raditya",
            "5027251001@student.its.ac.id",
            "5027251001",
            2025,
            2,
            "Teknologi Informasi"
        );

        Dosen dosen1 = new Dosen(
            "Hafara Firdausi",
            "hafara@its.ac.id",
            "0019099801",
            "Dosen",
            "Struktur Data"
        );

        Dosen dosen2 = new Dosen("Alvian Alif Hidayatullah", "alvianalifh@its.ac.id", "0019099802", "Dosen", "Kalkulus 2");

        System.out.println("=== SIMULASI UJIAN ETS ITS ===");

        mahasiswa.login();
        dosen1.login();
        dosen2.login();

        System.out.println("======");
        
        dosen1.buatSoal(etsStrukdat);
        dosen2.buatSoal(etsKalkulus2);

        mahasiswa.daftarUjian(etsStrukdat);
        mahasiswa.daftarUjian(etsKalkulus2);

        System.out.println("======");

        mahasiswa.lihatJadwalUjian();

        System.out.println("======");

        etsStrukdat.mulaiUjian();
        dosen1.awasiUjian(etsStrukdat);
        mahasiswa.kerjakanUjian(etsStrukdat);
        mahasiswa.submitUjian(etsStrukdat);
        etsStrukdat.selesaikanUjian();

        System.out.println("======");

        etsKalkulus2.mulaiUjian();
        dosen2.awasiUjian(etsKalkulus2);
        mahasiswa.kerjakanUjian(etsKalkulus2);
        mahasiswa.submitUjian(etsKalkulus2);
        etsKalkulus2.selesaikanUjian();

        System.out.println("======");

        dosen1.inputNilai(mahasiswa, etsStrukdat, 95.5);
        dosen1.validasiNilai(mahasiswa, etsStrukdat);
        dosen2.inputNilai(mahasiswa, etsKalkulus2, 91.0);
        dosen2.validasiNilai(mahasiswa, etsKalkulus2);

        System.out.println("======");

        mahasiswa.lihatNilai();

        System.out.println("======");

        mahasiswa.logout();
        dosen1.logout();
        dosen2.logout();
    }
}

abstract class Orang {
    protected String nama;
    protected String email;
    protected boolean loginAktif;

    public Orang(String nama, String email) {
        this.nama = nama;
        this.email = email;
        this.loginAktif = false;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void login() {
        loginAktif = true;
        System.out.println(nama + " berhasil login.");
    }

    public void logout() {
        loginAktif = false;
        System.out.println(nama + " logout.");
    }

    public abstract void lihatJadwalUjian();
}

class Mahasiswa extends Orang {
    private String nrp;
    private int angkatan;
    private int semester;
    private String departemen;
    private String statusUjian;
    private final List<Ujian> daftarUjianAktif;
    private final Map<String, Double> nilaiUjian;

    public Mahasiswa(String nama, String email, String nrp, int angkatan, int semester, String departemen) {
        super(nama, email);
        this.nrp = nrp;
        this.angkatan = angkatan;
        this.semester = semester;
        this.departemen = departemen;
        this.statusUjian = "BELUM_DAFTAR";
        this.daftarUjianAktif = new ArrayList<>();
        this.nilaiUjian = new LinkedHashMap<>();
    }

    public void daftarUjian(Ujian ujian) {
        if (daftarUjianAktif.contains(ujian)) {
            System.out.println(nama + " sudah terdaftar pada " + ujian.getNama() + ".");
            return;
        }
        daftarUjianAktif.add(ujian);
        statusUjian = "TERDAFTAR";
        System.out.println(nama + " mendaftar " + ujian.getNama() + " (" + ujian.getMataKuliah() + ").");
    }

    public void kerjakanUjian(Ujian ujian) {
        if (!daftarUjianAktif.contains(ujian)) {
            System.out.println(nama + " belum terdaftar pada " + ujian.getNama() + ".");
            return;
        }
        statusUjian = "MENGERJAKAN";
        System.out.println(nama + " mulai mengerjakan " + ujian.getNama() + ".");
    }

    public void submitUjian(Ujian ujian) {
        if (!daftarUjianAktif.contains(ujian)) {
            System.out.println(nama + " belum terdaftar pada " + ujian.getNama() + ".");
            return;
        }
        statusUjian = "SUBMIT";
        System.out.println(nama + " submit " + ujian.getNama() + ".");
    }

    public void lihatNilai() {
        System.out.println("Rekap nilai " + nama + ":");
        if (nilaiUjian.isEmpty()) {
            System.out.println("- Belum ada nilai.");
            return;
        }

        for (Map.Entry<String, Double> entry : nilaiUjian.entrySet()) {
            System.out.println("- " + entry.getKey() + " : " + entry.getValue());
        }
    }

    public void terimaNilai(Ujian ujian, double nilai) {
        nilaiUjian.put(ujian.getNama(), nilai);
    }

    @Override
    public void lihatJadwalUjian() {
        System.out.println("Jadwal ujian " + nama + ":");
        if (daftarUjianAktif.isEmpty()) {
            System.out.println("- Belum ada jadwal.");
            return;
        }

        for (Ujian ujian : daftarUjianAktif) {
            System.out.println("- " + ujian.getNama() + " | " + ujian.getTanggal() + " | " + ujian.getTempat());
        }
    }

    public String getNrp() {
        return nrp;
    }

    public int getAngkatan() {
        return angkatan;
    }

    public int getSemester() {
        return semester;
    }

    public String getDepartemen() {
        return departemen;
    }

    public String getStatusUjian() {
        return statusUjian;
    }
}

class Dosen extends Orang {
    private String nidn;
    private String jabatan;
    private String mataKuliahAmpu;

    public Dosen(String nama, String email, String nidn, String jabatan, String mataKuliahAmpu) {
        super(nama, email);
        this.nidn = nidn;
        this.jabatan = jabatan;
        this.mataKuliahAmpu = mataKuliahAmpu;
    }

    public void buatSoal(Ujian ujian) {
        System.out.println("Dosen " + nama + " membuat soal untuk " + ujian.getNama() + ".");
    }

    public void inputNilai(Mahasiswa mahasiswa, Ujian ujian, double nilai) {
        mahasiswa.terimaNilai(ujian, nilai);
        System.out.println("Nilai " + mahasiswa.getNama() + " untuk " + ujian.getNama() + " diinput: " + nilai);
    }

    public void awasiUjian(Ujian ujian) {
        System.out.println("Dosen " + nama + " mengawasi " + ujian.getNama() + " di " + ujian.getTempat() + ".");
    }

    public void validasiNilai(Mahasiswa mahasiswa, Ujian ujian) {
        System.out.println("Dosen " + nama + " memvalidasi nilai " + mahasiswa.getNama() + " untuk " + ujian.getNama() + ".");
    }

    @Override
    public void lihatJadwalUjian() {
        System.out.println("Jadwal pengawasan dosen " + nama + " untuk matkul " + mataKuliahAmpu + ".");
    }

    public String getNidn() {
        return nidn;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getMataKuliahAmpu() {
        return mataKuliahAmpu;
    }
}

abstract class Ujian {
    protected String nama;
    protected String tanggal;
    protected String status;
    protected String mataKuliah;
    protected String tempat;

    public Ujian(String nama, String tanggal, String mataKuliah, String tempat) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.mataKuliah = mataKuliah;
        this.tempat = tempat;
        this.status = "TERJADWAL";
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void mulaiUjian() {
        this.status = "BERLANGSUNG";
        System.out.println("Ujian " + nama + " dimulai di " + tempat + ".");
    }

    public void selesaikanUjian() {
        this.status = "SELESAI";
        System.out.println("Ujian " + nama + " selesai.");
    }
}

class UjianSKPB extends Ujian {
    private int jumlahSoal;
    private int durasiMenit;

    public UjianSKPB(String nama, String tanggal, String mataKuliah, String tempat, int jumlahSoal, int durasiMenit) {
        super(nama, tanggal, mataKuliah, tempat);
        this.jumlahSoal = jumlahSoal;
        this.durasiMenit = durasiMenit;
    }

    public int getJumlahSoal() {
        return jumlahSoal;
    }

    public void setJumlahSoal(int jumlah) {
        this.jumlahSoal = jumlah;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(int durasi) {
        this.durasiMenit = durasi;
    }
}

class UjianDepartemen extends Ujian {
    private String jenisPenilaian;
    private String deadline;

    public UjianDepartemen(String nama, String tanggal, String mataKuliah, String tempat, String jenisPenilaian, String deadline) {
        super(nama, tanggal, mataKuliah, tempat);
        this.jenisPenilaian = jenisPenilaian;
        this.deadline = deadline;
    }

    public String getJenisPenilaian() {
        return jenisPenilaian;
    }

    public void setJenisPenilaian(String jenis) {
        this.jenisPenilaian = jenis;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
