package edu.eci.arep.collection;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class LogServiceImpl {

    private static MongoCollection<Document> customers;
    private static FindIterable<Document> iterable;

    public LogServiceImpl() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:admin@cluster0.urkiqdr.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        //Obtener objeto base de datos. Si no existe lo crea
        MongoDatabase database = mongoClient.getDatabase("ARSWDB");
        //Obtener objeto colección. Si no existe lo crea
        customers = database.getCollection("tweet");

        //Obtiene un iterable
        iterable = customers.find();

    }

    public String getCurrent() {
        String allUsers = "";
        FindIterable<Document> currentUsers = customers.find();
        ArrayList<Object> temp = new ArrayList<>();
        for (Document document : currentUsers) {
            allUsers += "<tr><td>"
                    + document.get("tweet").toString()
                    + "<p>" + document.get("fecha").toString()
                    + "<p>"
                    + "</tr></td>";
        }
        return allUsers;
    }

    public void insertDocument(String tweet) {
        Document document = new Document("_id", new ObjectId());
        document.append("tweet", tweet);
        document.append("fecha", new Date().toString());
        customers.insertOne(document);
    }
}
