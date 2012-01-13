package mrmarkyb.server.utilities;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProxyServerTest extends HttpScenarioTest {


    @Test
    public void getResponseBodyFromProxy() throws IOException {
        given().aTargetServer().thatReturnsAResponseBodyOf("hello Mark");
        given().aProxyServer();
        when().aGetRequestIsPerformedOn(the().proxiedVersionOf(the().targetUri()));
        assertThat(the().response().body(), is("hello Mark"));
    }

    @Test
    public void getResponseHeaderFromProxy() throws IOException {
        given().aTargetServer().thatReturnsAResponseHeaderOf("foo", "hello hello");
        given().aProxyServer();
        when().aGetRequestIsPerformedOn(the().proxiedVersionOf(the().targetUri()));
        assertThat(the().response().headerValueFor("foo"), is("hello hello"));
    }

    @Test
    public void passesRequestHeaders() throws IOException {
        given().aTargetServer();
        given().aProxyServer();
        given().aClientHeaderOf("sheep", "baa");
        when().aGetRequestIsPerformedOn(the().proxiedVersionOf(the().targetUri())).andCompletes();
        assertThat(the().targetServer().receivedHeaderValueFor("sheep"), is("baa"));
    }


}
