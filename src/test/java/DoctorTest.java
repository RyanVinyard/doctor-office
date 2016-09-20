import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class DoctorTest {

  @Before
  public void openConnection() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @Test
  public void Doctor_instantiatesCorrectly_True() {
    Doctor testDoctor = new Doctor("pediatrician");
    assertTrue(testDoctor instanceof Doctor);
  }
}
