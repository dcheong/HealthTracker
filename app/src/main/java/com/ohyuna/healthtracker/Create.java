package com.ohyuna.healthtracker;

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

import java.util.Calendar;

public class Create extends AppCompatActivity {
    private int ageNum,heightNum,weightNum;
    private int ageinDays, ageinMonths;
    private String genderString;
    private boolean gen;
    private ZScore zscore;
    @Bind(R.id.firstName)
    EditText firstName;
    @Bind(R.id.secondName)
    EditText secondName;
    @Bind(R.id.lastName)
    EditText lastName;
    @Bind(R.id.bDay)
    EditText bDay;
    @Bind(R.id.bMonth)
    EditText bMonth;
    @Bind(R.id.bYear)
    EditText bYear;
    @Bind(R.id.age)
    TextView age;
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
    @Bind(R.id.done)
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
        ageinDays = 0; ageinMonths = 0;
        genderString = "M";
        gen = true;
        zscore = new ZScore(this);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writePatient();
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
                    gen = false;
                    updateZ();
                } else {
                    genderString = "M";
                    gen = true;
                    updateZ();
                }
            }
        });

    }
    public void updateZ() {
        if(height.getText().toString().length()!=0 && ageinDays != -1) {
            String hA = String.format("%4.2f",zscore.getHA(Double.parseDouble(height.getText().toString()),ageinMonths, gen));
            heightAge.setText(hA);
        }
        if(weight.getText().toString().length()!=0 && ageinDays != -1) {
            String wA = String.format("%4.2f",zscore.getWA(Double.parseDouble(weight.getText().toString()),ageinDays, gen));
            weightAge.setText(wA);

        }
        if(weight.getText().toString().length()!=0 && height.getText().toString().length()!=0) {
            String wH = String.format("%4.2f",zscore.getWH(Double.parseDouble(weight.getText().toString()),Double.parseDouble(height.getText().toString()),gen));
            weightHeight.setText(wH);
        }
    }
    public void writePatient() {
/*        JSONObject pat = new JSONObject();
        pat.put("Gender", genderString);
        pat.put("First Name", firstName.getText());
        pat.put("Second Name", secondName.getText());
        pat.put("Last Name", lastName.getText());
        pat.put("Birth Date", bDay.getText() + "/" + bMonth.getText() + "/" + bYear.getText());
        pat.put("Height", height.getText());
        pat.put("Weight", weight.getText());
        pat.put("Head", headCirc.getText());
        String patString = pat.toString();
        File file = getFileStreamPath(firstName.getText().toString() + secondName.getText().toString() + lastName.getText().toString() + ".txt");
        file.createNewFile();
        FileOutputStream writer = openFileOutput(file.getName(), Context.MODE_APPEND);
        writer.write(patString.getBytes());
        writer.flush();
        writer.close();
        System.out.println("file written");*/


    }
    public double calcAge() {
        if(!(bYear.getText().length()==0 || bMonth.getText().length()==0 || bDay.getText().length()==0)) {
            Calendar c = Calendar.getInstance();
            int y, m, d, a, am;
            y = c.get(Calendar.YEAR);
            m = c.get(Calendar.MONTH);
            d = c.get(Calendar.DAY_OF_MONTH);
            a = y - Integer.parseInt(bYear.getText().toString());
            am = 0;
            if (m < Integer.parseInt(bMonth.getText().toString())) {
                --a;
                am = 12 - Integer.parseInt(bMonth.getText().toString()) + m;
            }
            if (m == Integer.parseInt(bMonth.getText().toString()) && d < Integer.parseInt(bDay.getText().toString())) {
                --a;
            }
            if (a < 3) {
                headCirc.setAlpha(1);
                headCirc.setFocusableInTouchMode(true);
            } else {
                headCirc.setText(null);
                headCirc.setFocusableInTouchMode(false);
                headCirc.setAlpha(0.5f);
            }
            age.setText(am + " months " + a + " years");
            ageinDays = 365 * a + (int)(30.5 * am);
            ageinMonths = a * 12 + am;
            return a;
        } else {
            ageinDays = -1;
            age.setText("");
            return -1;
        }
    }
}
