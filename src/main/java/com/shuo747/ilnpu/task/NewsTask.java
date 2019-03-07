package com.shuo747.ilnpu.task;

import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.News;
import com.shuo747.ilnpu.repository.NewsDao;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsTask {


    private static final Logger logger = LoggerFactory.getLogger(NewsTask.class);
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private UpDateNew upDateNew;

    /**
     * 每间隔一小时更新校园通知
     */
    @Scheduled(fixedRate = 1000*60*60)
    public void updateNews() {
        logger.info("定时任务，updateNews");
        String res = OkHttpUtil.get(Url.SCHOOL_NEWS,null);
        Document doc = Jsoup.parse(res);
        Elements table = doc.select("table[class=\"winstyle27654\"]");
        if (table.size()<=0)
            return;
        Elements trs = doc.select("tr[height=\"20\"]");
        //System.out.println(trs.text());
        if (trs.size()<=0)
            return;
        for (int i = 0; i<trs.size();i++){

            upDateNew.updateNew(trs,i);

        }


    }
}
