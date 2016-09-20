import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class DoctorTest {

  @Before
  public void openConnection() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @After
  public void destroyAndMaimBeyondRecognition() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientQuery = "DELETE FROM patients *;";
      String deleteDoctorQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientQuery).executeUpdate();
      con.createQuery(deleteDoctorQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_True() {
    Doctor testDoctor = new Doctor("Connors", "turning into a lizard and fighting Spiderman");
    assertTrue(testDoctor instanceof Doctor);
  }

  @Test
  public void Doctor_patientListIsPresent_True() {
    Doctor testDoctor = new Doctor("Connors", "turning into a lizard and fighting Spiderman");
    Patient testPatient = new Patient("Peter Parker", testDoctor.getId());
    assertTrue(testDoctor.getPatients().get(0).equals(testPatient));
  }


}
