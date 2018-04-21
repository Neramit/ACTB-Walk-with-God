package com.example.biblereading.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.biblereading.R;

import java.util.ArrayList;

/**
 * Created by Neramit777 on 1/18/2018 at 6:09 PM.
 */

public class LocationFragment extends Fragment {

    private ArrayList<String> church_list = new ArrayList<String>();
    ListView churchList;
//    private friendAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_location, container, false);
        churchList = (ListView) view.findViewById(R.id.church_list);
        return view;
    }

}
