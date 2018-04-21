package com.example.biblereading;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager = CallbackManager.Factory.create();
    private static final String EMAIL = "email";
    LoginButton loginButton;
//    private ProgressDialog dialog;
    private FirebaseAuth mAuth;

    private static final String TAG = "FacebookLogin";
    TextView mStatusTextView;
    TextView mDetailTextView;
    boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

//        dialog = new ProgressDialog(this);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mStatusTextView = (TextView) findViewById(R.id.display_name);
        mDetailTextView = (TextView) findViewById(R.id.Uid);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        // If you are using in a fragment
        // loginButton.setFragment(this);

        // Callback registration
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile",""));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                if (dialog.isShowing()) {
//                    dialog.dismiss();}
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                startActivity(new Intent(LoginActivity.this, MainMenu.class));
                finish();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
//        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        // Check if user is signed in (non-null) and update UI accordingly.
        if (!loggedIn) {
            startActivity(new Intent(LoginActivity.this, MainMenu.class));
            finish();
        }
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Doing something, please wait.");
//        dialog.show();
//        //
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText("Display : " + user.getDisplayName());
            mDetailTextView.setText("Uid : " + user.getUid());

            findViewById(R.id.login_button).setVisibility(View.GONE);
//            findViewById(R.id.button_facebook_signout).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.login_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.button_facebook_signout).setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_exit);
        builder.setIcon(R.drawable.icon_app);
        builder.setMessage(R.string.message_exit);

        builder.setPositiveButton(R.string.Yes_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finishAndRemoveTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.exit(0);
                int pid = Process.myPid();
                Process.killProcess(pid);
            }
        });
        builder.setNegativeButton(R.string.No_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
