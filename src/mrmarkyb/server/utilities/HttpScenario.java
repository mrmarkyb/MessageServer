package mrmarkyb.server.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpScenario {
    private ServerBuilder serverBuilder = new ServerBuilder();
    private MyResponse theResponse;
    private List<Resource.ClientHeader> clientHeaders = new ArrayList<Resource.ClientHeader>();
    private ProxyServer proxyServer;

    public ServerBuilder aTargetServer() {
        return serverBuilder;
    }

    public void aProxyServer() throws IOException {
        proxyServer = new ProxyServer(8071, "/proxyit");
    }

    public String targetUri() {
        return serverBuilder.theUri();
    }

    public MyResponse aGetRequestIsPerformedOn(String uri) throws IOException {
        theResponse = new Resource(uri).getResponse(clientHeaders);
        return theResponse;
    }

    public void aClientHeaderOf(String name, String value) {
        clientHeaders.add(new Resource.ClientHeader(name, value));
    }

    public ServerBuilder targetServer() {
        return serverBuilder;
    }

    public String proxiedVersionOf(String targetUri) {
        return String.format("http://localhost:8071/proxyit?to=%s", targetUri);
    }

    public void stop() {
        if (proxyServer != null) {
            proxyServer.stop();
        }
        serverBuilder.stop();
    }

    public MyResponse response() {
        return theResponse;
    }
}
