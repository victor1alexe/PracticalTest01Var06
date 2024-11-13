package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button play, compute;
    EditText numere1, numere2, numere3;

    CheckBox hold1, hold2, hold3;

    String numar1, numar2, numar3;

    int total = 0;
    int checked_last = 0;
    String numar1_last, numar2_last, numar3_last;
    List<String> values = new ArrayList<>(Arrays.asList("0", "1", "2", "*"));

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        compute = findViewById(R.id.compute);
        numere1 = findViewById(R.id.numere1);
        numere2 = findViewById(R.id.numere2);
        numere3 = findViewById(R.id.numere3);
        hold1 = findViewById(R.id.hold1);
        hold2 = findViewById(R.id.hold2);
        hold3 = findViewById(R.id.hold3);

        numere1.setText("0");
        numere2.setText("0");
        numere3.setText("0");

        numar1 = numere1.getText().toString();
        numar2 = numere2.getText().toString();
        numar3 = numere3.getText().toString();

        play.setOnClickListener(view -> {
            // Set random values from values list

            String random1 = values.get((int) (Math.random() * values.size()));
            String random2 = values.get((int) (Math.random() * values.size()));
            String random3 = values.get((int) (Math.random() * values.size()));

            // Change only if checkbox is not checked
            if (!hold1.isChecked()) {
                numere1.setText(random1);
                numar1 = random1;
            }
            if (!hold2.isChecked()) {
                numere2.setText(random2);
                numar2 = random2;
            }
            if (!hold3.isChecked()) {
                numere3.setText(random3);
                numar3 = random3;
            }

            String message = "Values: " + numar1 + " " + numar2 + " " + numar3;
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           String gained = result.getData().getStringExtra(Constants.GAINED);
           total += Integer.parseInt(gained);
            Toast.makeText(getApplicationContext(), String.valueOf(total), Toast.LENGTH_LONG).show();
        });

        compute.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
            intent.putExtra(Constants.NUMAR1, numar1);
            intent.putExtra(Constants.NUMAR2, numar2);
            intent.putExtra(Constants.NUMAR3, numar3);
            int checked = 0;
            if (hold1.isChecked()) {
                checked++;
            }
            if (hold2.isChecked()) {
                checked++;
            }
            if (hold3.isChecked()) {
                checked++;
            }

            // if nothing has changed
            if (numar1.equals(numar1_last) && numar2.equals(numar2_last) && numar3.equals(numar3_last)) {
                if (checked == checked_last) {
                    Toast.makeText(getApplicationContext(), String.valueOf(total), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            numar1_last = numar1;
            numar2_last = numar2;
            numar3_last = numar3;
            checked_last = checked;

            intent.putExtra(Constants.NR_CHECKED, checked);
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save total
        outState.putInt(Constants.GAINED, total);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // restore total
        total = savedInstanceState.getInt(Constants.GAINED);
    }
}