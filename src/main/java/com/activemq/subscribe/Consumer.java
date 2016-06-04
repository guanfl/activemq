/**
* ClassName : Consumer.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.subscribe;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.listener.Listener;
import com.activemq.util.ConnectionUtil;

/**
 *  消息消费者 JMS Message Consumer 
 *  subscribe订阅者
 */
public class Consumer {
    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final String TOPIC_NAME = "FirstTopic";
    
    public static void main(String args[]){
        Connection connection = null;
        
        try {
            connection = ConnectionUtil.getConnection();    //获取连接
            connection.start();             //启动连接
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);    //创建session 不添加事务，自动提交
            //Destination destination = session.createQueue(QUEUE_NAME);                            //创建消息队列，消费者和生产者的消息队列名称需要一致
            
            Destination destination = session.createTopic(TOPIC_NAME);                              //发布订阅模式 创建topic
            MessageConsumer messageConsumer = session.createConsumer(destination);                  // 创建消息消费者
            
            //接收消息
            messageConsumer.setMessageListener(new Listener(Consumer.class));
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            
        }
    }
}
