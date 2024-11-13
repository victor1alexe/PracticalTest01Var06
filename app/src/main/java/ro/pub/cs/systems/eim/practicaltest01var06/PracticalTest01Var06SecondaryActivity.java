package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    TextView gained;
    Button ok;

    String gained_value;
    String numar1, numar2, numar3;
    int no_checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);

        gained = findViewById(R.id.gained);
        ok = findViewById(R.id.ok);

        gained.setText("");

        Intent receivedIntent = getIntent();
        if (receivedIntent != null && receivedIntent.getExtras().containsKey(Constants.NUMAR1)) {
            numar1 = receivedIntent.getStringExtra(Constants.NUMAR1);
        }
        if (receivedIntent != null && receivedIntent.getExtras().containsKey(Constants.NUMAR2)) {
            numar2 = receivedIntent.getStringExtra(Constants.NUMAR2);
        }
        if (receivedIntent != null && receivedIntent.getExtras().containsKey(Constants.NUMAR3)) {
            numar3 = receivedIntent.getStringExtra(Constants.NUMAR3);
        }
        if (receivedIntent != null && receivedIntent.getExtras().containsKey(Constants.NR_CHECKED)) {
            no_checked = receivedIntent.getIntExtra(Constants.NR_CHECKED, -1);
        }

//        Toast.makeText(this, "Numerele sunt: " + numar1 + " " + numar2 + " " + numar3, Toast.LENGTH_LONG).show();

        boolean condition1 = numar1.equals(numar2) && numar2.equals(numar3) && numar1.equals(numar3);
        boolean condition2 = numar1.equals("*") && numar2.equals(numar3);
        boolean condition3 = numar2.equals("*") && numar1.equals(numar3);
        boolean condition4 = numar3.equals("*") && numar1.equals(numar2);
        boolean condition5 = numar1.equals("*") && numar2.equals("*");
        boolean condition6 = numar2.equals("*") && numar3.equals("*");
        boolean condition7 = numar1.equals("*") && numar3.equals("*");

        // check if all numbers are equal or asterisk
        if (condition1 || condition2 || condition3 || condition4 || condition5 || condition6 || condition7) {
            switch (no_checked) {
                case 0:
                    gained_value = "Gained 100";
                    break;
                case 1:
                    gained_value = "Gained 50";
                    break;
                case 2:
                    gained_value = "Gained 10";
                    break;
                case 3:
                    gained_value = "";
                    break;
            }
        }

        gained.setText(gained_value);

        ok.setOnClickListener(view -> {
            Intent intentResult = new Intent();
            // if textview contains gained get the value and send it back
            if (gained.getText().toString().contains("Gained")) {
    // get the value of gained after the word Gained
                String gainedValue = gained.getText().toString().substring(7);
                intentResult.putExtra(Constants.GAINED, gainedValue);
            } else {
                intentResult.putExtra(Constants.GAINED, "0");
            }
            setResult(RESULT_OK, intentResult);
            finish();
        });
    }
}