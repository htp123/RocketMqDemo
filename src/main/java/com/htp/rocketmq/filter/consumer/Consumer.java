package com.htp.rocketmq.filter.consumer;



        import java.io.File;
        import java.io.IOException;
        import java.io.InputStream;
        import java.util.List;
        import java.util.Properties;
//        import org.springframework.stereotype.Component;
        import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
        import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
        import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
        import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
        import com.alibaba.rocketmq.client.exception.MQClientException;
        import com.alibaba.rocketmq.common.MixAll;
        import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
        import com.alibaba.rocketmq.common.message.MessageExt;

public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("FilterConsumer1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        String className = "com.htp.rocketmq.filter.filter.MessageFilterImpl";
        String filterCode = MixAll.file2String("F:/project/busiworkspace/RocketMqDemo/src/main/java/com/htp/rocketmq/filter/filter/MessageFilterImpl.java");
/*System.out.println("filterCode:"+filterCode);*/

        /*consumer.subscribe("SequenceTopicTest", className, filterCode);*/
        consumer.subscribe("SequenceTopicTest","TagA");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.print(Thread.currentThread().getName() + " Receive New Messages: " );
                for (MessageExt msg: msgs) {
                    System.out.println("content:" + new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
