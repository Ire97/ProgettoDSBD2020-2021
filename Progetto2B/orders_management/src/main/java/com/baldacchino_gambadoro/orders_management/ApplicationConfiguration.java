package com.baldacchino_gambadoro.orders_management;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                MongoReactiveDataAutoConfiguration.class,
                EmbeddedMongoAutoConfiguration.class
        }
)
@AutoConfigureAfter({EmbeddedMongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoAutoConfiguration.class})
public class ApplicationConfiguration extends AbstractMongoClientConfiguration {

    @Value(value = "${MONGO_HOST}")
    private String mongoHost;

    /*@Value(value="${MONGO_USER}")
    private String mongoUser;

    @Value(value="${MONGO_PASS}")
    private String mongoPass;

    @Value(value="${MONGO_AUTH_DB}")
    private String mongoAuthDb;*/

    @Value(value="${MONGO_PORT}")
    private String mongoPort;

    @Value(value="${MONGO_DB_NAME}")
    private String mongoDBName;

    public ApplicationConfiguration() {
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoDBName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        /*String s = String.format("mongodb://%s:%s@%s:%s/%s?authSource=%s",
                mongoUser, mongoPass, mongoHost, mongoPort, mongoDBName, mongoAuthDb);*/
        String s = String.format("mongodb://%s:%s/%s",
                mongoHost, mongoPort, mongoDBName);
        return MongoClients.create(s);
    }
}