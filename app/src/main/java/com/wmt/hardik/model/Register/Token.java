
package com.wmt.hardik.model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("jsonschema2pojo")
public class Token {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
