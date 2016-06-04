/**
* ClassName : Provider.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.listener;

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
 * 消息生产者 监听模式
 * 生产者发送消息到队列中，消费者从队列中获取数据
 * 时间监听模式:当消息队列中有消息了之后，通知消费者去取数据，而不用一直轮训地取数据
 */
public class Provider {
    private static Logger logger = LoggerFactory.getLogger(Provider.class);
    
    private static final String  QUEUE_NAME = "FirstQueue";
    private static final int SENDNUM = 10;
    
    public static void main(String args[]){
        Connection connection = null ;         //连接
        Session session = null;               //会话 接收或发送消息的线程
        Destination destination = null ;       //消息的目的地
        MessageProducer messageProducer = null;   //消息生产者
        
        try {
            connection = ConnectionUtil.getConnection();                  //获取连接
            connection.start();                                         //启动连接
            
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE); //arg1 是否开启事务，消息确认模式//创建session
            destination = session.createQueue(QUEUE_NAME);            //创建消息队列
            messageProducer = session.createProducer(destination);          //创建消息生产者
            //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);   //设置不持久化模式//对messageProducer进行一些设置
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
