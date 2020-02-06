package huitx.libztframework.net;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import huitx.libztframework.context.LibPreferenceEntity;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.NetUtils;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

/**
 * 1、OkhttpHelper
 */
public class OkhttpHelper {
    private static final int WRITE_OUT_TIME = 20;
    private static final int READ_OUT_TIME = 20;
    private static final int CONNECT_OUT_TIME = 10;
    private static final String TAG = "OkhttpHelper";

    private static OkhttpHelper client;
    private OkHttpClient mOkhttpClient;

    public static OkhttpHelper getOkHttpHelper() {
        if (client == null) {
            synchronized (OkhttpHelper.class) {
                if (client == null) {
                    client = new OkhttpHelper();
                }
            }
        }
        return client;
    }

//    public OkHttpClient getClient(){
//        HashMap<String,String> mDataMap = null;
//        mOkhttpClient = new OkHttpClient().newBuilder()
//                .writeTimeout(WRITE_OUT_TIME,  TimeUnit.SECONDS)
//                .readTimeout(READ_OUT_TIME, TimeUnit.SECONDS)
//                .connectTimeout(CONNECT_OUT_TIME, TimeUnit.SECONDS)
////                .retryOnConnectionFailure(true) //设置超时时再请求一次
//                .addInterceptor(new HeaderParameterInter())     //添加请求头参数
////                .addInterceptor(new BodyParameterInter())     //添加公共查询参数
////                .addInterceptor(new MutiBaseUrlInterceptor())   //添加可以处理多个Baseurl的拦截器
//                .addInterceptor(new LoggingInterceptor())     //添加请求信息拦截器
//                .addInterceptor(new CacheInterceptor())
////                .addNetworkInterceptor(new CacheInterceptor())//必须要有，否则会返回504，addNetWoekInterceptor()是在网络畅通的情况下调用，addInterceptor在任何情况下都会调用
//                .cache(new Cache(new File(ApplicationData.context.getExternalFilesDir
//                        (PreferenceEntity.KEY_CACHE_PATH), "HDFS_HttpCache"), 14 * 1024 * 100))         //设置缓存目录，以及缓存大小
//                .build();
//        return mOkhttpClient;
//    }

    public OkHttpClient getClient() {
        mOkhttpClient = new OkHttpClient().newBuilder()
                .writeTimeout(WRITE_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_OUT_TIME, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_OUT_TIME, TimeUnit.SECONDS)
                .addInterceptor(new HeaderParameterInter())     //添加请求头参数
                .addInterceptor(new BodyParameterInter())     //添加请求体参数
                .addInterceptor(new LoggingInterceptor())     //添加请求信息拦截器
                .build();
        return mOkhttpClient;
    }


//拦截器1、请求头参数添加   2、公共请求参数添加   3、请求信息  4、缓存   5、 log

    //1请求头参数添加
    public class HeaderParameterInter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //创建请求参数
            Request requestBuilder = LibPreferenceEntity.getLoginParamsForOkHttp(request);
//            Request requestBuilder = request.newBuilder()
////                    .header()
//                    .addHeader("id", (String) HeaderParamss.get("id"))
//                    .addHeader("user", (String) HeaderParamss.get("user"))
//                    .addHeader("imei", (String) HeaderParamss.get("imei"))
//                    .build();
            return chain.proceed(requestBuilder);
        }
    }

    //2 POST 公共查询参数添加
    public class BodyParameterInter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            //创建map集合，将添加的公共参数保存到map中
            Map mUrlParamsMap = new HashMap<>();

            HashMap<String, String> commonalityMap = LibPreferenceEntity.getQueryBodyParamsForOkHttp();

            if (commonalityMap == null) {

                return chain.proceed(chain.request());
            }

            //拿到原来的request
            Request oldrequest = chain.request();
            //拿到请求的url
            String url = oldrequest.url().toString();
            //判断是get还是post
            if (oldrequest.method().equalsIgnoreCase("POST")) {
//                if (commonalityMap!=null && commonalityMap.size()>0){
//                    StringBuilder stringBuilder = new StringBuilder(url);
//                    //拼接公共请求参数
//                    for(Map.Entry<String,String> entry:commonalityMap.entrySet()){
//                        stringBuilder.append("&"+entry.getKey()+ "=" + entry.getValue());
//                    }
//                    url = stringBuilder.toString();
//                    //如果之前的url没有？号，我们需要手动给他添加一个？号
//                    if (!url.contains("?")){
//                        url = url.replaceFirst("&", "?");
//                    }
//                    //依据原来的request构造一个新的request
//                    Request request = oldrequest.newBuilder()
//                            .url(url)
//                            .build();
//
//                    return chain.proceed(request);
//
//                }else {
//                    if (commonalityMap!=null&& commonalityMap.size()>0){
                RequestBody body = oldrequest.body();
                if (body != null && body instanceof FormBody) {

                    FormBody formBody = (FormBody) body;
                    //1.把原来的的body里面的参数添加到新的body中
                    FormBody.Builder builder = new FormBody.Builder();

                    //为了防止重复添加相同的key和value
                    Map<String, Object> temmap = new HashMap<>();

                    for (int i = 0; i < formBody.size(); i++) {
                        builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        temmap.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }

                    //2.把公共请求参数添加到新的body中
                    for (Map.Entry<String, String> entry : commonalityMap.entrySet()) {
                        if (!temmap.containsKey(entry.getKey())) {
                            builder.add(entry.getKey(), entry.getValue());
                        }
                    }
                    FormBody newFormBody = builder.build();
                    //依据原来的request构造一个新的request,
                    Request newRequest = oldrequest.newBuilder()
                            .post(newFormBody)
                            .build();
                    return chain.proceed(newRequest);
                }
            }
