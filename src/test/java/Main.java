import java.util.ArrayList;
import java.util.List;

public class Main {

    private List<TestInterface> loadTests() {
        List<TestInterface> tests = new ArrayList<>();
        tests.add(new CacheablesTest());
        tests.add(new FileStorageHandlerTest());
        return tests;
    }

    public static void main(String[] args) {
        Main main = new Main();
        for(TestInterface test : main.loadTests()) {
            test.run();
        }
    }

}
