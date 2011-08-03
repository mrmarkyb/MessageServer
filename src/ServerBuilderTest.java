import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
    public void checkHeadersReceived() throws IOException {
        givenATargetServer();
        whenAGetRequestIsPerformedOn(theTargetUri());
    }

    private void whenAGetRequestIsPerformedOn(String uri) throws IOException {
        theResponse = new Resource(uri).getResponse();
    }

    private String theTargetUri() {
        return serverBuilder.theUri();
    }

    private ServerBuilder givenATargetServer() {
        return serverBuilder;
    }



}
