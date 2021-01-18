package com.baldacchino_gambadoro.orders_management.Kafka;

import com.baldacchino_gambadoro.orders_management.ClassSerializable.OrderPaid;
import com.baldacchino_gambadoro.orders_management.ClassSerializable.OrderValidation;
import com.baldacchino_gambadoro.orders_management.DataModel.TotalOrder;
import com.baldacchino_gambadoro.orders_management.Repository.TotalOrderRepository;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    TotalOrderRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orderupdates", groupId = "group-consumer")
    public void listenOrderValidation(ConsumerRecord<String, String> record) {
        if(record != null){
            if(record.key().equals("order_validation")) {
                System.out.println(record.key());
                System.out.println(record.value());
                OrderValidation orderValidation = new Gson().fromJson(record.value(), OrderValidation.class);
                System.out.println(orderValidation);
                if (orderValidation.getStatus() != 0) {
                    TotalOrder order = repository.findBy_id(orderValidation.getOrderId());
                    order.setStatus("Abort");
                    repository.save(order);
                }
            }else if(record.key().equals("order_paid")){
                System.out.println(record.key());
                System.out.println(record.value());
                OrderPaid orderPaid = new Gson().fromJson(record.value(), OrderPaid.class);
                TotalOrder order = repository.findTotalOrderBy_idAndUserIdAndAmount(orderPaid.getOrderId(),
                        orderPaid.getUserId(), orderPaid.getAmount());
                if(order != null){
                    order.setStatus("Paid");
                    repository.save(order);
                    kafkaTemplate.send("pushnotifications", "order_paid", new Gson().toJson(order));
                    kafkaTemplate.send("invoicing", "order_paid", new Gson().toJson(order));
                }else{
                    TotalOrder order_error = repository.findTotalOrderBy_idAndUserId(orderPaid.getOrderId(),
                            orderPaid.getUserId());
                    if(order_error != null){
                        //esiste un ordine ma c'è l'amount sbagliato.
                        order_error.setStatus("Abort");
                        repository.save(order_error);
                        orderPaid.getExtraArgs().put("error", "WRONG_AMOUNT_PAID");

                    }else{
                        orderPaid.getExtraArgs().put("error", "ORDER_NOT_FOUND");
                    }
                    kafkaTemplate.send("logging", "order_paid_validation_failure", new Gson().toJson(orderPaid));
                }
            }

        }
    }

}
