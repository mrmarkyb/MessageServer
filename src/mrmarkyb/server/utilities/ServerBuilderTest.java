package mrmarkyb.server.utilities;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ServerBuilderTest extends HttpScenarioTest {


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
        assertThat(the().targetServer().receivedHeaderValueFor("thingamy"), is("whatsit"));
    }


}
