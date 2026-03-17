package hu.unideb.inf.shoppinglist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class ColorFragment extends Fragment {

    private String color = "";

    public String getColor() {
        return color;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_color, container, false);
        RadioGroup rg = rootView.findViewById(R.id.colorRadioGroup);
        rg.setOnCheckedChangeListener(
                (radioGroup, checkedId) -> {
                    if (checkedId==R.id.redRadioButton) color = getString(R.string.red);
                    else color = getString(R.string.green);
                }
        );
        return rootView;
    }
}