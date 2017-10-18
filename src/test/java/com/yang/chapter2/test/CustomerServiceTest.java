package com.yang.chapter2.test;

import com.yang.chapter2.model.Customer;
import com.yang.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private final CustomerService customerService;
    public CustomerServiceTest(){

        this.customerService = new CustomerService();
    }
    @Before
    public void init(){
        //初始化数据库
    }
    @Test
    public void getCustomerListTest() throws Exception{
        long id=1;
        List<Customer> customerList=customerService.getCutomerList("");
        System.out.println(customerList);
        Assert.assertNotNull(customerList);
    }
    @Test
    public void getCustomerTest() throws Exception{
        long id=1;
        Customer customer=customerService.getCustomer(id);
        System.out.println(customer);
        Assert.assertNotNull(customer);
    }
    @Test
    public void createCustomerTest() throws Exception{
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        fieldMap.put("name","customer100");
        fieldMap.put("contact","John");
        fieldMap.put("telephone","13424564325");
        boolean result=customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }
    @Test
    public void updateCustomerTest() throws Exception
    {
        long id=1;
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        fieldMap.put("contact","sssss");
        boolean result=customerService.updateCustomer(id,fieldMap);
        Assert.assertTrue(result);
    }
    @Test
    public void deleteCustomerTest() throws Exception
    {
        long id=1;
        boolean result=customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
