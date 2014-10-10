package com.example.myapp.DataParse;

import com.example.myapp.Module.gtdContent;
import com.example.myapp.Module.smsContent;

import java.util.regex.Pattern;

/**
 * change data from smsContent to gtdContent
 * Created by zhuxiaolin on 2014/10/8.
 */
public class smsDataParse {

    private gtdContent gtd ;
    private smsContent sms ;

    public gtdContent parse(String str){
        System.out.println(" parse1 ");
        Pattern pattern = Pattern.compile("[,，到]");
//        this.sms = sms;
        if(str == ""){
            return null;
        }
        gtd = new gtdContent();
        //String str = sms.getSmsContent();
        String[] strs = pattern.split(str);
        System.out.println(" parse2 ");
        for (int i = 0; i < strs.length; i++) {
            gtd.setStartTime(strs[0]);
            gtd.setEndTime(strs[1]);
            gtd.setPlace(strs[2]);
            gtd.setContent(strs[3]);
            gtd.setWrite_time(System.currentTimeMillis());
            System.out.println(strs[i]);
        }
        return gtd;
    }
}
