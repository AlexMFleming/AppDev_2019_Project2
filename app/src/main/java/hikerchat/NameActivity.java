package hikerchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hiker.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 *  Login screen with anonymous nickname
 */

public class NameActivity extends Activity {

    private EditText mUsernameView;
    private String mUsername;
    private Socket mSocket;

    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SocketApplication app = (SocketApplication) getApplication();
        mSocket = app.getSocket();

        //Passed from LoginActivity
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && !bundle.isEmpty())
            userEmail = bundle.getString("email");
        //Direct activity access results in empty bundle
        if (userEmail.isEmpty() || userEmail == null) {
            //Setup layout for creating a nickname
            setContentView(R.layout.activity_chatnickname);

            // Hook up login form and a listener
            mUsernameView = (EditText) findViewById(R.id.username_input);
            mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin("anon");
                        return true;
                    }
                    Log.e("onEditorAction Error", "Error Occurred.");
                    return false;
                }
            });

            Button signInButton = (Button) findViewById(R.id.sign_in_button);
            signInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin("anon");
                }
            });

        } else {
            //If userEmail was passed then we want to use that as the username
            mUsername = trimEmail(userEmail);
            attemptLogin(mUsername);
            Log.i("Bundle: ", "Not empty");
        }

        mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    //    mSocket.off("login", onLogin);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String input) {
        String username;

        if (input.equals("anon")) {
            Log.i("Condition: ", "Anonymous");
            // Reset errors.
            mUsernameView.setError(null);

            // Store values at the time of the login attempt.
            username = mUsernameView.getText().toString().trim();

            // Check for a valid username.
            if (TextUtils.isEmpty(username)) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                mUsernameView.setError(getString(R.string.error_field_required));
                mUsernameView.requestFocus();
                return;
            }
            mUsername = username;
        } else {
            Log.i("Condition: ", input);
            username = input;
            mUsername = input;
        }

        // perform the user login attempt.
        mSocket.emit("add user", username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.i("onLogin: ", "Reached.");

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
                Log.i("Server response", String.valueOf(numUsers));
            } catch (JSONException e) {
                Log.e("JSON getInt Error", "Error Occurred.");
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("username", mUsername);
            intent.putExtra("numUsers", numUsers);

            Log.i("onLogin2: ", "Reached.");

            setResult(RESULT_OK, intent);

            Log.i("onLogin3: ", "Reached.");
            finish();
        }
    };

    private String trimEmail(String email) {
        int index = email.indexOf('@');
        String user = email.substring(0,index);
        return user;
    }
}



