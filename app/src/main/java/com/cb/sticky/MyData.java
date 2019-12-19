package com.cb.sticky;

/**
 * Created by cb on 2019/12/16.
 */
public class MyData {
    String text;
    int type;
    String stickyText;

    MyData(String text, int type) {
        this.text = text;
        this.type = type;
    }

    MyData(String text, int type, String stickyText) {
        this.text = text;
        this.type = type;
        this.stickyText = stickyText;
    }
}
