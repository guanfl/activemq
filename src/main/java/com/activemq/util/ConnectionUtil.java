/**
* ClassName : ConnectionUtil.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ConnectionUtil {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;         //默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;     //默认连接密码
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;  //默认连接url
    
    
    public static Connection getConnection() throws JMSException {
        ConnectionFactory connectionFactory;
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        return connectionFactory.createConnection();
    }
}
