package com.example.hookintent;

import android.content.ComponentName;
import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class IntentLogger {
    private static final HashMap<Integer, String> FLAGS = new HashMap<>();

    static {
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_TASK, "FLAG_ACTIVITY_CLEAR_TASK");
        FLAGS.put(Intent.FLAG_ACTIVITY_SINGLE_TOP, "FLAG_ACTIVITY_SINGLE_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT, "FLAG_ACTIVITY_BROUGHT_TO_FRONT");
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_TOP, "FLAG_ACTIVITY_CLEAR_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS, "FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS");
        FLAGS.put(Intent.FLAG_ACTIVITY_FORWARD_RESULT, "FLAG_ACTIVITY_FORWARD_RESULT");
        FLAGS.put(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY, "FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY");
        FLAGS.put(Intent.FLAG_ACTIVITY_MULTIPLE_TASK, "FLAG_ACTIVITY_MULTIPLE_TASK");
        FLAGS.put(Intent.FLAG_ACTIVITY_NEW_DOCUMENT, "FLAG_ACTIVITY_NEW_DOCUMENT");
        FLAGS.put(Intent.FLAG_ACTIVITY_NEW_TASK, "FLAG_ACTIVITY_NEW_TASK");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_ANIMATION, "FLAG_ACTIVITY_NO_ANIMATION");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_HISTORY, "FLAG_ACTIVITY_NO_HISTORY");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_USER_ACTION, "FLAG_ACTIVITY_NO_USER_ACTION");
        FLAGS.put(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP, "FLAG_ACTIVITY_PREVIOUS_IS_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, "FLAG_ACTIVITY_REORDER_TO_FRONT");
        FLAGS.put(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED, "FLAG_ACTIVITY_RESET_TASK_IF_NEEDED");
        FLAGS.put(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS, "FLAG_ACTIVITY_RETAIN_IN_RECENTS");
        FLAGS.put(Intent.FLAG_ACTIVITY_TASK_ON_HOME, "FLAG_ACTIVITY_TASK_ON_HOME");
        FLAGS.put(Intent.FLAG_DEBUG_LOG_RESOLUTION, "FLAG_DEBUG_LOG_RESOLUTION");
        FLAGS.put(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES, "FLAG_EXCLUDE_STOPPED_PACKAGES");
        FLAGS.put(Intent.FLAG_FROM_BACKGROUND, "FLAG_FROM_BACKGROUND");
        FLAGS.put(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION, "FLAG_GRANT_PERSISTABLE_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION, "FLAG_GRANT_PREFIX_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_GRANT_READ_URI_PERMISSION, "FLAG_GRANT_READ_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_GRANT_WRITE_URI_PERMISSION, "FLAG_GRANT_WRITE_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_INCLUDE_STOPPED_PACKAGES, "FLAG_INCLUDE_STOPPED_PACKAGES");
        FLAGS.put(Intent.FLAG_RECEIVER_FOREGROUND, "FLAG_RECEIVER_FOREGROUND");
        FLAGS.put(Intent.FLAG_RECEIVER_NO_ABORT, "FLAG_RECEIVER_NO_ABORT");
        FLAGS.put(Intent.FLAG_RECEIVER_REGISTERED_ONLY, "FLAG_RECEIVER_REGISTERED_ONLY");
        FLAGS.put(Intent.FLAG_RECEIVER_REPLACE_PENDING, "FLAG_RECEIVER_REPLACE_PENDING");
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, "FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET");
    }

    private IntentLogger() {}

    public static void dump(String tag, Intent intent) {
        if (intent == null) {
            Log.i(tag, "no intent found");
            return;
        }
        Bundle extras = intent.getExtras();
        Log.i(tag, "Intent[@" + Integer.toHexString(intent.hashCode()) + "] content:");
        Log.i(tag, "Action   : " + intent.getAction());
        Log.i(tag, "Category : " + intent.getCategories());
        Log.i(tag, "Data     : " + intent.getDataString());
        dumpComponentName(tag, intent.getComponent());
        dumpFlags(tag, intent.getFlags());
        Log.i(tag, "HasExtras: " + hasExtras(extras));
        dumpExtras(tag, extras);
    }

    public static void dumpComponentName(String tag, Intent intent) {
        if (intent == null) {
            return;
        }
        dumpComponentName(tag, intent.getComponent());
    }

    public static void dumpComponentName(String tag, ComponentName componentName) {
        if (componentName != null)
            Log.i(tag, "Component: " + componentName.getPackageName() + "/" + componentName.getClassName());
        else
            Log.i(tag, "Component: null");
    }

    public static void dumpFlags(String tag, Intent intent) {
        if (intent == null) {
            return;
        }
        dumpFlags(tag, intent.getFlags());
    }

    public static void dumpFlags(String tag, int flags) {
        Log.i(tag, "Flags    : " + Integer.toBinaryString(flags));
        for (int flag : FLAGS.keySet()) {
            if ((flag & flags) != 0) {
                Log.i(tag, "Flag     : " + FLAGS.get(flag));
            }
        }
    }

    public static void dumpExtras(String tag, Intent intent) {
        if (intent == null) {
            return;
        }
        dumpExtras(tag, intent.getExtras());
    }

    public static void dumpExtras(String tag, Bundle extras) {
        if (extras == null) {
            return;
        }
        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            if (value instanceof Bundle) {
                dumpExtras(tag, (Bundle) value);
            } else {
                try {
                    Log.i(tag, "Extra[" + key + "] :" + String.valueOf(extras.get(key)));
                } catch (BadParcelableException e) {
                    Log.w(tag, "Extra contains unknown class instance for [" + key + "]: ", e);
                }
            }
        }
    }

    public static boolean hasExtras(Intent intent) {
        return hasExtras(intent.getExtras());
    }

    public static boolean hasExtras(Bundle extras) {
        try {
            return (extras != null && !extras.isEmpty());
        } catch (BadParcelableException e) {
            Log.w("IntentLogger", "Extra contains unknown class instance: ", e);
            return true;
        }
    }
}