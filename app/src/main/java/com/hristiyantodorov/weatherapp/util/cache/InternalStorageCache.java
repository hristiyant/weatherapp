package com.hristiyantodorov.weatherapp.util.cache;

import android.content.Context;

import com.hristiyantodorov.weatherapp.util.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InternalStorageCache {

    private InternalStorageCache() {
    }

    public static void writeObject(Context context, Object object) throws IOException {
        FileOutputStream fos =
                context.openFileOutput(Constants.KEY_PERSISTENCE_FORECAST_OBJECT, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(Constants.KEY_PERSISTENCE_FORECAST_OBJECT);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}
