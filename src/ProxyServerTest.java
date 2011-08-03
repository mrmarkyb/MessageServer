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
public class ProxyServerTest {


    private ServerBuilder targetServerBuilder;
    private MyResponse theResponse;
    private ProxyServer proxyServer;

    @Before
    public void setUp() throws Exception {
        targetServerBuilder = new ServerBuilder();
    }

    @After
    public void tearDown() {
        targetServerBuilder.stop();
        proxyServer.stop();
    }


    @Test
    public void getResponseBodyFromProxy() throws IOException {
        givenATargetServer().thatReturnsAResponseBodyOf("hello Mark");
        givenAProxyServer();
        whenAGetRequestIsPerformedOn(theProxiedVersionOf(theTargetUri()));
        assertThat(theResponse.body(), is("hello Mark"));
    }

    @Test
    public void getResponseHeaderFromProxy() throws IOException {
        givenATargetServer().thatReturnsAResponseHeaderOf("foo", "hello hello");
        givenAProxyServer();
        whenAGetRequestIsPerformedOn(theProxiedVersionOf(theTargetUri()));
        assertThat(theResponse.headerValueFor("foo"), is("hello hello"));
    }

    private void whenAGetRequestIsPerformedOn(String uri) throws IOException {
        theResponse = new Resource(uri).getResponse();
    }

    private String theTargetUri() {
        return targetServerBuilder.theUri();
    }

    private ServerBuilder givenATargetServer() {
        return targetServerBuilder;
    }

    private String theProxiedVersionOf(String targetUri) {
        return String.format("http://localhost:8071/proxyit?to=%s", targetUri);
    }

    private void givenAProxyServer() throws IOException {
        proxyServer = new ProxyServer(8071, "/proxyit");
    }

}
