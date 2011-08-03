import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 25/07/11
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class ServerBuilderTest {

    private HttpScenario httpScenario;

    @Before
    public void setUp() throws Exception {
        httpScenario = new HttpScenario();
    }

    @After
    public void tearDown() {
        httpScenario.stop();
    }


    @Test
    public void getResponseBodyFromUrl() throws IOException {
        given().aTargetServer().thatReturnsAResponseBodyOf("hello Mark");
        when().aGetRequestIsPerformedOn(given().targetUri());
        assertThat(the().response().body(), is("hello Mark"));
    }

    @Test
    public void getResponseHeaderFromUrl() throws IOException {
        given().aTargetServer().thatReturnsAResponseHeaderOf("Myheader", "hello again Mark");
        when().aGetRequestIsPerformedOn(given().targetUri());
        assertThat(the().response().headerValueFor("Myheader"), is("hello again Mark"));
    }

    @Test
    public void checkHeadersReceivedFromClient() throws IOException {
        given().aTargetServer();
        given().aClientHeaderOf("thingamy", "whatsit");
        when().aGetRequestIsPerformedOn(the().targetUri()).andCompletes();
        assertThat(the().targetServer().getHeaderValue("thingamy"), is("whatsit"));
    }

    private HttpScenario given() {
        return httpScenario;
    }

    private HttpScenario when() {
        return httpScenario;
    }

    private HttpScenario the() {
        return httpScenario;
    }


    private class HttpScenario {
    private ServerBuilder serverBuilder = new ServerBuilder();
    private MyResponse theResponse;
    private List<Resource.ClientHeader> clientHeaders = new ArrayList<Resource.ClientHeader>();

        private ServerBuilder aTargetServer() {
            return serverBuilder;
        }

        private String targetUri() {
            return serverBuilder.theUri();
        }

        private MyResponse aGetRequestIsPerformedOn(String uri) throws IOException {
            theResponse = new Resource(uri).getResponse(clientHeaders);
            return theResponse;
        }

        private void aClientHeaderOf(String name, String value) {
            clientHeaders.add(new Resource.ClientHeader(name, value));
        }

        private ServerBuilder targetServer() {
            return serverBuilder;
        }

        public void stop() {
            serverBuilder.stop();
        }

        public MyResponse response() {
            return theResponse;
        }
    }
}