//                }
//            }

            return chain.proceed(oldrequest);
        }
    }

    //3 URL 拼接公共查询参数添加
    public class UrlParameterInter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = LibPreferenceEntity.getUrlParamsForOkHttp(request);

//            Request request = chain.request();
//            HttpUrl url = request.url().newBuilder()
//                    .addQueryParameter("bodyparameter","312")
//                    .build();
            return chain.proceed(request.newBuilder().url(url).build());
        }
    }


    /**
     * 设置缓存的拦截器
     */
    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            LOGUtils.LOG(TAG, "缓存拦截器");
            Request request = chain.request();
            if (!NetUtils.isAPNType()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);

            if (NetUtils.isAPNType()) {
                String cacheControl = request.cacheControl().toString();
                LOGUtils.LOG(TAG, "有网");

                int maxAge = 20;    // 在线缓存,单位:秒
                return response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();

//                return response.newBuilder()
//                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", cacheControl)
//                        .build();
            } else {
                LOGUtils.LOG(TAG, "无网");
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + "60 * 60 * 24 * 7") //单位  秒
                        .build();
            }
        }
    }

    /**
     * 打印请求信息
     * log打印:http://blog.csdn.net/csdn_lqr/article/details/61420753
     */
    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间,纳秒
            String method = request.method();
            JSONObject jsonObject = new JSONObject();
            if ("POST".equals(method) || "PUT".equals(method)) {
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    if (body != null) {
                        for (int i = 0; i < body.size(); i++) {
                            try {
                                jsonObject.put(body.name(i), body.encodedValue(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    LOGUtils.LOG(TAG, String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                            request.url(), chain.connection(), jsonObject.toString(), request.method()));
                } else {
                    Buffer buffer = new Buffer();
                    RequestBody requestBody = request.body();
                    if (requestBody != null) {
                        request.body().writeTo(buffer);
                        String body = buffer.readUtf8();
                        LOGUtils.LOG(TAG, String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                                request.url(), chain.connection(), body, request.method()));
                    }
                }
            } else {
                LOGUtils.LOG(TAG, String.format("发送请求 %s on %s%nMethod:%s",
                        request.url(), chain.connection(), request.method()));
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            LOGUtils.LOG(TAG,
                    String.format("Retrofit接收响应: %s %n返回json:【%s】 %n耗时：%.1fms",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d
                    ));
            return response;
        }

    }

    /**
     * 打印log日志：该拦截器用于记录应用中的网络请求的信息
     */
    private HttpLoggingInterceptor getHttpLogingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //包含所有的请求信息
                //如果收到响应是json才打印
                if ("{".equals(message) || "[".equals(message)) {
                    LOGUtils.LOG(TAG, "收到响应: " + message);
                }
                LOGUtils.LOG(TAG, "message=" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    private String BASE_URL_OTHER = "http://wthrcdn.etouch.cn/";

    /**
     * 添加可以处理多个Baseurl的拦截器：http://blog.csdn.net/qq_36707431/article/details/77680252
     * Retrofit(OKHttp)多BaseUrl情况下url实时自动替换完美解决方法:https://www.2cto.com/kf/201708/663977.html
     * <p>
     * //     http://wthrcdn.etouch.cn/weather_mini?city=北京
     * //    @Headers({"url_name:other"})
     * //    @GET("weather_mini")
     * //    Observable<WeatherEntity> getMessage(@Query("city") String city);
     */
    private class MutiBaseUrlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取request
            Request request = chain.request();
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //获取request的创建者builder
            Request.Builder builder = request.newBuilder();
            //从request中获取headers，通过给定的键url_name
            List<String> headerValues = request.headers("url_name");
            if (headerValues != null && headerValues.size() > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("url_name");
                //匹配获得新的BaseUrl
                String headerValue = headerValues.get(0);
                HttpUrl newBaseUrl = null;
                if ("other".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(BASE_URL_OTHER);
//                } else if ("other".equals(headerValue)) {
//                    newBaseUrl = HttpUrl.parse(BASE_URL_PAY);
                } else {
                    newBaseUrl = oldHttpUrl;
                }
                //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme("http")//更换网络协议,根据实际情况更换成https或者http
                        .host(newBaseUrl.host())//更换主机名
                        .port(newBaseUrl.port())//更换端口
                        .removePathSegment(0)//移除第一个参数v1
                        .build();
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                LOGUtils.LOG(TAG, "Url" + "intercept: " + newFullUrl.toString());
                return chain.proceed(builder.url(newFullUrl).build());
            }
            return chain.proceed(request);
        }
    }

}
