package com.ohyuna.healthtracker;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class Create extends AppCompatActivity {
    private int ageNum,heightNum,weightNum;
    private int ageinDays, ageinMonths;
    private String genderString;
    private boolean gen;
    private ZScore zscore;
    @Bind(R.id.image)
    ImageView image;
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
        gen = false;
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
                finish();
            }
        });
        bDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getAge();
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
                getAge();
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
                getAge();
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
                    gen = true;
                    updateZ();
                } else {
                    genderString = "M";
                    gen = false;
                    updateZ();
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 1);
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
    private void savePicture(Bitmap bm, String imgName) {
        OutputStream fOut = null;
        String strDirectory = Environment.getExternalStorageDirectory().toString();

        File f = new File(strDirectory, imgName);
        try {
            fOut = new FileOutputStream(f);

            /**Compress image**/
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

            /**Update image to gallery**/
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    f.getAbsolutePath(), f.getName(), f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        DBManager db = new DBManager(Create.this);
        db.start();
        String birthdate = bDay.getText().toString() + "-" + bMonth.getText().toString() + "-" + bYear.getText().toString();
        Double hCirc;
        if (headCirc.getText().toString().trim().length()>0) {
            hCirc = Double.parseDouble(headCirc.getText().toString());
        } else {
            hCirc = 0.0;
        }
        int patientid = db.newPatient(firstName.getText().toString(),
                secondName.getText().toString(),
                lastName.getText().toString(),
                birthdate,
                Double.parseDouble(height.getText().toString()),
                Double.parseDouble(weight.getText().toString()),
                hCirc,
                Double.parseDouble(heightAge.getText().toString()),
                Double.parseDouble(weightAge.getText().toString()),
                Double.parseDouble(weightHeight.getText().toString()),
                gen);
        BitmapDrawable bMap = (BitmapDrawable) image.getDrawable();
        if (bMap != null) {
            savePicture(bMap.getBitmap(), String.valueOf(patientid));
        }
        db.close();
    }
    public double getAge() {
        if(!(bYear.getText().length()==0 || bMonth.getText().length()==0 || bDay.getText().length()==0)) {
            int y = Integer.parseInt(bYear.getText().toString());
            int m = Integer.parseInt(bMonth.getText().toString());
            int d = Integer.parseInt(bDay.getText().toString());
            AgeCalc calc = new AgeCalc();
            int[] ageArray = calc.calculateAge(d,m,y);
            age.setText(ageArray[0] + " days" + ageArray[1] + " months" + ageArray[2] + " years");
            ageinDays = 365 * ageArray[2] + (int)(30.5 * ageArray[1]) + ageArray[0];
            ageinMonths = 12 * ageArray[2] + ageArray[1] + (int) Math.round(ageArray[0]/30.5);
            return ageArray[0];
        } else {
            ageinDays = -1;
            age.setText("");
            return -1;
        }

    }
}
