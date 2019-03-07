package com.shuo747.ilnpu.utils.okhttp;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.PCard;
import com.shuo747.ilnpu.utils.rc.RCYKT;
import com.shuo747.ilnpu.utils.rc.RecognizeCode;
import okhttp3.*;
//import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil{
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static  String get(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);
            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String getForHeader(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .addHeader("key", "value")
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
           //logger.error("okhttp3 put error >> ex = {}", e);
            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);
            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public static String postXmlParams(String url, String xml) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);
            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }




    public static String post3(String url, String url2,Map<String, String> params,Map<String, String> params2) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {


                FormBody.Builder builder2 = new FormBody.Builder();
                //添加参数
                if (params != null && params2.keySet().size() > 0) {
                    for (String key : params2.keySet()) {
                        builder2.add(key, params2.get(key));
                    }
                }

                Request request2 = new Request.Builder()
                        .url(url2)
                        .post(builder2.build())
                        .build();
                Response response2 = null;

                //System.out.println(response.body().string());
                logger.info("--联网post->post");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        //System.out.println(response2.body().string());
                        return response2.body().string();
                    }
                } catch (Exception e) {
                    //logger.error("okhttp3 put error >> ex = {}", e);
                    logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);
            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }




    public static String post2(String url, String url2,Map<String, String> params) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                Request request2 = new Request.Builder()
                        .url(url2)
                        .build();
                Response response2 = null;

                //System.out.println(response.body().string());
                logger.info("--联网post->get");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        //System.out.println(response2.body().string());
                        return response2.body().string();
                    }
                } catch (Exception e) {
                    //logger.error("okhttp3 put error >> ex = {}", e);

                    logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }


    /**
     * 添加了验证码的请求，该方法仅适用于教务处
     * @param url 登录
     * @param url2 登录后要获取的内容
     * @param params 登录所需
     * @return
     */
    public static String post4(String url, String url2,Map<String, String> params) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数

        Request request = new Request.Builder()
                .url(Url.JWC_VALIDATE_7001)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                BufferedImage img = ImageIO.read(response.body().byteStream());
                String validate = RecognizeCode.recognize(img);
                System.out.println("验证码是"+validate);
                params.put("Agnomen", validate);
                if (params != null && params.keySet().size() > 0) {
                    for (String key : params.keySet()) {
                        builder.add(key, params.get(key));
                    }
                }

                Request request2 = new Request.Builder()
                        .url(url)
                        .post(builder.build())
                        .build();
                Response response2 = null;
                Response response3 = null;
                //System.out.println(response.body().string());
                logger.info("--联网(教务处含验证码)post->get");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        System.out.println("response2\n"+response2.body().string());

                        Request request3 = new Request.Builder()
                                .url(url2)
                                .build();

                        response3 = okHttpClient.newCall(request3).execute();
                        return response3.body().string();

                    }
                } catch (Exception e) {
                    //logger.error("okhttp3 put error >> ex = {}", e);

                    logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());

                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                    if (response3 != null) {
                        response3.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }



    /**
     * 添加了验证码的请求，该方法仅适用于判断教务处用户密码匹配
     * @param params 登录所需
     * @return
     */
    public static String post5(Map<String, String> params) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数

        Request request = new Request.Builder()
                .url(Url.JWC_VALIDATE_7001)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                BufferedImage img = ImageIO.read(response.body().byteStream());
                String validate = RecognizeCode.recognize(img);
                //System.out.println("验证码是"+validate);
                params.put("Agnomen", validate);
                if (params != null && params.keySet().size() > 0) {
                    for (String key : params.keySet()) {
                        builder.add(key, params.get(key));
                    }
                }

                Request request2 = new Request.Builder()
                        .url(Url.JWC_LOGIN_7001)
                        .post(builder.build())
                        .build();
                Response response2 = null;
                //System.out.println(response.body().string());
                logger.info("--联网(教务处含验证码)post");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        return response2.body().string();

                    }
                } catch (Exception e) {
                    //logger.error("okhttp3 put error >> ex = {}", e);

                    logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }



    /**
     * 添加了验证码的请求，该方法仅适用于一卡通
     * @param url 登录
     * @param url2 登录后要获取的内容
     * @param sid 登录所需
     * @return
     */
    public static String post6(String url,String url2,String sid) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数

        Request request = new Request.Builder()
                .url(Url.PCARD_VALIDATE)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            Map<String,String> params = new HashMap();
            int status = response.code();
            if (response.isSuccessful()) {
                BufferedImage img = ImageIO.read(response.body().byteStream());
                int validate = RCYKT.recognize(img);
                System.out.println("验证码是"+validate);
                params.put("randcode", String.valueOf(validate));
                params.put("userpwd", "111111");
                params.put("username", sid);
                params.put("usertype", "2");
                params.put("logintype", "1");
                if (params != null && params.keySet().size() > 0) {
                    for (String key : params.keySet()) {
                        builder.add(key, params.get(key));
                    }
                }

                Request request2 = new Request.Builder()
                        .url(url)
                        .post(builder.build())
                        .build();
                Response response2 = null;
                Response response3 = null;
                System.out.println(response.body().string());
                logger.info("--联网(一卡通含验证码)post->get");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        System.out.println("response2\n"+response2.body().string());

                        Request request3 = new Request.Builder()
                                .url(url2)
                                .build();

                        response3 = okHttpClient.newCall(request3).execute();
                        return response3.body().string();

                    }
                } catch (Exception e) {
                    logger.error("okhttp3 put error >> exName = {}", e.getClass().getName());
                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                    if (response3 != null) {
                        response3.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }


    /**
     * 获取近7天交易
     * 添加了验证码的请求，该方法仅适用于一卡通两步post请求
     * @param url 登录
     * @param url2 登录后要获取的内容
     * @param sid 登录所需
     * @return
     */
    public static String post7(String url, String url2, String sid, Map<String, String> map) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数

        Request request = new Request.Builder()
                .url(Url.PCARD_VALIDATE)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .build();
            response = okHttpClient.newCall(request).execute();
            Map<String, String> params = new HashMap();
            int status = response.code();
            if (response.isSuccessful()) {
                BufferedImage img = ImageIO.read(response.body().byteStream());
                int validate = RCYKT.recognize(img);
                System.out.println("验证码是" + validate);
                params.put("randcode", String.valueOf(validate));
                params.put("userpwd", "111111");
                params.put("username", sid);
                params.put("usertype", "2");
                params.put("logintype", "1");
                if (params != null && params.keySet().size() > 0) {
                    for (String key : params.keySet()) {
                        builder.add(key, params.get(key));
                    }
                }

                Request request2 = new Request.Builder()
                        .url(url)
                        .post(builder.build())
                        .build();
                Response response2 = null;
                Response response3 = null;
                Response response4 = null;
                System.out.println(response.body().string());
                logger.info("--联网(一卡通含验证码)post->get");
                try {
                    response2 = okHttpClient.newCall(request2).execute();
                    if (response2.isSuccessful()) {
                        String res = response2.body().string();
                        System.out.println("res" + res);


                        Request request3 = new Request.Builder()
                                .url(Url.PCARD_ACCOUNT_SHOW)
                                .build();
                        response3 = okHttpClient.newCall(request3).execute();
                        if (response3.isSuccessful()) {
                            PCard pCard = new Gson().fromJson(response3.body().string(), PCard.class);
                            FormBody.Builder builder2 = new FormBody.Builder();
                            map.put("accquary", pCard.getUserAccountList().get(0).get(0));
                            if (map != null && map.keySet().size() > 0) {
                                for (String key : map.keySet()) {
                                    builder2.add(key, map.get(key));
                                }
                            }
                            Request request4 = new Request.Builder()
                                    .url(url2)
                                    .post(builder2.build())
                                    .build();

                            response4 = okHttpClient.newCall(request4).execute();
                            //System.out.println("response3\n"+response3.body().string());
                            return response4.body().string();
                        }
                    }
                } catch (Exception e) {
                    logger.error("okhttp3 put error getPCardbill>> exName = {}", e.getClass().getName());
                } finally {
                    if (response2 != null) {
                        response2.close();
                    }
                    if (response3 != null) {
                        response3.close();
                    }
                    if (response4 != null) {
                        response4.close();
                    }
                }
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);

            logger.error("okhttp3 put error >> exName = {} exMessage = {}", e.getClass().getName(),e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }
    public static class MyCookieJar implements CookieJar {
        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url, cookies);
            cookieStore.put(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"), cookies);
            for(Cookie cookie:cookies){
                System.out.println("cookie Name:"+cookie.name());
                System.out.println("cookie Path:"+cookie.path());
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"));
            if(cookies==null){
                System.out.println("没加载到cookie");
            }
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }

}
