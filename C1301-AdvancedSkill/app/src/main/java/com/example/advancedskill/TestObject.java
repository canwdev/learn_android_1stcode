package com.example.advancedskill;


import android.widget.Toast;

import java.io.Serializable;

/**
 * 通过序列化(Serializable)方式，使Intent能传递此对象
 * 还可以通过Parcelable方式，此处不举例，复杂但效率比序列化高。
 */

public class TestObject implements Serializable{
    private int integer;
    private String string;

    public TestObject() {
    }

    public TestObject(int i, String s) {
        integer = i;
        string = s;
    }

    public static void tShort(String string) {
        Toast.makeText(MyApplication.getContext(), string, Toast.LENGTH_SHORT).show();
    }

    public static void tLong(String string) {
        Toast.makeText(MyApplication.getContext(), string, Toast.LENGTH_LONG).show();
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
