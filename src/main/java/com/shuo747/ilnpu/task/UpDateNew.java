package com.shuo747.ilnpu.task;


import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.Grade;
import com.shuo747.ilnpu.entity.News;
import com.shuo747.ilnpu.repository.GradeDao;
import com.shuo747.ilnpu.repository.NewsDao;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class UpDateNew {

    private static final Logger logger = LoggerFactory.getLogger(GradeTask.class);
    @Autowired
    private NewsDao newsDao;


    @Async
    public void updateNew(Elements trs,int i) {
        Elements as = trs.get(i).select("a");
        String href = as.get(0).attr("href");
        Elements span = trs.get(i).select("span[class=\"timestyle27654\"]");
        //System.out.println(as.get(0).text());
        //System.out.println(span.get(0).text());
        //System.out.println(href);


        Document doc2 = Jsoup.parse(OkHttpUtil.get(Url.SCHOOL_DOMAIN+href,null));
        //原方案
        //Elements table2 = doc2.select("div[class=\"Section0\"]");
        Elements table2 = doc2.select("div[class=\"v_news_content\"]");
        //System.out.println(table2.toString());


        //try {
        News news = new News(as.get(0).text(),span.get(0).text(),table2.toString().replaceAll("<style>.*?</style>",""));
        if (newsDao.checkExistNews(news.getTitle())>0){
            //logger.info("--------------------------，find update News title={} time={}",news.getTitle(),news.getTime());
        }else {
            logger.info("定时任务，find update News title={} time={}",news.getTitle(),news.getTime());
            newsDao.save(news);
        }

        //} catch (Exception e) {
        //e.printStackTrace();
        //}


    }

}
