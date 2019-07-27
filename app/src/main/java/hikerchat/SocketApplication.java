package hikerchat;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketApplication extends Application {
    //Free tier AWS EC2 instance
    public static final String EC2 = "http://ec2-34-230-45-91.compute-1.amazonaws.com:8000";

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(EC2);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
