package huitx.libztframework.net;

import android.annotation.SuppressLint;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Part;

public class RxJavaDataImpl<T> {


    public static <T> void getData(String url, Map<String, Object> headerMap, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi().
                getData(url, headerMap).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(observer);
    }

    public static <T> void getData(String url, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi().
                getData(url).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(observer);
    }

    public static <T> void getData(String url, HashMap<String, Object> params, DefaultObserver<ResponseBody> observer) {
        if (params != null) {
            RetrofitHelper.getService().getApi().
                    getData(url, params).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeOn(Schedulers.io()).
                    subscribeWith(observer);
        } else {
            getData(url, observer);
        }
    }

    @SuppressLint("CheckResult")
    public static void postData(String url, Map<String, Object> map, DefaultObserver<ResponseBody> mBody) {
        if (map != null) {
            RetrofitHelper.getService().getApi().
                    postData(url, map).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(mBody);
//                    .subscribeWith(mBody);
        } else {
            RetrofitHelper.getService().getApi().
                    postData(url).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeWith(mBody);
        }
    }

    public static void postData(String url, Map<String, Object> headerMap, Map<String, Object> params, DefaultObserver<ResponseBody> observer) {
        if (params != null) {
            RetrofitHelper.getService().getApi().
                    postData(headerMap, url, params).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeWith(observer);
        } else if (params == null) {
            RetrofitHelper.getService().getApi().
                    postData(url, headerMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeWith(observer);
        }
    }

    public static <T> void postData(String url, Map<String, Object> headersMap, RequestBody body, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi().
                postData(url, headersMap, body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
    }


    public static <T> void downloadFile(String url, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi().
                downloadFile(url).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(observer);
    }


    public static <T> void uploadFile(String url, Part part, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi()
                .uploadFile(url, part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public static <T> void uploadFile(String url, List<MultipartBody.Part> uploadFiles, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi()
                .uploadFiles(url, uploadFiles)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
    }

    public static <T> void uploadFile(String url, @Part MultipartBody.Part parts, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi()
                .uploadFile(url, parts).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(observer);
    }

    public static <T> void uploadFile(String url, Map<String, Object> headerMap, @Part MultipartBody.Part parts, DefaultObserver<ResponseBody> observer) {
        RetrofitHelper.getService().getApi()
                .uploadFile(url, headerMap, parts).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(observer);
    }

    public static <T> void postHeader(String url, HashMap<String, String> header, String path, DefaultObserver<ResponseBody> observer) {
        if (header == null) {
            header = new HashMap<>();
        }
        //通过一个图片路径，转换类型
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/octet-stream"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), requestBody);
        //调用方法
        RetrofitHelper.getService().getApi().postHeader(url, header, builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        // return OkHttpUtil.getInterface();
    }

}
