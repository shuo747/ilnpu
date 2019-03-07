package com.shuo747.ilnpu.entity;

import java.io.Serializable;
import java.util.List;

public class PCard implements Serializable {

    /**
     * useraccountheader : 账号</td>卡类型</td>账户余额</td>卡内余额</td>当前过渡余额</td>上次过渡余额</td>自动转账警戒额</td>自动转账额</td>冻结状态</td>挂失状态</td>银行卡号</td></tr>
     * sno : 1611030213
     * name : 张硕
     * userAccountList : [[72999,"800-正式卡 ","0.00","0.00","0.00",0,"20.00","50.00","正常","正常","62166305****3823"]]
     * userAccinfoList : []
     */
    private String useraccountheader;
    private String sno;
    private String name;
    private List<List<String>> userAccountList;
    private List<?> userAccinfoList;

    public void setUseraccountheader(String useraccountheader) {
        this.useraccountheader = useraccountheader;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserAccountList(List<List<String>> userAccountList) {
        this.userAccountList = userAccountList;
    }

    public void setUserAccinfoList(List<?> userAccinfoList) {
        this.userAccinfoList = userAccinfoList;
    }

    public String getUseraccountheader() {
        return useraccountheader;
    }

    public String getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public List<List<String>> getUserAccountList() {
        return userAccountList;
    }

    public List<?> getUserAccinfoList() {
        return userAccinfoList;
    }

    @Override
    public String toString() {
        return "PCard{" +
                "useraccountheader='" + useraccountheader + '\'' +
                ", sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", userAccountList=" + userAccountList +
                ", userAccinfoList=" + userAccinfoList +
                '}';
    }
}
