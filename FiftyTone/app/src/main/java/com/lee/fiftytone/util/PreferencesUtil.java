package com.lee.fiftytone.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sp工具类
 *
 * @author darren
 */

public class PreferencesUtil {

    public final static int MODE = Context.MODE_PRIVATE;
    public final static String XML_NAME = "harpos_sharepreferences";
    public final static String XML_NAME_DEL = "harpos_sharepreferences_del";
    /*TradeDataDao*/
    public final static String MER_ID = "mer_id"; // 商户ID
    public final static String ID = "id"; // 用户ID

    public final static String TASK_LIST = "task_list";//正在背的任务清单
    public final static String TASK_TOTAL = "task_total";//正在执行任务的总数
    /**
     * 创建SharePreferences实例
     *
     * @param context context参数
     * @return 返回实例
     */
    private static SharedPreferences getPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(XML_NAME, MODE);
        if (preferences != null) {
            return preferences;
        }
        return null;
    }

    /**
     * 创建SharePreferences实例,每次退出程序后需要清除
     *
     * @param context context参数
     * @return 返回实例
     */
    @SuppressWarnings("unused")
    private static SharedPreferences getDelPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(XML_NAME_DEL, MODE);
        if (preferences != null) {
            return preferences;
        }
        return null;
    }

    /**
     * 保存boolean类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveBoolean(Context context, String key, Boolean value) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            edit.putBoolean(key, value);
            edit.commit();
            return true;
        }
        return false;
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            return preferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 保存String类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveString(Context context, String key, String value) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            edit.putString(key, value);
            edit.commit();
            return true;
        }
        return false;
    }

    /**
     * 判断json中是否含有该key，返回stringValue或null
     */
    public static String getStringByKey(JSONObject jsonObject, String jsonKey) {
        if (!jsonObject.has(jsonKey)) return null;
        try {
            return jsonObject.getString(jsonKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            return preferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public static void clearSp(Context context) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            preferences.edit().clear().commit();
        }
    }

    /**
     * 保存set类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveStringSet(Context context, String key, Set<String> value) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            edit.putStringSet(key, value);
            edit.commit();
            return true;
        }
        return false;
    }

    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            return preferences.getStringSet(key, new HashSet<String>());
        }
        return null;
    }

    /**
     * 保存long类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveLong(Context context, String key, long value) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            edit.putLong(key, value);
            edit.commit();
            return true;
        }
        return false;
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            return preferences.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 保存list类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveListOrMap(Context context, String key, List value) {
        SharedPreferences preferences = getDelPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            try {
                String liststr = object2String(value);
                edit.putString(key, liststr);
                edit.commit();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 保存list类型数据
     *
     * @param key
     * @param value
     */
    public static boolean saveListOrMap(Context context, String key, Map value) {
        SharedPreferences preferences = getDelPreferences(context);
        if (preferences != null) {
            Editor edit = preferences.edit();
            try {
                String liststr = object2String(value);
                edit.putString(key, liststr);
                edit.commit();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List getList(Context context, String key) {
        SharedPreferences preferences = getDelPreferences(context);
        List showSceneList = null;
        if (preferences != null) {
            try {
                showSceneList = String2List(preferences.getString(key, ""));
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return showSceneList;
    }

    public static void clearDelData(Context context, String key) {
        SharedPreferences preferences = getDelPreferences(context);
        if (preferences != null) {
            preferences.edit().remove(key).commit();
        }
    }

    public static Map<String, Integer> getMap(Context context, String key) {
        SharedPreferences preferences = getDelPreferences(context);
        Map<String, Integer> showSceneMap = null;
        if (preferences != null) {
            try {
                showSceneMap = String2Map(preferences.getString(key, ""));
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return showSceneMap;
    }

    private static String object2String(List SceneList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;

    }

    @SuppressWarnings("unchecked")
    public static List String2List(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }

    private static String object2String(Map SceneList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;

    }

    @SuppressWarnings("unchecked")
    private static Map<String, Integer> String2Map(String objectString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Map<String, Integer> sceneMap = (Map<String, Integer>) objectInputStream
                .readObject();
        objectInputStream.close();
        return sceneMap;
    }

}
