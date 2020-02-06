/**
 * 
 */
package com.zhxl.arcgisdemo.main.net;


public class HomeNetEntity {

   public int code;
   public String cost;
   public String msg;
   public Data data;

   public static class Data {
           public int android;
           //强制更新版本号
           public int androidUnder;
           //废弃 0,非强制更新，1，强制更新
           public int isUpdate;
           public String androidMessage;
           //Android 更新地址
           public String androidUrl;

   }


}
