package tw.pu.edu.gm.o1073011.lifetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    EditText email;
    EditText password;
    Button login;
    String eml;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_edt);
        password = findViewById(R.id.pwd_edt);
        login = findViewById(R.id.signinbtn);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        eml = email.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(eml)) {
            email.setError("Email Required");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Password Required");
            return;
        }

        auth.signInWithEmailAndPassword(eml, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(getApplicationContext(), "Wrong Email", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException){
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        } else
                        Toast.makeText(getApplicationContext(), "Sign In Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}