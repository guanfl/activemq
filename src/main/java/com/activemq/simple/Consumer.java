/**
* ClassName : Consumer.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.simple;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.util.ConnectionUtil;

/**
 *  消息消费者 JMS Message Consumer 
 */
public class Consumer {
    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final String QUEUE_NAME="FirstQueue";
    
    public static void main(String args[]){
        Connection connection = null;
        
        try {
            connection = ConnectionUtil.getConnection();    //获取连接
            connection.start();             //启动连接
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建session 不添加事务，自动提交
            Destination destination = session.createQueue(QUEUE_NAME);            //创建消息队列，消费者和生产者的消息队列名称需要一致
            MessageConsumer messageConsumer = session.createConsumer(destination);    // 创建消息消费者
            //轮训接收消息
            while(true){
                TextMessage message = (TextMessage)messageConsumer.receive(100L);
                if(message != null){
                    logger.debug("received message==========>" + message.getText());
                }else
                    break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
}
