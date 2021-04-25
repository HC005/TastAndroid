
package com.wmt.hardik.model.UserList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("jsonschema2pojo")
public class Pagination {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("lastPage")
    @Expose
    private Integer lastPage;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

}
