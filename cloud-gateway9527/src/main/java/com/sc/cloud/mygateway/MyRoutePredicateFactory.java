package com.sc.cloud.mygateway;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 需求分析：自定义配置会员等级UserType，按照钻/金/银和yml配置的会员等级，以适配是否可以访问
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }
    public Predicate<ServerWebExchange> apply(final MyRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {

                //http://localhost:9527/pay/gateway/get/1?userType=diamond
                //检查request的参数里面，userType是否为指定的值，符合配置就通过
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
                if (userType==null) {
                    return false;
                }
                //如果说参数存在，就和config的数据进行比较
                if (userType.equals(config.getUserType())) {
                    return true;
                }
                return false;
            }
        };
    }

    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }
    //路由自定义规则，重要
    @Validated
    public static class Config {

        @Setter@Getter@NotEmpty
        private  String userType;//钻/金/银用户等级

    }

}
