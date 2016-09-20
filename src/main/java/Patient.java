import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Patient {
  private String name;
  private int id;
  private int doctorId;

  public Patient(String name, int doctorId) {
    this.name = name;
    this.doctorId = doctorId;
    this.save();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public static List<Patient> all() {
    String sql = "SELECT * FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName()) &&
             this.getDoctorId() == (newPatient.getDoctorId()) &&
             this.getId() == (newPatient.getDoctorId());

    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients(name, doctorId) VALUES (:name, :doctorId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("doctorId", this.doctorId)
        .executeUpdate().getKey();
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      Patient patient = con.createQuery("SELECT * FROM patients where id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Patient.class);
      return patient;
    }
  }

  public void updateName(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patients SET name = :walkergirl WHERE id = :id";
      con.createQuery(sql)
        .addParameter("walkergirl", newName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
