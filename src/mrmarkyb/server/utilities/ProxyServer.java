package mrmarkyb.server.utilities;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 25/07/11
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class ProxyServer {

    protected HttpServer httpServer;

    public ProxyServer(int port, String context) throws IOException {
        createHttpServer(port, context, proxyHandler());
        start();
    }

    public static void main(String[] args) throws IOException {
        new ProxyServer(8079, "/proxyit");
    }

    public static String getToParam(String url) {
        return url.replaceAll(".*?\\?to=", "");
    }

    private HttpHandler proxyHandler() {
        return new HttpHandler() {
            public void handle(HttpExchange httpExchange) throws IOException {
                try {
                    Headers requestHeaders = httpExchange.getRequestHeaders();
                    String target = getToParam(httpExchange.getRequestURI().toString());
                    URLConnection urlConnection = new URL(target).openConnection();
                    for (String headerName : requestHeaders.keySet()) {
                        if (headerName.equals("Host")) continue;
                        List<String> values = requestHeaders.get(headerName);
                        for (String value : values) {
                            urlConnection.setRequestProperty(headerName, value);
                        }
                    }

                    InputStream inputStream = urlConnection.getInputStream();
                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    for (String headerName : headerFields.keySet()) {
                        if (null== headerName || "Host".equals(headerName)) continue;
                        List<String> values = headerFields.get(headerName);
                        for (String value : values) {
                            httpExchange.getResponseHeaders().add(headerName, value);
                        }
                    }
                    List<String> strings = headerFields.get(null);
                    int responseCode = Integer.parseInt(strings.get(0).split(" ")[1]);
                    long contentLength = Long.parseLong(headerFields.get("Content-length").get(0));
                    httpExchange.sendResponseHeaders(responseCode, contentLength);

                    OutputStream responseBody = httpExchange.getResponseBody();
                    byte readBuffer[] = new byte[1];
                    while (-1 != inputStream.read(readBuffer)) {
                        responseBody.write(readBuffer);
                    }

                    responseBody.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };
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
