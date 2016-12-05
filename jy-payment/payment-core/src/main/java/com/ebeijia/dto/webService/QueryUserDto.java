package com.ebeijia.dto.webService;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by YJ on 2016/9/26.
 */
@XStreamAlias("root")
public class QueryUserDto {

    @XStreamImplicit(itemFieldName = "Result")
    private List<QueryUser> result;

    @XStreamAlias("Flag")
    private String flag;


    public List<QueryUser> getResult() {
        return result;
    }

    public void setResult(List<QueryUser> result) {
        this.result = result;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


}
