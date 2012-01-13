package mrmarkyb.server.utilities;

import org.junit.After;
import org.junit.Before;

public class HttpScenarioTest {
    protected HttpScenario httpScenario;

    @Before
    public void setUp() throws Exception {
        httpScenario = new HttpScenario();
    }

    @After
    public void tearDown() {
        httpScenario.stop();
    }

    protected HttpScenario given() {
        return httpScenario;
    }

    protected HttpScenario when() {
        return httpScenario;
    }

    protected HttpScenario the() {
        return httpScenario;
    }
}
