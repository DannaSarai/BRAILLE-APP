import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void testMainRunsWithoutException() {
        // Simple test to check if main runs without throwing exceptions
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            assert false : "Main.main threw an exception: " + e.getMessage();
        }
    }
}
