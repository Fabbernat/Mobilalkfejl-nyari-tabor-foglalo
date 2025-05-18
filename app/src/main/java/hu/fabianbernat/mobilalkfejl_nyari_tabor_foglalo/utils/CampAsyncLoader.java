package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils;

import java.util.Random;

import javax.annotation.Nullable;

import android.context.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CampAsyncLoader extends AsyncTaskLoader<String> {
    public CampAsyncLoader(@NonNull Context context) {
        super(context);

    }

    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Random randy = new Random();
        int howLong = randy.nextInt(11);
        int ms = howLong * 1000;

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Bejelentkezés vendégként sikeres" + ms + "ms után";
    }
}
