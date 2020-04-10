package com.example.dinoquestionandkids.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.fragment.Fragment1;
import com.example.dinoquestionandkids.fragment.Fragment2;
import com.example.dinoquestionandkids.fragment.Fragment3;
import com.example.dinoquestionandkids.fragment.Fragment4;
import com.example.dinoquestionandkids.fragment.Fragment5;
import com.example.dinoquestionandkids.fragment.Fragment6;
import com.example.dinoquestionandkids.fragment.Fragment7;
import com.example.dinoquestionandkids.fragment.Fragment8;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    public static Fragment newInstance(int index) {
        Fragment fragment = null;
        switch (index){
            case 1: fragment = new Fragment1(); break;
            case 2: fragment = new Fragment2(); break;
            case 3: fragment = new Fragment3(); break;
            case 4: fragment = new Fragment4(); break;
            case 5: fragment = new Fragment5(); break;
            case 6: fragment = new Fragment6(); break;
            case 7: fragment = new Fragment7(); break;
            case 8: fragment = new Fragment8(); break;
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

}