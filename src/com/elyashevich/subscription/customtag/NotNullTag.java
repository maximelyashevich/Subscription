package com.elyashevich.subscription.customtag;

import com.elyashevich.subscription.entity.Genre;

public class NotNullTag {
    public static Genre notNull(Object ob) {
        Genre res = null;
        if (ob == null) {
            res = new Genre("без жанра");
        } else {
            res = (Genre) ob;
        }
        return res;
    }
}
