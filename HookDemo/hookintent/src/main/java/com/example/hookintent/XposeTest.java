package com.example.hookintent;

import android.content.Intent;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposeTest implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        hookIntent(loadPackageParam);

    }




    private void hookIntent(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "startActivity", Intent.class, new XC_MethodHook() {

//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                Log.e("intent", "after---------------");
//                Intent intent = (Intent) param.args[0];
//                IntentLogger.dump("intent", intent);
//
//            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Intent intent = (Intent) param.args[0];
                IntentLogger.dump("intent", intent);
            }
        });
    }

}
