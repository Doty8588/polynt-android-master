package com.Polynt.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Alex on 6/2/2015.
 */
public class Constants {

    /* Setup for Parse*/
    public static final String PRODUCTS_LIST = "products.plist";
    public static final String LITERATURE_LIST = "literature.plist";
    public static final String PRODUCTS_LIST_URL = "https://host2.polyntusa.com/Public/PublicProdBulletin.nsf/products.plist";
    public static final String LITERATURE_LIST_URL = "https://host2.polyntusa.com/Public/PublicProdBulletin.nsf/literature.plist";
    public static final int TYPE_PRODUCT = 1;
    public static final int TYPE_LITERATURE = 2;
    public static final String PARSE_APPLICATION_ID = "ZAr19jyIb8nSUVh7SKMh9lLo4F0ZwTk3cOCKpcxo";
    public static final String PARSE_CLIENT_KEY = "98ob6I8qQ9HknsqoBY9MyOQHhhEM2KV8UVQA8w8l";


    public static final String SQLITE_DB_FILE_NAME = "bpp.sqlite";

    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqhe4eBz9pcVwSSF2jUE94YgVmv2Sms3L/c6VHAYTUfJIi8acHS3v8/mADEb878AmsKjCwbdvMsZWu0lgDIzKAJQ0X7kmRcOnGRjqD7SfkF6wySIF2vNqK5bz0ANU+gFYo8VNAQ9KDi2B6xCFNOpZOS8joOpAH40zlC6xYv0+Fku/f4dhjJFI1yfqdeO6l6zi+bJDcb+G147UwCsnGed2o5Acq4AGrMx/W0Bmpud2ba1l3NRJgXgZiBZbfqljZooY/C2mcTblJqougbl0S9qY1AIlccx/YqO7tzI//gPtSAcd0/zjZrnrFUoHgDEm8fypETNlbKJg4Yq0qWlkIylzrwIDAQAB";

    public class ProductIds{
        public static final String kProductIdUpgradeToPro = "com.blueprint.football.upgrade_to_pro";
    }

    public static final boolean IS_PRO_MODE = false;

    public static final boolean NO_INAPP_MODE = false;
}
