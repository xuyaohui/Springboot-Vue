package com.teradata.utils;


public class Constant {

    public static final String JWTSIGNINGKEY ="base64EncodedSecretKey";


    //由於涉及多個庫的CRUD操作，將庫名設置成常量，在mappper中直接引用
    //客群過期短信提醒庫名
    public static final String BASS_USER_DATABASE="PD_TAGCFG";
    //用戶信息管理庫名
    public static final String USER_DATABASE="PD_TAGCFG";
}
