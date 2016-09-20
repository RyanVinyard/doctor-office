import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatientTest {

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
  public void Patient_instantiatesCorrectly_true() {
    Patient testPatient = new Patient("Dan Dingle", 1);
    assertTrue(testPatient instanceof Patient);
  }

  @Test
  public void Patient_instantiatesWithName_String() {
    Patient testPatient = new Patient("Dan Dingle", 1);
    testPatient.save();
    testPatient.updateName("Jongy Brogan");
    assertEquals("Jongy Brogan", testPatient.getName());
    assertEquals("Jongy Brogan", Patient.find(testPatient.getId()).getName());
  }
}
