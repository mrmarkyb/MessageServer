import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: mburnett
 * Date: 02/08/11
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class MyResponse {
    private URLConnection urlConnection;

    public MyResponse(URLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    public String body() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        streamBodyTo(output);
        return new String(output.toByteArray());
    }

    private void streamBodyTo(ByteArrayOutputStream output) {
        try {
            InputStream inputStream = urlConnection.getInputStream();

            byte readBuffer[] = new byte[1];
            while (-1 != inputStream.read(readBuffer)) {
                output.write(readBuffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String headerValueFor(String headerName) {
        return urlConnection.getHeaderField(headerName);
    }

    public void andCompletes() {
        body();
    }
}
