/**
* ClassName : Provider.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.simple;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.util.ConnectionUtil;

/**
 * ActiveMQ JMSProvider
 * 消息生产者
 */
public class Provider {
    private static Logger logger = LoggerFactory.getLogger(Provider.class);
    
    //private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;         //默认连接用户名
    //private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;     //默认连接密码
    //private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;  //默认连接url
    private static final String  QUEUE_NAME = "FirstQueue";
    private static final int SENDNUM = 10;
    
    public static void main(String args[]){
        //ConnectionFactory connectionFactory = null  ;//连接工厂
        Connection connection = null ;         //连接
        Session session = null;               //会话 接收或发送消息的线程
        Destination destination = null ;       //消息的目的地
        MessageProducer messageProducer = null;   //消息生产者
        
        try {
            //connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
            //connection = connectionFactory.createConnection();          //通过连接工厂获取连接
            connection = ConnectionUtil.getConnection();                  //获取连接
            connection.start();                                         //启动连接
            //创建session
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE); //arg1 是否开启事务，消息确认模式
            destination = session.createQueue(QUEUE_NAME);            //创建消息队列
            messageProducer = session.createProducer(destination);          //创建消息生产者
            //对messageProducer进行一些设置
            //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);   //设置不持久化模式
            sendMessage(session, messageProducer);                          //发送消息
            session.commit();
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * message producer通过session来发送消息
     * @param session 通道
     * @param messageProducer 发送者
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception{
        for(int i = 0; i < Provider.SENDNUM; i++){
            TextMessage message = session.createTextMessage("ActiveMq 发送消息" + i);
            logger.debug(message.getText());
            messageProducer.send(message);
        }
    }
    
}
