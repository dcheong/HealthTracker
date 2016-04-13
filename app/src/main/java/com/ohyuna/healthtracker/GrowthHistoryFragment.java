package com.ohyuna.healthtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class GrowthHistoryFragment extends Fragment {
    DBManager db;
    ghAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GrowthHistoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GrowthHistoryFragment newInstance(String param1, String param2) {
        GrowthHistoryFragment fragment = new GrowthHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_growthhistory_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        db = new DBManager(this.getContext());
        db.start();
        ArrayList<GHEntry> ghEntries = db.getPatientGH(((PatientView)getActivity()).patientid);
        db.close();
        adapter = new ghAdapter(this.getContext());
        adapter.setList(ghEntries);
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

class ghAdapter extends BaseAdapter {
    Context context;
    ArrayList<GHEntry> ghEntries;
    private static LayoutInflater inflater = null;

    public ghAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setList(ArrayList<GHEntry> ghEntries) {
        this.ghEntries = ghEntries;

    }
    @Override
    public int getCount() {
        return ghEntries.size();
    }
    @Override
    public Object getItem(int position) {
        return ghEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ghEntries.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.fragment_growthhistory, null);
        }
        TextView height = (TextView) vi.findViewById(R.id.height);
        TextView weight = (TextView) vi.findViewById(R.id.weight);
        TextView head = (TextView) vi.findViewById(R.id.head);
        TextView zha = (TextView) vi.findViewById(R.id.zha);
        TextView zwa = (TextView) vi.findViewById(R.id.zwa);
        TextView zwh = (TextView) vi.findViewById(R.id.zwh);
        TextView date = (TextView) vi.findViewById(R.id.date);
        height.setText(String.valueOf(ghEntries.get(position).height));
        weight.setText(String.valueOf(ghEntries.get(position).weight));
        head.setText(String.valueOf(ghEntries.get(position).head));
        zha.setText(String.valueOf(ghEntries.get(position).zha));
        zwa.setText(String.valueOf(ghEntries.get(position).zwa));
        zwh.setText(String.valueOf(ghEntries.get(position).zwh));
        date.setText(ghEntries.get(position).date);
        return vi;
    }


}