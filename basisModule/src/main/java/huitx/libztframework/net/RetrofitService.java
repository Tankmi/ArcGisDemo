package huitx.libztframework.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者：ZhuTao
 * 创建时间：2019/3/20 : 11:09
 * 描述：进行Retrofit网络请求接口的实例——发布动态
 */
public interface RetrofitService {

    @GET
    Observable<ResponseBody> getData(@Url String url);

    //    @GET
//    Observable<ResponseBody> getDatas(@Url String url, @HeaderMap Map<String, Object> headers);
    @GET
    Observable<ResponseBody> getData(@Url String url, @HeaderMap Map<String, Object> headers);

    @GET
    Observable<ResponseBody> getData(@Url String url, @QueryMap HashMap<String, Object> params);


    @POST
    Observable<ResponseBody> postData(@Url String url);


    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@HeaderMap Map<String, Object> headers, @Url String url);


    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@HeaderMap Map<String, Object> headers, @Url String url, @FieldMap Map<String, Object> params);

    @POST
    Observable<ResponseBody> postData(@Url String url, @HeaderMap Map<String, Object> headersMap, @Body RequestBody body);


    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);

    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(@Url String url, @Part Part part);

    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part part);

    @POST
    @Multipart
    Observable<ResponseBody> uploadFiles(@Url String url, @Part List<MultipartBody.Part> list);

    //上传文件
    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(@Url String url, @HeaderMap Map<String, Object> headers, @Part MultipartBody.Part part);

    @POST
    Observable<ResponseBody> postHeader(@Url String url, @HeaderMap HashMap<String, String> header, @Body MultipartBody file);



}
