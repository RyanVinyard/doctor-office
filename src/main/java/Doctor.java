import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Doctor {
  private String name;
  private String specialty;
  private List<Patient> patients;
  private int id;

  public Doctor(String name, String specialty) {
    this.name = name;
    this.specialty = specialty;
    this.save();
  }

  public String getSpecialty() {
    return specialty;
  }

  public List<Patient> getPatients() {
    return patients;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, specialty, patients FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getId() == newDoctor.getId() &&
             this.getSpecialty().equals(newDoctor.getSpecialty()) &&
             this.getPatients().equals(newDoctor.getPatients());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors(specialty, patients, name) VALUES (:specialty, :patients, :name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("specialty", this.specialty)
        .addParameter("patients", this.patients)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      Doctor doctor = con.createQuery("SELECT * FROM doctors where id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }
}
