/**
* ClassName : Consumer.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.util.ConnectionUtil;

/**
 *  消息消费者 JMS Message Consumer 
 */
public class ConsumerWtihListener {
    private static Logger logger = LoggerFactory.getLogger(ConsumerWtihListener.class);
    private static final String QUEUE_NAME="FirstQueue";
    
    public static void main(String args[]){
        Connection connection = null;
        
        try {
            connection = ConnectionUtil.getConnection();                                                        //获取连接
            connection.start();                                                                                 //启动连接
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);                        //创建session 不添加事务，自动提交
            Destination destination = session.createQueue(QUEUE_NAME);                                          //创建消息队列，消费者和生产者的消息队列名称需要一致
            MessageConsumer messageConsumer = session.createConsumer(destination);                              // 创建消息消费者
            
            //监听获取消息
            messageConsumer.setMessageListener(new Listener());                                                 //注册消息监听
        } catch (JMSException e) {
            e.printStackTrace();
        } /*finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }*/
    }
}
