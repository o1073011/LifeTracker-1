package tw.pu.edu.gm.o1073011.lifetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.loginactbtn);
        register_btn = findViewById(R.id.registeractbtn);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginactbtn:
                startActivity(new Intent(this,LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                return;

            case R.id.registeractbtn:
                startActivity(new Intent(this,RegistrationActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                return;
        }
    }
}