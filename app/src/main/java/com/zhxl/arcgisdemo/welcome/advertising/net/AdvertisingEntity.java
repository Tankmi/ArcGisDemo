/**
 * 
 */
package com.zhxl.arcgisdemo.welcome.advertising.net;


public class AdvertisingEntity {

   public int code;
   public String cost;
   public String msg;
   public Data data;

   public static class Data {
       //xmax(app) 或 pc第1张
           public String imgUrl;
           //480*800(app) 或 pc第2张
           public String imgUrl1;
           //450*800(app) ,
           public String imgUrl2;
           //8p(app) ,
           public String imgUrl3;
           //是否展示 0不展示 1展示 ,
           public int isShow;
           //类型 1app 2pc
           public String type;
   }


}
