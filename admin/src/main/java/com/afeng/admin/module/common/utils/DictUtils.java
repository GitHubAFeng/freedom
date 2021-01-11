package com.afeng.admin.module.common.utils;

import com.afeng.admin.common.cache.RedisUtils;
import com.afeng.admin.module.admin.model.DictData;
import com.afeng.admin.module.common.constant.Constants;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author ruoyi
 */
public class DictUtils {

    /**
     * 设置字典缓存
     *
     * @param key       参数键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(RedisUtils cacheUtil, String key, List<DictData> dictDatas) {
        Map<String, Object> cdata = new HashMap<>();
        cdata.put(getCacheKey(key), dictDatas);
        cacheUtil.hmset(Constants.SYS_DICT_CACHE, cdata);
    }

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<DictData> getDictCache(RedisUtils cacheUtil, String key) {
        Map<Object, Object> data = cacheUtil.hmget(Constants.SYS_DICT_CACHE);
        Object cacheObj = MapUtils.getObject(data, getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache(RedisUtils cacheUtil) {
        cacheUtil.del(Constants.SYS_DICT_CACHE);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
