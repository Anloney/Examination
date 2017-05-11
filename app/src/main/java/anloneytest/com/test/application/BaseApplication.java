package anloneytest.com.test.application;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * 全局的application
 * Created by Anloney on 2016/3/28.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
    }
}
