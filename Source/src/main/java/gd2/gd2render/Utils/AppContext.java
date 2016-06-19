package gd2.gd2render.Utils;


import android.app.Application;
import android.content.Context;

public class AppContext extends Application {

    private static Context context;

    /**
     * onCreate method to set context.
     */
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    /**
     * Returns static context for the Application
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
