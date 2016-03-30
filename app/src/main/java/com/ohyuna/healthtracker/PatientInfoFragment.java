package com.ohyuna.healthtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.text.Editable;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private boolean editing = false;

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
    @Bind(R.id.editSave)
    Button editSave;
    @Bind(R.id.done)
    Button done;
    @Bind(R.id.image)
    CircleImageView pImage;
    @Bind(R.id.gender)
    Switch gender;

    public PatientInfoFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientInfoFragment newInstance(String param1, String param2) {
        PatientInfoFragment fragment = new PatientInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_patient_view_info, container, false);
        ButterKnife.bind(this, view);
        calcAge();
        zscore = new ZScore(super.getContext());

        updateZ();
        ageNum = 0; heightNum=0; weightNum=0;
        ageinDays = 0; ageinMonths=0;
        gen = false;

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editing) {
                    firstName.setFocusableInTouchMode(false);
                    secondName.setFocusableInTouchMode(false);
                    lastName.setFocusableInTouchMode(false);
                    bDay.setFocusableInTouchMode(false);
                    bMonth.setFocusableInTouchMode(false);
                    bYear.setFocusableInTouchMode(false);
                    weight.setFocusableInTouchMode(false);
                    height.setFocusableInTouchMode(false);
                    headCirc.setFocusableInTouchMode(false);
                    gender.setClickable(false);
                    editSave.setText("Edit");
                    editing = false;
                } else {
                    editing = true;
                    firstName.setFocusableInTouchMode(true);
                    secondName.setFocusableInTouchMode(true);
                    lastName.setFocusableInTouchMode(true);
                    bDay.setFocusableInTouchMode(true);
                    bMonth.setFocusableInTouchMode(true);
                    bYear.setFocusableInTouchMode(true);
                    weight.setFocusableInTouchMode(true);
                    height.setFocusableInTouchMode(true);
                    headCirc.setFocusableInTouchMode(true);
                    gender.setClickable(true);
                    editSave.setText("Save");
                }
            }
        });
        pImage.setImageResource(R.drawable.child1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
