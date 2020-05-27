//package ba.unsa.etf.rma.vj_18508;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//
//public class MovieCursorAdapter extends CursorAdapter {
//
//    private String posterPath="https://image.tmdb.org/t/p/w342";
//
//    public TodoCursorAdapter(Context context, Cursor cursor) {
//        super(context, cursor, 0);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.list_element, parent, false);
//    }
//
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        // Find fields to populate in inflated template
//
//        ImageView image = (ImageView) view.findViewById(R.id.icon);
////        image.setImageResource(currentMovie.getSlika());
//
//        TextView title = (TextView) view.findViewById(R.id.itemName);
//        TextView genre = (TextView) view.findViewById(R.id.itemGenre);
//        // Extract properties from cursor
//        String title1 = cursor.getString(cursor.getColumnIndexOrThrow("title"));
//        String genre1 = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
//        // Populate fields with extracted properties
//        title.setText(title1);
//        genre.setText(genre1);
//
//        String poster = cursor.getString(cursor.getColumnIndexOrThrow("posterPath"));
//
//        Glide.with(context).load(posterPath+poster).centerCrop().placeholder(R.drawable.filmic)
//                .error(R.drawable.filmic).fallback(R.drawable.filmic).into(image);
//
//    }
//
//
//
//}
