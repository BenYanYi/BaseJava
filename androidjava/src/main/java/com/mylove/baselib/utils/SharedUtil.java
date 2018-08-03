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
 * @author yanyi
 */

public class SharedUtil {
    @SuppressLint("StaticFieldLeak")
    private static SharedUtil instance;
    private SharedPreferences sha;
    private SharedPreferences.Editor oEditor;
    private String name = "share";
    private Context mContext;

    private SharedUtil(String name, Context context) {
        this.name = name;
        this.mContext = context;
    }

    private SharedUtil(Context context) {
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

    public static SharedUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedUtil.class) {
                if (instance == null) {
                    instance = new SharedUtil(context);

                }
            }
        }
        return instance;
    }

    /**
     * save data to local
     *
     * @param key    save key
     * @param values save values
     */
    public void saveShaString(String key, String values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putString(key, values);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key    save key
     * @param values save values
     */
    public void saveShaBoolean(String key, boolean values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putBoolean(key, values);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key    save key
     * @param values save values
     */
    public void saveShaInt(String key, int values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putInt(key, values);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key    save key
     * @param values save values
     */
    public void saveShaLong(String key, long values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putLong(key, values);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key    save key
     * @param values save values
     */
    public void saveShaFloat(String key, float values) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putFloat(key, values);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key save key
     * @param set save Set
     */
    public void saveShaSet(String key, Set<String> set) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        oEditor.putStringSet(key, set);
        oEditor.apply();
    }

    /**
     * save data to local
     *
     * @param key  save key
     * @param list save Set
     * @param <T>  save type
     */
    public <T> void saveShaList(String key, List<T> list) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        oEditor = sha.edit();
        Gson gson = new Gson();
        String str = gson.toJson(list);
        oEditor.putString(key, str);
        oEditor.apply();
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in Set(default new HashSet<String>())
     */
    public Set<String> getShaSet(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        Set<String> set = sha.getStringSet(key, new HashSet<String>());
        return set;
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in String(default "")
     */
    public String getShaString(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getString(key, "");
    }

    /**
     * get data locally
     *
     * @param key        corresponding key
     * @param defaultStr default values
     * @return data in String
     */
    public String getShaString(String key, String defaultStr) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getString(key, defaultStr);
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in Boolean (default false)
     */
    public boolean getShaBoolean(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getBoolean(key, false);
    }

    /**
     * get data locally
     *
     * @param key        corresponding key
     * @param defaultBoo default values
     * @return data in Boolean
     */
    public boolean getShaBoolean(String key, boolean defaultBoo) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getBoolean(key, defaultBoo);
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in int(default -1)
     */
    public int getShaInt(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getInt(key, -1);
    }

    /**
     * get data locally
     *
     * @param key        corresponding key
     * @param defaultInt default values
     * @return data in int
     */
    public int getShaInt(String key, int defaultInt) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getInt(key, defaultInt);
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in float(default -1)
     */
    public float getShaFloat(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getFloat(key, -1f);
    }

    /**
     * get data locally
     *
     * @param key          corresponding key
     * @param defaultFloat default values
     * @return data in float
     */
    public float getShaFloat(String key, float defaultFloat) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getFloat(key, defaultFloat);
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in long(default -1)
     */
    public long getShaLong(String key) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getLong(key, -1L);
    }

    /**
     * get data locally
     *
     * @param key         corresponding key
     * @param defaultLong default values
     * @return data in float
     */
    public long getShaLong(String key, long defaultLong) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sha.getLong(key, defaultLong);
    }

    /**
     * get data locally
     *
     * @param key corresponding key
     * @return data in List(default null ArrayList)
     * not recommended for use
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
     * get data locally
     *
     * @param key    corresponding key
     * @param tClass type
     * @return data in List(default null ArrayList)
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
     * get data locally
     *
     * @param key         corresponding key
     * @param defaultList default values
     * @return data in List
     * not recommended for use
     */
    @Deprecated
    public <T> List<T> getShaList(String key, List<T> defaultList) {
        sha = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String str = sha.getString(key, "");
        if (StringUtil.isNotEmpty(str)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>() {
            }.getType();
            return gson.fromJson(str, type);
        } else {
            return defaultList;
        }
    }

    /**
     * get data locally
     *
     * @param key         corresponding key
     * @param tClass      type
     * @param defaultList default values
     * @return data in List
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