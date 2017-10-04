package com.example.vukhachoi.chat2017;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

/**
 * Created by Vu Khac Hoi on 9/24/2017.
 */

public class HomeFragment  extends Fragment {
Button btnInput;
    FirebaseUser user;
    public  HomeFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home, container, false);
        btnInput=view.findViewById(R.id.btnInput);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ImageView imageView =view.findViewById(R.id.test);
        Picasso.with(getContext()).load(user.getPhotoUrl()).into(imageView);

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        BitmapDrawable drawableLeft = new BitmapDrawable(getResources(), getRoundedCornerBitmap(bitmap));
        btnInput.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);

        return view;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff4242DB;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth()/2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas.drawCircle(0, 0, bitmap.getWidth(), paint);
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
