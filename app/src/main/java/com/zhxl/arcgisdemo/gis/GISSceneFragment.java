package com.zhxl.arcgisdemo.gis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import huitx.libztframework.utils.EventBusMessage;

/**
 * GIS
 * SceneView
 * @author ZhuTao
 * @date 2019/10/11
 * @params
 */

@SuppressLint("ValidFragment")
public class GISSceneFragment extends BaseFragment {

    protected MyHandler mHandler;

    @BindView(R.id.gismap_gis_scene)
    SceneView mSceneView;

    public GISSceneFragment() {
        super(R.layout.fragment_main_gis_scene);
        TAG = getClass().getSimpleName() + "     ";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusSelPhoto(EventBusMessage data) {

    }

    @Override
    protected void initHead() {
        if (mHandler == null) mHandler = new MyHandler(mContext);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    protected void initLogic() {
        setupMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSceneView != null) {
            mSceneView.resume();
        }
    }

    private void setupMap() {
        if (mSceneView != null) {
            double latitude = 33.8210;
            double longitude = -118.6778;
            double altitude = 44000.0;
            double heading = 0.1;
            double pitch = 30.0;
            double roll = 0.0;

            ArcGISScene scene = new ArcGISScene();
            scene.setBasemap(Basemap.createStreets());
            mSceneView.setScene(scene);
            Camera camera = new Camera(latitude, longitude, altitude, heading, pitch, roll);
            mSceneView.setViewpointCamera(camera);
        }
    }

    protected static final int MSG_OrderMessage = 100;

    protected class MyHandler extends Handler {

        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<Context> mActivity;

        protected MyHandler(Context activity) {
            mActivity = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OrderMessage:
                    break;
            }
        }

    }

    @Override
    protected void initLocation() {
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void pauseClose() {
        if (mSceneView != null) {
            mSceneView.pause();
        }
    }

    @Override
    protected void destroyClose() {
        if (mSceneView != null) {
            mSceneView.dispose();
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
