/*
 * Copyright (c) 2014 Amberfog.
 *
 * This source code is Amberfog Confidential Proprietary
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse
 * engineer the software. Otherwise this violation would be treated by law and
 * would be subject to legal prosecution. Legal use of the software provides
 * receipt of a license from the right holder only.
 */

package com.example.ammar.languagetranslator;


import android.content.Context;

public class Country {

    private String cIdentifier;
    private String language;
    private String mCountry;
    private String mCountryCodeStr;
    private int mNum;

    public Country(Context context, String str, int num) {
        String[] data = str.split(",");
        mNum = num;
        cIdentifier = data[0];
        language = data[1];
        mCountry = data[2];
        /*mCountryCodeStr = "+" + data[2];*/
//        String fileName = String.format("f%03d", num);
//        mResId = context.getApplicationContext().getResources().getIdentifier(fileName, "drawable", context.getApplicationContext().getPackageName());
    }

    public String getcIdentifier() {
        return cIdentifier;
    }

    public String getlanguage() {
        return language;
    }

    public String getCountry() {
        return mCountry;
    }

    /*public String getCountryCodeStr() {
        return mCountryCodeStr;
    }*/


    public int getNum() {
        return mNum;
    }
}
