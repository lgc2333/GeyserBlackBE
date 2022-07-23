import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLog {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("TestLog");
        logger.setLevel(Level.ALL);
    }
}
