package com.util;

import java.io.File;

/**
 * Created by yyq on 11/25/15.
 */
public class ParentPath {
    public static String getParentPath(String path){
        String parentPath = path.substring(0, path.lastIndexOf(File.separator));
        return parentPath;
    }
}
