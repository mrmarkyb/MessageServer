package mrmarkyb.server.utilities;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class ServerBuilder {
    private int port = 8070;
    private String contextPath = "/context";
    private String payload = "a payload";
    private String headerName = "dummy";
    private String headerValue = "value";
    private Headers lastRequestHeaders;
    protected HttpServer httpServer;

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

    protected HttpServer createHttpServer(int port, String path, HttpHandler httpHandler) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 10);
        httpServer.createContext(path, httpHandler);
        return httpServer;
    }

    protected void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}
