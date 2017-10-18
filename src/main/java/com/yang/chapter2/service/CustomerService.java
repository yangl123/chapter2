package com.yang.chapter2.service;

import com.yang.chapter2.helper.DatabaseHelper;
import com.yang.chapter2.model.Customer;
import com.yang.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     */
    public List<Customer> getCutomerList(String keyWord) {
        List<Customer> customerList = null;

        String sql = "SELECT * FROM customer";

        customerList=  DatabaseHelper.queryEntityList(Customer.class, sql);
        return customerList;
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id) {

        String sql="select * from customer where id=?";

        return DatabaseHelper.queryEntity(Customer.class,sql,id);
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
