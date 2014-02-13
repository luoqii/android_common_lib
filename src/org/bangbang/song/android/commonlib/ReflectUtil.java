package org.bangbang.song.android.commonlib;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static int getIntFieldValue(Class<?> clazz, Object object, String fieldName) {
        int value = 0;
        
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.getInt(object);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return value;
    }
}
