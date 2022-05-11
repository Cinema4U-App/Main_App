package ro.sd.a2.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.UserDto;

@Service
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    public void send(UserDto payload){
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

}
