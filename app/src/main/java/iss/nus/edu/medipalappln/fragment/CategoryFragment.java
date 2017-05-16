package iss.nus.edu.medipalappln.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.adapter.CategoryListAdapter;

public class CategoryFragment extends Fragment {
    private CategoryListAdapter categoryListAdapter;
    private TextView tvEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false);

        View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);
        ListView facilityList = (ListView) fragmentView.findViewById(R.id.lv_facility_list);
        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_value2);
        categoryListAdapter = new CategoryListAdapter(getActivity());
        facilityList.setAdapter(categoryListAdapter);
        /*FloatingActionButton floatingActionButton =
                (FloatingActionButton) fragmentView.findViewById(R.id.fabf);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AddCategoryActivity.class));
            }
        });*/
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryListAdapter.refreshFacilities();
        tvEmpty.setVisibility(categoryListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
