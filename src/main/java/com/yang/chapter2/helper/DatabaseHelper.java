package com.yang.chapter2.helper;

import com.yang.chapter2.util.PropsUtil;
import com.yang.chapter2.util.StringUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author yangle
 */
public class DatabaseHelper {
    private static final Logger loger= LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNER=new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL=new ThreadLocal<Connection>();
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    static {
        Properties properties= PropsUtil.loadProps("config.properties");
        DRIVER=properties.getProperty("jdbc.driver");
        URL=properties.getProperty("jdbc.url");
        USER=properties.getProperty("jdbc.username");
        PASSWORD=properties.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            loger.error("faild to load jdbc driver",e);
        }
    }
    /**
     * 获取数据库连接
     */
    public static Connection getConnection(){

        Connection connection=CONNECTION_THREAD_LOCAL.get();
        if(connection==null){
        try {
            connection= DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            loger.error("faild to create connection",e);
            throw new RuntimeException();
        }finally {
            CONNECTION_THREAD_LOCAL.set(connection);
        }
      }
        return connection;
    }


/**
 * 关闭数据库连接
 */
public static void closeConnection(){
    Connection connection=CONNECTION_THREAD_LOCAL.get();
    if(connection!=null){
        try {
            connection.close();
        } catch (SQLException e) {
            loger.error("faild to close connection",e);
            throw new RuntimeException();
        }finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }
}

/**
 * 查询实体列表
 */
public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object ... params){

    List<T> entityList=null;
    try {
        Connection connection=getConnection();
        entityList=QUERY_RUNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        closeConnection();
    }
    return entityList;

}
/**
 * 获取实体
 */
public static <T> T queryEntity(Class<T> entityClass,String sql,Object params){
    T entity;
    Connection connection=getConnection();

    try {
        entity=QUERY_RUNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
    } catch (SQLException e) {
      loger.error("execute sql query failture",e);
      throw new RuntimeException();
    }finally {
        closeConnection();
    }
    return entity;
}


    /**
     * 执行查询语句
     */
    public static  List<Map<String,Object>> executeQuery(String sql, Object ... params){

        List<Map<String,Object>> entityList=null;
        try {
            Connection connection=getConnection();
            entityList=QUERY_RUNER.query(connection,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entityList;

    }


    /**
     * 执行更新语句
     */
    public static  int executeUpdate(String sql, Object ... params){

        int rows=0;
        try {
            Connection connection=getConnection();
            rows=QUERY_RUNER.update(connection,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return rows;

    }

    /**
     * 执行添加操作
     */
    public static  <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){


        if(fieldMap==null){
            loger.error("faild to inset entity:fieldMap is null");
            return false;
        }
        String sql="insert into "+getTableName(entityClass);
        StringBuilder colloumns=new StringBuilder("(");
        StringBuilder values=new StringBuilder("(");
        for(String name:fieldMap.keySet()){
            colloumns.append(name).append(", ");
            values.append("?, ");
        }
        colloumns.replace(colloumns.lastIndexOf(", "),colloumns.length(),")");
        values.replace(values.lastIndexOf(", "),values.length(),")");
        sql+=colloumns+" VALUES "+values;
        Object[] params=fieldMap.values().toArray();

        return executeUpdate(sql,params)==1;

    }

    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 更新实体
      */

    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(fieldMap==null){
            loger.error("faild to update entity:fieldMap is null");
            return false;
        }
        String sql="UPDATE "+getTableName(entityClass)+" SET ";
        StringBuilder coloumns=new StringBuilder();
        StringBuilder values=new StringBuilder();
        for (String name:fieldMap.keySet()){
            coloumns.append(name).append("=?, ");
        }
        sql+=coloumns.substring(0,coloumns.lastIndexOf(", "))+" WHERE id=?";
        List<Object> paramList=new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] paramms=paramList.toArray();
        return executeUpdate(sql,paramms)==1;

    }
    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql="delete from "+getTableName(entityClass)+" where id=?";
        return executeUpdate(sql,id)==1;
    }
}
