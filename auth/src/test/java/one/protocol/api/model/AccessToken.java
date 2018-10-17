package one.protocol.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("exp")
    @Expose
    private String exp;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getExp() {
        return exp;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
}
