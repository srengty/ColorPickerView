package itc.gic.android.usingsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.knowEnglish);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
                editor.putBoolean("english",checked);
                editor.apply();
            }
        });

        //init value from last saved data
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        boolean english = sharedPreferences.getBoolean("english",false);
        toggleButton.setChecked(english);

        final EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
                editor.putString("color", editText.getText().toString());
                editor.apply();
            }
        });
        editText.setText(sharedPreferences.getString("color",""));
    }
}
