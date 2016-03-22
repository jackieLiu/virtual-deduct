package com.ai.runner.center.omc.virtualdeduct.utils;

public enum DbTypeEnum {
	AR("ar"), 
    ABM("abm"),
    INV("inv");    
    private String value;
     
    private DbTypeEnum(String value){
        this.value = value;
    }
     
     
    public static String getDbTypeName(DbTypeEnum typeEnum){
         
        return typeEnum.value;
    }
}
