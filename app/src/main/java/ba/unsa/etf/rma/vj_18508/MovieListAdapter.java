package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private int resource;

    public MovieListAdapter (Context context, int r, List<Movie> items){
        super(context, r, items);
        resource = r;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        LinearLayout newView;
        if(convertView == null){

            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, newView, true);

        }else{

            newView = (LinearLayout)convertView;

        }

        Movie currentMovie = getItem(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.icon);
            image.setImageResource(R.drawable.filmic);

            TextView name = (TextView) convertView.findViewById(R.id.itemName);
            name.setText(currentMovie.getName());

            TextView genre = (TextView) convertView.findViewById(R.id.itemGenre);
            genre.setText(currentMovie.getGenre());


        return newView;
    }

}
