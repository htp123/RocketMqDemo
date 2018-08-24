package com.htp.rocketmq.filter.filter;

/**
 * Created by Administrator on 2018/8/21.
 */
import com.alibaba.rocketmq.common.filter.FilterContext;
import com.alibaba.rocketmq.common.filter.MessageFilter;
import com.alibaba.rocketmq.common.message.MessageExt;

public class MessageFilterImpl implements MessageFilter{
    @Override
    public boolean match(MessageExt messageExt, FilterContext filterContext) {
        System.out.println("filter.....");
        String property = messageExt.getProperty("SequenceId");
        if(property != null){
            int id = Integer.parseInt(property);
            if(id % 2 == 0){
                System.out.println("id:"+id);
                filterContext.setConsumerGroup("FilterConsumer1");
                filterContext.setConsumerGroup("consumer1");
                return true;
            }
        }
        return false;
    }

}
