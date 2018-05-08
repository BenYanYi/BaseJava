package com.mylove.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author myLove
 * @time 2017/10/24 9:47
 * @overview
 */

public class SharedUtil {
    @SuppressLint("StaticFieldLeak")
    private static SharedUtil instance;
    private SharedPreferences sha;
    private SharedPreferences.Editor oEditor;
    private String name;
    private Context mContext;

    private SharedUtil(String name, Context context) {
        this.name = name;
        this.mContext = context;
    }

    public static SharedUtil getInstance(String name, Context context) {
        if (instance == null) {
            synchronized (SharedUtil.class) {
                if (instance == null) {
                    instance = new SharedUtil(name, context);

                }
            }
        }
        return instance;
    }

    /**
     * 数据保存本地
     *
     * @param key    保存的key值
     * @param values 保存的values
     */
    public void saveShaString(String key, String values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putString(key, values);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key    保存的key值
     * @param values 保存的values
     */
    public void saveShaBoolean(String key, boolean values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putBoolean(key, values);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key    保存的key值
     * @param values 保存的values
     */
    public void saveShaInt(String key, int values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putInt(key, values);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key    保存的key值
     * @param values 保存的values
     */
    public void saveShaLong(String key, long values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putLong(key, values);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key    保存的key值
     * @param values 保存的values
     */
    public void saveShaFloat(String key, float values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putFloat(key, values);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key 保存的key值
     * @param set 保存的数组
     */
    public void saveShaSet(String key, Set<String> set) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putStringSet(key, set);
        oEditor.apply();
    }

    /**
     * 数据保存本地
     *
     * @param key  保存的key值
     * @param list 保存的集合
     */
    public <T> void saveShaList(String key, List<T> list) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        Gson gson = new Gson();
        String str = gson.toJson(list);
        oEditor.putString(key, str);
//        oEditor.putStringSet(key, set);
        oEditor.apply();
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public Set<String> getShaSet(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        Set<String> set = sha.getStringSet(key, new HashSet<String>());
        return set;
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public String getShaString(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getString(key, "");
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    public String getShaString(String key, String defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getString(key, defaultStr);
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public boolean getShaBoolean(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getBoolean(key, false);
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    public boolean getShaBoolean(String key, boolean defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getBoolean(key, defaultStr);
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public int getShaInt(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getInt(key, -1);
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    public int getShaInt(String key, int defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getInt(key, defaultStr);
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public float getShaFloat(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getFloat(key, -1f);
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    public float getShaFloat(String key, float defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getFloat(key, defaultStr);
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public long getShaLong(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getLong(key, -1L);
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    public long getShaLong(String key, long defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getLong(key, defaultStr);
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    @Deprecated
    public <T> List<T> getShaList(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String str = sha.getString(key, "");
        if (StringUtil.isNotEmpty(str)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>() {
            }.getType();
            return gson.fromJson(str, type);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public <T> List<T> getShaList(String key, Class<T> tClass) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String str = sha.getString(key, "");
        List<T> oList = new ArrayList<>();
        if (StringUtil.isNotEmpty(str)) {
            Gson gson = new Gson();
            try {
                JSONArray array = new JSONArray(str);
                for (int i = 0; i < array.length(); i++) {
                    String s = array.getJSONObject(i).toString();
                    oList.add(gson.fromJson(s, tClass));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return oList;
    }

    /**
     * 从本地获取数据
     *
     * @param key        保存的key值
     * @param defaultStr 取值失败返回的值
     * @return 值
     */
    @Deprecated
    public <T> List<T> getShaList(String key, List<T> defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String str = sha.getString(key, "");
        if (StringUtil.isNotEmpty(str)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>() {
            }.getType();
            return gson.fromJson(str, type);
        } else {
            return defaultStr;
        }
    }

    /**
     * 从本地获取数据
     *
     * @param key 保存的key值
     * @return 值
     */
    public <T> List<T> getShaList(String key, Class<T> tClass, List<T> defaultList) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String str = sha.getString(key, "");
        List<T> oList = new ArrayList<>();
        if (StringUtil.isNotEmpty(str)) {
            Gson gson = new Gson();
            try {
                JSONArray array = new JSONArray(str);
                for (int i = 0; i < array.length(); i++) {
                    String s = array.getJSONObject(i).toString();
                    oList.add(gson.fromJson(s, tClass));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtil.isListNotEmpty(oList)) {
            return oList;
        } else {
            return defaultList;
        }
    }
}