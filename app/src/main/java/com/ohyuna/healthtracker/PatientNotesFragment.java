package com.ohyuna.healthtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientNotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientNotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    notesAdapter adapter;
    DBManager db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PatientNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientNotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientNotesFragment newInstance(String param1, String param2) {
        PatientNotesFragment fragment = new PatientNotesFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_notes, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        db = new DBManager(this.getContext());
        db.start();
        ArrayList<Note> notes = db.getPatientNotes(((PatientView)getActivity()).patientid);
        db.close();
        adapter = new notesAdapter(this.getContext());
        adapter.setList(notes);
        listView.setAdapter(adapter);
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();

        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()
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
}

class notesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Note> notes;
    private static LayoutInflater inflater = null;

    public notesAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setList(ArrayList<Note> notes) {
        this.notes = notes;

    }
    @Override
    public int getCount() {
        return notes.size();
    }
    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).patientid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.patient_note, null);
        }
        TextView date = (TextView) vi.findViewById(R.id.date);
        TextView message = (TextView) vi.findViewById(R.id.message);
        TextView author = (TextView) vi.findViewById(R.id.author);
        TextView category = (TextView) vi.findViewById(R.id.category);
        date.setText(notes.get(position).date);
        message.setText(notes.get(position).message);
        author.setText(notes.get(position).author);
        category.setText(String.valueOf(notes.get(position).cat));
        return vi;
    }


}