package br.com.iuryalmeida.TaskEase.Utils;

import java.lang.reflect.Field;

public class Utils {
    public static void copyNonNullProperties(Object src, Object target) {
        Field[] fields = src.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(src);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
            }
        }
    }
}