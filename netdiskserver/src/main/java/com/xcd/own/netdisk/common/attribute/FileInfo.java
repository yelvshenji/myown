package com.xcd.own.netdisk.common.attribute;

import java.io.Serializable;

/**
 * Created by Administrator on 14-10-26.
 */
public class FileInfo implements Serializable {
    private String fname;
    private String fpath;
    private String fowner;
    private String fsize;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public String getFowner() {
        return fowner;
    }

    public void setFowner(String fowner) {
        this.fowner = fowner;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }
}
