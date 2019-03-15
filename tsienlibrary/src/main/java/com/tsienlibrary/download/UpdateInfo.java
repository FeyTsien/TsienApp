package com.tsienlibrary.download;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/19
 *     desc   :
 * </pre>
 */
public class UpdateInfo {

    private int version;

    private String description;

    private String url;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
