package com.garderob.web_garderob.dto;

import java.io.Serializable;

public class NewFavouriteDto implements Serializable {
    private long bottomId;
    private long top1layerId;
    private long top2layerId;
    private long dress;
    private long shoes;


    public long getBottomId() {
        return bottomId;
    }

    public void setBottomId(long bottomId) {
        this.bottomId = bottomId;
    }

    public long getTop1layerId() {
        return top1layerId;
    }

    public void setTop1layerId(long top1layerId) {
        this.top1layerId = top1layerId;
    }

    public long getTop2layerId() {
        return top2layerId;
    }

    public void setTop2layerId(long top2layerId) {
        this.top2layerId = top2layerId;
    }

    public long getDress() {
        return dress;
    }

    public void setDress(long dress) {
        this.dress = dress;
    }

    public long getShoes() {
        return shoes;
    }

    public void setShoes(long shoes) {
        this.shoes = shoes;
    }
}
