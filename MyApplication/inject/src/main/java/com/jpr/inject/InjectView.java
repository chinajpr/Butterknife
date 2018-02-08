package com.jpr.inject;

import android.app.Activity;
import android.os.Parcelable;


public class InjectView {
    public static void bind(Activity activity) {
        String claName = activity.getClass().getName();
        try {
            Class<?> viewBidClass = Class.forName(claName + "$$ViewBinder");
            ViewBinder viewBinder = (ViewBinder) viewBidClass.newInstance();
            viewBinder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
