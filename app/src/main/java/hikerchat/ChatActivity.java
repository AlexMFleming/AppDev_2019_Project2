package hikerchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hiker.R;

public class ChatActivity extends AppCompatActivity {
    private String parkName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);

        Bundle bundle = getIntent().getExtras();
        parkName = bundle.getString("parkname");
        SocketApplication.stationName = parkName;
    }
}
