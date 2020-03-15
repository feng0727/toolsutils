package com.hhcx.hhcx_report.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

//import org.apache.wicket.util.file.File;


public class PropertiesUtilFx {

    private static Properties properties;

    private String proper_resource;
    public void setProper_resource(String proper_resource) {
        this.proper_resource = proper_resource;
    }

    public PropertiesUtilFx(String proper_resource) {
        this.proper_resource = proper_resource;
    }

    public Map<String,String> getProperties(){
        properties = getInstance();
        try {
            InputStream inputStream = PropertiesUtilFx.class.getClassLoader().getResourceAsStream(proper_resource);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> regMap = new HashMap<String, String>();

        regMap.put("uyunbaseuri", properties.getProperty("uyunbaseuri"));
        regMap.put("modelids", properties.getProperty("modelids"));
        regMap.put("modellist", properties.getProperty("modellist"));
        return regMap;
    }

    public static Properties getInstance(){
        if(null == properties){
            properties = new Properties();
        }
        return properties;
    }

    public static Properties loadResource(String file){
        properties = getInstance();
        try {
            InputStream inputStream = PropertiesUtilFx.class.getClassLoader().getResourceAsStream(file);
            System.out.println(inputStream);
            if(properties != null){
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void main(String[] args) {
        PropertiesUtilFx pu = new PropertiesUtilFx("reportparams.properties");
        Map<String, String> propsMap = pu.getProperties();

        for(Entry<String, String> entry : propsMap.entrySet()) {
            System.out.println(("key: " + entry.getKey() + ", value: " + entry.getValue()));
        }

        Properties properties = loadResource("reportparams.properties");
        System.out.println(properties.getProperty("uyunbaseuri"));
    }
}