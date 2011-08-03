import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
* Created by IntelliJ IDEA.
* User: mburnett
* Date: 02/08/11
* Time: 19:40
* To change this template use File | Settings | File Templates.
*/
public class ServerBuilder extends MyServer{
    private int port = 8070;
    private String contextPath = "/context";
    private String payload = "a payload";
    private String headerName = "dummy";
    private String headerValue = "value";

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
}
