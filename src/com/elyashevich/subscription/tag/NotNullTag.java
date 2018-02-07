package com.elyashevich.subscription.tag;

import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.util.TextConstant;

public class NotNullTag {
    public static Genre notNull(Object ob) {
        return ob == null ? new Genre(TextConstant.WITHOUT_GENRE) : (Genre) ob;
    }
}
