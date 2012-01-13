package mrmarkyb.server.utilities;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 26/07/11
 * Time: 08:15
 * To change this template use File | Settings | File Templates.
 */
public class Resource {
    private String uri;

    public Resource(String uri) {
        this.uri = uri;
    }


    public MyResponse getResponse() throws IOException {
        return getResponse(new ArrayList<ClientHeader>());
    }


    public MyResponse getResponse(List<ClientHeader> clientHeaders) throws IOException {
        URLConnection urlConnection = new URL(uri).openConnection();
        for (ClientHeader clientHeader : clientHeaders) {
            urlConnection.addRequestProperty(clientHeader.name, clientHeader.value);
        }
        return new MyResponse(urlConnection);
    }

    public static class ClientHeader {
        private String name;
        private String value;

        public ClientHeader(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
