package kr.ac.jbnu.se.foodtruckowner;

import com.github.kimkevin.cachepot.CachePot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 김현표 on 2016-11-27.
 */

public class CustomCachePot extends CachePot {

    private static CachePot instance;


    public static CachePot getInstance() {
        if (instance == null) {
            synchronized (CustomCachePot.class) {
                if (instance == null) {
                    instance = new CustomCachePot();
                }
            }
        }
        return instance;
    }
    private Map<Class<?>, Object> dataMap;
    private Map<Integer, Object> dataListMap;

    public <T> void push(T data) {
        if (dataMap == null) {
            dataMap = new HashMap<>();
        }

        dataMap.put(data.getClass(), data);
    }

    public <T> T pop(Class classType) {
        T value = (T) dataMap.get(classType);
        return value;
    }
    @Override
    public <T> void push(int position, T data) {
        if (dataListMap == null) {
            dataListMap = new HashMap<>();
        }
        dataListMap.put(position, data);
    }

    @Override
    public <T> T pop(int position) {
        T value = (T) dataListMap.get(position);
        return value;
    }

}
