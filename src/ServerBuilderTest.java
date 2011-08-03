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


    private ServerBuilder serverBuilder;
    private MyResponse theResponse;
    private List<Resource.ClientHeader> clientHeaders = new ArrayList<Resource.ClientHeader>();

    @Before
    public void setUp() throws Exception {
        serverBuilder = new ServerBuilder();
    }

    @After
    public void tearDown() {
        serverBuilder.stop();
    }


    @Test
    public void getResponseBodyFromUrl() throws IOException {
        givenATargetServer().thatReturnsAResponseBodyOf("hello Mark");
        whenAGetRequestIsPerformedOn(theTargetUri());
        assertThat(theResponse.body(), is("hello Mark"));
    }

    @Test
    public void getResponseHeaderFromUrl() throws IOException {
        givenATargetServer().thatReturnsAResponseHeaderOf("Myheader", "hello again Mark");
        whenAGetRequestIsPerformedOn(theTargetUri());
        assertThat(theResponse.headerValueFor("Myheader"), is("hello again Mark"));
    }

    @Test
    public void checkHeadersReceivedFromClient() throws IOException {
        givenATargetServer();
        givenAClientHeaderOf("thingamy", "whatsit");
        whenAGetRequestIsPerformedOn(theTargetUri());
        andTheResponse().isReceived();
        assertThat(theTargetServer().getHeaderValue("thingamy"), is("whatsit"));
    }

    private MyResponse andTheResponse() {
        return theResponse;
    }

    private ServerBuilder theTargetServer() {
        return serverBuilder;
    }

    private void givenAClientHeaderOf(String name, String value) {
        clientHeaders.add(new Resource.ClientHeader(name, value));
    }

    private void whenAGetRequestIsPerformedOn(String uri) throws IOException {
        theResponse = new Resource(uri).getResponse(clientHeaders);
    }

    private String theTargetUri() {
        return serverBuilder.theUri();
    }

    private ServerBuilder givenATargetServer() {
        return serverBuilder;
    }


}
