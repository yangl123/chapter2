package com.yang.chapter2.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
/**
 * @author yangle
 */
@WebServlet("/customer_create")
public class CutomerCreateServlet extends HttpServlet {
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

    }
}
