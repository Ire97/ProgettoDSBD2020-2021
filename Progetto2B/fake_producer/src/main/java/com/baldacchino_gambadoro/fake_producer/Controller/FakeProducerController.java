package com.baldacchino_gambadoro.fake_producer.Controller;

import com.baldacchino_gambadoro.fake_producer.DataModel.OrderPaid;
import com.baldacchino_gambadoro.fake_producer.DataModel.OrderValidation;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/producer")
public class FakeProducerController {

    @Value("orderupdates")
    private String topicNameOrder;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(path="/prova_1")
    public @ResponseBody
    String prova_1(@RequestParam("id") ObjectId orderId, @RequestParam Integer status){
        OrderValidation orderValidation = new OrderValidation(LocalDateTime.now(), status, orderId);
        kafkaTemplate.send(topicNameOrder, "order_validation", new Gson().toJson(orderValidation));
        return "Inviato con successo";
    }
    //Da togliere
    @PostMapping(path="/prova_2")
    public @ResponseBody
    String prova_2(@RequestParam("id") ObjectId orderId, @RequestParam String userId, @RequestParam double amount){
        OrderPaid orderPaid = new OrderPaid(orderId, userId, amount);
        kafkaTemplate.send(topicNameOrder, "order_paid", new Gson().toJson(orderPaid));
        return "Inviato con successo";
    }
}
