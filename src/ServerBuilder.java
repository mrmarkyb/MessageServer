import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class ServerBuilder extends MyServer {
    private int port = 8070;
    private String contextPath = "/context";
    private String payload = "a payload";
    private String headerName = "dummy";
    private String headerValue = "value";
    private Headers lastRequestHeaders;

    public ServerBuilder onPort(int port) {
        this.port = port;
        return this;
    }

    public ServerBuilder withContext(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public void thatReturnsAResponseBodyOf(String payload) {
        this.payload = payload;
    }

    public String theUri() {
        try {
            createHttpServer(port, contextPath, new HttpHandler() {
                public void handle(HttpExchange httpExchange) throws IOException {
                    byte[] bytes = payload.getBytes();
                    lastRequestHeaders = httpExchange.getRequestHeaders();
                    httpExchange.getResponseHeaders().add(headerName, headerValue);
                    httpExchange.sendResponseHeaders(200, bytes.length);
                    httpExchange.getResponseBody().write(bytes);
                }
            });
            start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.format("http://localhost:%d%s", port, contextPath);
    }

    public void thatReturnsAResponseHeaderOf(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public String receivedHeaderValueFor(String name) {
        assertNotNull(lastRequestHeaders);
        assertTrue(lastRequestHeaders.containsKey(name));
        return lastRequestHeaders.get(name).get(0);  //To change body of created methods use File | Settings | File Templates.
    }
}
