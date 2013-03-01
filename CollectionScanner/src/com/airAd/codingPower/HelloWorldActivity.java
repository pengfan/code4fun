package com.airAd.codingPower;

import com.airAd.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    private Finder finder;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmain);
        finder = new Finder(this);
        textView = finder.findView(R.id.test_show);
        /*        List<PackageInfo> packageInfoList = null;
                try {
                    packageInfoList = this.getPackageManager().getInstalledPackages(0);
                } catch (Exception e0) {
                    try {
                        packageInfoList = this.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
                    } catch (Exception e_GET_ACTIVITIES) {
                        Log.w("test", "getAppInfoList error:" + e_GET_ACTIVITIES.getMessage());
                    }
                }
                if (packageInfoList != null) {
                    Log.i("test", "packageSize:" + packageInfoList.size() + "");
                } else {
                    Log.i("test", "packageSize:null");
                }*/
    }

    public static class Finder {
        private Activity act;

        Finder(Activity act) {
            this.act = act;
        }

        public <T extends View> T findView(int id) {
            return (T) act.findViewById(id);
        }
    }
}
