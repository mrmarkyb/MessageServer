import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 25/07/11
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class MyServer {
    protected HttpServer httpServer;

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
