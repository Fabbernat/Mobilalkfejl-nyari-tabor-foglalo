package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {
    public static Bitmap loadImageFromAssets(Context context, String fileName) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = context.getAssets().open("images/" + fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}