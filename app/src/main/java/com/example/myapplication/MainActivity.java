package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    RadioGroup rgConvert;
    ArrayList<RadioButton> radios;
    RadioButton usdRadio;
    RadioButton jpyRadio;
    RadioButton gbpRadio;
    RadioButton cadRadio;
    RadioButton aedRadio;
    Button convertButton;

    String dialogTitle = "Convert to US Dollar (USD)"; //default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init(){
        editText = findViewById(R.id.etAmount);
        rgConvert = findViewById(R.id.rgCurrency);
        usdRadio = findViewById(R.id.rbUSD);
        jpyRadio = findViewById(R.id.rbJPY);
        gbpRadio = findViewById(R.id.rbGBP);
        cadRadio = findViewById(R.id.rbCAD);
        aedRadio = findViewById(R.id.rbAED);
        convertButton = findViewById(R.id.btnConvert);

        radios = new ArrayList<RadioButton>();
        radios.add(usdRadio);
        radios.add(jpyRadio);
        radios.add(gbpRadio);
        radios.add(cadRadio);
        radios.add(aedRadio);

        for(RadioButton radio : radios){
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogTitle = "Convert to " + (String) radio.getText();
                }
            });
        }

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Input amount first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rgConvert.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "Select currency to convert", Toast.LENGTH_SHORT).show();
                    return;
                }
                double toConvert = Double.valueOf(editText.getText().toString());
                double rate = 0;
                int selectedCurrencyId = rgConvert.getCheckedRadioButtonId();
                if(selectedCurrencyId == usdRadio.getId()){
                    rate = 58.04;
                }
                if(selectedCurrencyId == jpyRadio.getId()){
                    rate = 0.38;
                }
                if(selectedCurrencyId == gbpRadio.getId()){
                    rate = 77.58;
                }
                if(selectedCurrencyId == cadRadio.getId()){
                    rate = 41.36;
                }
                if(selectedCurrencyId == aedRadio.getId()){
                    rate = 15.80;
                }

                DecimalFormat df = new DecimalFormat("#.##");
                double convertedAmount = rate * toConvert;
                String formattedAmount = df.format(convertedAmount);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(dialogTitle)
                        .setMessage("Converted amount: " + formattedAmount + "PHP")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                editText.setText("");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

}