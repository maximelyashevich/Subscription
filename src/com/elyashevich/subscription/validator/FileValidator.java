package com.elyashevich.subscription.validator;

import java.io.File;

public class FileValidator {
    public static boolean isFileReadable(File file) {
        return file!=null&& file.isFile() && file.exists() && file.canRead();
    }
}
