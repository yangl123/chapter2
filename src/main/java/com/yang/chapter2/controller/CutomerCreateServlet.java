package com.yang.chapter2.controller;

import com.yang.chapter2.model.Customer;
import com.yang.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

/**
 * @author yangle
 */
@WebServlet("/customer")
public class CutomerCreateServlet extends HttpServlet {
    private CustomerService customerService;
    @Override
    public void init() throws ServletException {
        customerService=new CustomerService();
    }

    /**
     * 进入创建客户界面
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    /**
     * 处理创建客户请求
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        List<Customer>  customerList=customerService.getCutomerList(null);
        request.setAttribute("customerList",customerList);
        request.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(request,response);
    }
}
