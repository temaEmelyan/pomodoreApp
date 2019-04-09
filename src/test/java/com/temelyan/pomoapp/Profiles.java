package com.temelyan.pomoapp;

class Profiles {

    private static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    static String getActiveDbProfile() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            return Profiles.HSQL_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.postgresql.Driver");
                return POSTGRES_DB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }
}

