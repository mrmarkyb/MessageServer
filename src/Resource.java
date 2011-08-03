import com.sun.net.httpserver.Headers;
import org.omg.CORBA.portable.*;

import java.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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
        URLConnection urlConnection = new URL(uri).openConnection();
        return new MyResponse(urlConnection);
    }


}
