import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Doctor {
  private String specialty;
  private List<Patient> patients;
  private int id;

  public Doctor(String specialty) {
    this.specialty = specialty;
    this.save();
  }

  public String getSpecialty() {
    return specialty;
  }

  public List<Patient> getPatients() {
    return patients;
  }

  public int getId() {
    return id;
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, specialty, patients FROM doctor";
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
      String sql = "INSERT INTO doctor(specialty, patients) VALUES (:specialty, :patients)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("specialty", this.specialty)
        .addParameter("patients", this.patients)
        .executeUpdate()
        .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      Doctor doctor = con.createQuery("SELECT * FROM doctor where id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }
}
