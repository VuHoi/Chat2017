package adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vukhachoi.chat2017.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Friend;

/**
 * Created by Vu Khac Hoi on 9/25/2017.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {
    Activity context;
    int resource;
    List<Friend> objects;
    public FriendAdapter(Activity context, int resource, List<Friend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);





        TextView textView = row.findViewById(R.id.txtNameFriend);
        TextView textView1 = row.findViewById(R.id.txtlast);
ImageView imageView12=row.findViewById(R.id.imageView12);
        ImageView imageView = row.findViewById(R.id.imgAvatar);


        final Friend hoa = this.objects.get(position);
        textView.setText(hoa.getName());
textView1.setText(hoa.getLastCommenet());
        Picasso.with(context).load(Uri.parse(hoa.getAvatar())).into(imageView);
        Picasso.with(context).load(Uri.parse(hoa.getStatus())).into(imageView12);
        return row;
    }
}
