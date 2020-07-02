package tw.pu.edu.gm.o1073011.lifetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnReg;
    private TextView mSignin;

    private ProgressDialog mDialog;

    // firebase...
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark2));
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
        //mDialog=new ProgressDialog(this);

        registration();
    }

    private void registration() {
        mEmail = findViewById(R.id.email_reg);
        mPass = findViewById(R.id.password_reg);
        btnReg = findViewById(R.id.btn_reg);
        mSignin = findViewById(R.id.signin_here);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    mPass.setError("Password Required");
                    return;
                }

                //mDialog.setMessage("Processing...");
                //mDialog.show();

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }else{
                            //mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }
}