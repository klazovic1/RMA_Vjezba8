package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieCursorAdapter extends ResourceCursorAdapter {

    public TextView titleView;
    public TextView genreView;
    public ImageView imageView;
    private String posterPath="https://image.tmdb.org/t/p/w342";

    public MovieCursorAdapter(Context context, int layout, Cursor c, boolean autoRequery) {
        super(context, layout, c, autoRequery);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        titleView = view.findViewById(R.id.itemName);
        genreView = view.findViewById(R.id.itemGenre);
        imageView = view.findViewById(R.id.icon);
        titleView.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_TITLE)));
        genreView.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_RELEASEDATE)));
        Glide.with(context)
                .load(posterPath+cursor.getString(cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_POSTERPATH)))
                .centerCrop()
                .placeholder(R.drawable.filmic)
                .error(R.drawable.filmic)
                .fallback(R.drawable.filmic)
                .into(imageView);
    }



}
