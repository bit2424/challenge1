package com.example.challenge1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends Fragment {

    private RecyclerView locItemViewList;
    private LinearLayoutManager layoutManager;
    private LocItemAdapter adpater;
    private OnChangeFragment observer;
    private AppModel model;


    public ListItemFragment() {
        adpater = new LocItemAdapter();
    }


    // TODO: Rename and change types and number of parameters
    public static ListItemFragment newInstance() {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_item,container,false);
        locItemViewList = root.findViewById(R.id.locItemsViewList);
        locItemViewList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        locItemViewList.setLayoutManager(layoutManager);
        locItemViewList.setAdapter(adpater);

        return root;
    }


    public void setModel(AppModel model) {  this.model = model; }
    public void setObserver(OnChangeFragment observer) {
        this.observer = observer;
    }
}