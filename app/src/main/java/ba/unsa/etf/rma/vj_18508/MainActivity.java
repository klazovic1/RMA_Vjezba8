package ba.unsa.etf.rma.vj_18508;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnItemClick {


    private boolean twoPaneMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details = findViewById(R.id.movie_detail);

        if(details != null){
            twoPaneMode = true;
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.movie_detail);
            if(movieDetailFragment == null){
                movieDetailFragment = new MovieDetailFragment();
                fragmentManager.beginTransaction().replace(R.id.movie_detail, movieDetailFragment).commit();
            }
        }else{
            twoPaneMode = false;
        }

        Fragment listFragment = fragmentManager.findFragmentByTag("list");

        if(listFragment == null){
            listFragment = new MovieListFragment();
            fragmentManager.beginTransaction().replace(R.id.movies_list, listFragment, "list").commit();
        }else{

            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }




    }


    @Override
    public void onItemClicked(Movie movie){

        Bundle arguments = new Bundle();
        arguments.putParcelable("movie", movie);
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(arguments);
        if(twoPaneMode){

            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail, movieDetailFragment).commit();

        }else{

            getSupportFragmentManager().beginTransaction().replace(R.id.movies_list, movieDetailFragment).addToBackStack(null).commit();

        }

    }




}
