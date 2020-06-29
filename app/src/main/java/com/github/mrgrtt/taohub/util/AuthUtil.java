package com.github.mrgrtt.taohub.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.model.User;

public class AuthUtil {
    public static void saveLoginState(User user, Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sp.edit().putLong(context.getString(R.string.preference_user_id_key), user.getId())
                .putString(context.getString(R.string.preference_username_key), user.getUsername())
                .apply();
    }

    public static void removeLoginState(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sp.edit().remove(context.getString(R.string.preference_user_id_key))
                .remove(context.getString(R.string.preference_username_key)).apply();
    }

    public static long getCurrentUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sp.getLong(context.getString(R.string.preference_user_id_key), 0L);
    }

    public static String getCurrentUsername(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sp.getString(context.getString(R.string.preference_username_key), "写Bug快乐");
    }
}
