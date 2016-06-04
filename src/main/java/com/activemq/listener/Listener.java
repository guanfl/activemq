/**
* ClassName : Listener.java
* Create on ：2016年6月4日
* Copyrights 2016 guanfl All rights reserved.
* Email : guanfl@163.com
*/
package com.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener implements MessageListener {
    private Class clazz;
    private static Logger logger = LoggerFactory.getLogger(Listener.class);

    public Listener() {
        
    }

    public Listener(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onMessage(Message message) {
        try {
            logger.debug(this.clazz.getSimpleName() + "接收到消息===========>"+ ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
