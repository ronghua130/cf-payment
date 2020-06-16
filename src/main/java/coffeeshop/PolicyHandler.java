package coffeeshop;

import coffeeshop.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelPay(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){
            System.out.println("##### listener CancelPay : " + orderCanceled.toJson());

            // cancel건 삭제
            Payment payment = new Payment();
            int result = paymentRepository.deleteByOrderId(orderCanceled.getOrderId());
            System.out.println("cancelPay result : "  + result);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void onEvent(@Payload String message) { }
}
