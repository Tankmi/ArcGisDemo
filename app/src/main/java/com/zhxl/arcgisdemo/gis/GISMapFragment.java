package com.zhxl.arcgisdemo.gis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.CoordinateFormatter;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
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
 *
 * @author ZhuTao
 * @date 2019/10/11
 * @params
 */

@SuppressLint("ValidFragment")
public class GISMapFragment extends BaseFragment {

    protected MyHandler mHandler;

    @BindView(R.id.gismap_gis)
    MapView mMapView;

    public GISMapFragment() {
        super(R.layout.fragment_main_gis);
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

        initListener();

        loadOfflineDataUrl();
//        loadOfflineData();
//        loadMobileMapPackage("file:///android_asset/hangzhou.tpk");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    private void setupMap() {
        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
//            Basemap.Type basemapType = Basemap.Type.STREETS_NIGHT_VECTOR;
            double latitude = 34.09042;
            double longitude = -118.71511;
            int levelOfDetail = 11;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        /**
         * 点击事件对象
         *     MotionEvent {
         *     action=ACTION_UP,
         *     id[0]=0,
         *     x[0]=455.5782,
         *     y[0]=1095.3904,
         *     toolType[0]=TOOL_TYPE_FINGER,
         *     buttonState=0,
         *     metaState=0,
         *     flags=0x0,
         *     edgeFlags=0x0,
         *     pointerCount=1,
         *     historySize=0,
         *     eventTime=351739375,
         *     downTime=351739296,
         *     deviceId=5,
         *     source=0x1002 }
         */
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(getActivity(), mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent v) {
                android.graphics.Point screenPoint = new android.graphics.Point(Math.round(v.getX()), Math.round(v.getY()));
                Point clickPoint = mMapView.screenToLocation(screenPoint);
                //获取点击位置的经纬度
                String x = CoordinateFormatter.toLatitudeLongitude(clickPoint, CoordinateFormatter.LatitudeLongitudeFormat.DECIMAL_DEGREES, 4);
                GraphicsOverlay graphicsOverlay_1 = new GraphicsOverlay();
                //加个点
//                SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 10);
                //加个图标
                BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_close);
                PictureMarkerSymbol pointSymbol = new PictureMarkerSymbol(drawable);

                Graphic pointGraphic = new Graphic(clickPoint, pointSymbol);
                graphicsOverlay_1.getGraphics().add(pointGraphic);
                mMapView.getGraphicsOverlays().add(graphicsOverlay_1);
                return true;
            }
        });
    }

    private void loadOfflineDataUrl() {
        //WebGIS（Online & Portal）
//        try {
//            String theURLString =
//                    "http://www.arcgis.com/home/webmap/viewer.html?webmap=55c1665bcd064552944a9e8296271ec3";
//            ArcGISMap mainArcGISMap = new ArcGISMap(theURLString);
//            Basemap mainBasemap = mainArcGISMap.getBasemap();
//            LayerList mainLayerList = mainArcGISMap.getOperationalLayers();
//
//            ArcGISMap map = new ArcGISMap(mainBasemap);
//            mMapView.setMap(map);
//
//        }
//        catch (Exception e)
//        {
//
//        }

        //传统GIS
        try {
//            String theURLString = "http://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
            String theURLString = "http://map.geoq.cn/arcgis/rest/services/ChinaOnlineStreetWarm/MapServer";
//            String theURLString = "file:///android_asset/hangzhou.tpk";
//            String theURLString = "file:///android_asset/lu_polyline.shp";
            ArcGISTiledLayer mainArcGISTiledLayer = new ArcGISTiledLayer(theURLString);
            Basemap mainBasemap = new Basemap(mainArcGISTiledLayer);
            ArcGISMap mainArcGISMap = new ArcGISMap(mainBasemap);
            mMapView.setMap(mainArcGISMap);
        } catch (Exception e) {

        }

        //在线矢量切片
//        try {
//            String theOfflineTiledLayers =
//                    "https://www.arcgis.com/home/item.html?id=e19e9330bf08490ca8353d76b5e2e658";
//            ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(theOfflineTiledLayers);
//            Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
//            ArcGISMap mainArcGISMap = new ArcGISMap(mainBasemap);
//            mMapView.setMap(mainArcGISMap);
//            Viewpoint vp = new Viewpoint(47.606726, -122.335564, 72223.819286);
//            mainArcGISMap.setInitialViewpoint(vp);
//        }
//        catch (Exception e)
//        {
//
//        }
    }

    private void loadOfflineData() {
//        Utils utils = new Utils();
        //tpk--缓存显示
//        TileCache tileCache = new TileCache(utils.Save_Path  + "/" +  utils.File_name);
        TileCache tileCache = new TileCache("file:///android_asset/hangzhou.tpk");
//        TileCache tileCache = new TileCache("file:///android_asset/nn_polyline.dbf");
//        TileCache tileCache = new TileCache("file:///android_asset/lu_polyline.shp");
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(tileCache);
        tiledLayer.setMinScale(8000);
        tiledLayer.setMaxScale(1600);
        Basemap basemap = new Basemap(tiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        mMapView.setMap(map);
    }

    private void loadMobileMapPackage(String mmpkFile) {
        MobileMapPackage mapPackage = new MobileMapPackage(mmpkFile);
        mapPackage.loadAsync();
        mapPackage.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mapPackage.getLoadStatus() == LoadStatus.LOADED && mapPackage.getMaps().size() > 0) {
                    mMapView.setMap(mapPackage.getMaps().get(0));
                } else {
                    LOG("离线文件加载失败");
                    // Log an issue if the mobile map package fails to load
                }
            }
        });
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
        if (mMapView != null) {
            mMapView.pause();
        }
    }

    @Override
    protected void destroyClose() {
        if (mMapView != null) {
            mMapView.dispose();
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
