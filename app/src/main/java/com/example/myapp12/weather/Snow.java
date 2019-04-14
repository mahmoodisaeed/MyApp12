
package com.example.myapp12.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow implements Serializable
{

    @SerializedName("3h")
    @Expose
    private Double _3h;
    private final static long serialVersionUID = 1274126438018211068L;

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

}
