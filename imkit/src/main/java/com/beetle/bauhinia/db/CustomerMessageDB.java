package com.beetle.bauhinia.db;

/**
 * Created by houxh on 16/1/18.
 * FileCustomerMessageDB vs SQLCustomerMessageDB
 */
public class CustomerMessageDB extends FileCustomerMessageDB {

    private static CustomerMessageDB instance = new CustomerMessageDB();

    public static CustomerMessageDB getInstance() {
        return instance;
    }

}
