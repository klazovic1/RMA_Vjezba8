package ba.unsa.etf.rma.vj_18508;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MovieListFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceData){


        return inflater.inflate(R.layout.fragment_list, container, false);

    }

}
