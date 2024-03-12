import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class DataUpdatingTest {
    DataUpdating du=new DataUpdating();
    @Test
    void connectDataBase() {
        du.getDataFromUser();
        assertTrue(du.connectDataBase());
    }

    @Test
    void getDataFromUser() {
        assertTrue(du.getDataFromUser());
    }
}