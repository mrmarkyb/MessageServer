import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 22/07/11
 * Time: 07:59
 * To change this template use File | Settings | File Templates.
 */
public class MessageServer extends MyServer {

    MessageServer(int port, String message) throws IOException {
        createHttpServer(port, "/message", messageProducingHandler(message));
        start();
    }


    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            new MessageServer(8000 + i, "hello " + i);
        }

    }

    private HttpHandler messageProducingHandler(final String message) {
        return new HttpHandler() {
            public void handle(HttpExchange httpExchange) throws IOException {
                String response = message;
                httpExchange.sendResponseHeaders(200, response.length());
                httpExchange.getResponseBody().write(response.getBytes());
                httpExchange.getResponseBody().close();
            }
        };
    }

}
