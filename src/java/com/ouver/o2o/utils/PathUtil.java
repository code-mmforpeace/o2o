package com.ouver.o2o.utils;

/**
 * 文件 路径工具类
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "E:/projectLib";
        }else {
            basePath = "/home/test";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop"+ shopId +"/";
        return imagePath.replace("/",seperator);
    }

    public static String getHeadLineImgPath(int lineId){
        String imagePath = "/upload/item/headline"+lineId+"/";
        return imagePath.replace("/",seperator);
    }
}
