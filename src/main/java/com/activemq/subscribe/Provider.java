/**
* ClassName : Provider.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.subscribe;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.util.ConnectionUtil;

/**
 * ActiveMQ JMSProvider publisher
 * 发布订阅模式：
 * 一个发布者多个订阅者
 */
public class Provider {
    private static Logger logger = LoggerFactory.getLogger(Provider.class);
    
    private static final String TOPIC_NAME = "FirstTopic";
    private static final int SENDNUM = 10;
    
    public static void main(String args[]){
        Connection connection = null ;         //连接
        Session session = null;               //会话 接收或发送消息的线程
        Destination destination = null ;       //消息的目的地
        MessageProducer messageProducer = null;   //消息生产者
        
        try {
            connection = ConnectionUtil.getConnection();                    //获取连接
            connection.start();                                             //启动连接
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE); //arg1 是否开启事务，消息确认模式
            //destination = session.createQueue(QUEUE_NAME);                //创建消息队列
            
            destination = session.createTopic(TOPIC_NAME);                //发布订阅模式中创建的时候topic
            messageProducer = session.createProducer(destination);          //创建消息生产者
            sendMessage(session, messageProducer);                          //发送消息
            session.commit();
        } catch (Exception e) {
            
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
