package com.ohyuna.healthtracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Create extends AppCompatActivity {
    private int ageNum,heightNum,weightNum;
    private String genderString;
    @Bind(R.id.firstName)
    EditText firstName;
    @Bind(R.id.lastName)
    EditText lastName;
    @Bind(R.id.bDay)
    EditText bDay;
    @Bind(R.id.bMonth)
    EditText bMonth;
    @Bind(R.id.bYear)
    EditText bYear;
    @Bind(R.id.height)
    EditText height;
    @Bind(R.id.weight)
    EditText weight;
    @Bind(R.id.headCirc)
    EditText headCirc;
    @Bind(R.id.heightAge)
    TextView heightAge;
    @Bind(R.id.weightAge)
    TextView weightAge;
    @Bind(R.id.weightHeight)
    TextView weightHeight;
    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.gender)
    Switch gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        ageNum = 0; heightNum = 0; weightNum = 0;
        genderString = "M";
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    writePatient();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcAge();
                updateZ();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcAge();
                updateZ();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcAge();
                updateZ();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateZ();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateZ();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    genderString = "F";
                } else {
                    genderString = "M";
                }
            }
        });

    }
    public void updateZ() {
        double heightAgeNum;
        double weightAgeNum;
        double weightHeightNum;
    }
    public void writePatient() throws JSONException, IOException {
        JSONObject pat = new JSONObject();
        pat.put("Gender", genderString);
        pat.put("First Name", firstName.getText());
        pat.put("Last Name", lastName.getText());
        pat.put("Birth Date", bDay.getText() + "/" + bMonth.getText() + "/" + bYear.getText());
        pat.put("Height", height.getText());
        pat.put("Weight", weight.getText());
        pat.put("Head", headCirc.getText());
        String patString = pat.toString();
        File file = getFileStreamPath("patientdb.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream writer = openFileOutput(file.getName(), Context.MODE_APPEND);
        writer.write(patString.getBytes());
        writer.flush();
        writer.close();
        System.out.println("file written");


    }
    public double calcAge() {
        if(!(bYear.getText().length()==0 || bMonth.getText().length()==0 || bDay.getText().length()==0)) {
            Calendar c = Calendar.getInstance();
            int y, m, d, a;
            y = c.get(Calendar.YEAR);
            m = c.get(Calendar.MONTH);
            d = c.get(Calendar.DAY_OF_MONTH);
            a = y - Integer.parseInt(bYear.getText().toString());
            if (m < Integer.parseInt(bMonth.getText().toString())) {
                --a;
            }
            if (m == Integer.parseInt(bMonth.getText().toString()) && d < Integer.parseInt(bDay.getText().toString())) {
                --a;
            }
            if(a<5) {
                headCirc.setFocusableInTouchMode(true);
            } else {
                headCirc.setText(null);
                headCirc.setFocusableInTouchMode(false);
            }
            return a;
        }
        else {
            return -1;
        }

    }
}
