package com.example.springelasticsearchdemo;

import com.example.springelasticsearchdemo.domain.Product;
import com.example.springelasticsearchdemo.domain.UserPurchases;
import com.example.springelasticsearchdemo.repository.ProductRepository;
import com.example.springelasticsearchdemo.repository.RatingRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class SpringElasticsearchDemoApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    public static void main(String[] args) {
        SpringApplication.run(SpringElasticsearchDemoApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        this.indexRating();
//        this.indexProducts();
    }

    public void indexProducts() {
        Path path = Paths.get("C:\\Work\\Workspaces\\etc\\elastic-graph\\data\\dafiti_skus_full.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            long count = reader.lines()
                    .parallel()
                    .map(line -> {
                        String[] split = line.split(";");
                        return new Product()
                                .setSku(split[0])
                                .setGender(split[1])
                                .setCategory(split[2])
                                .setPrice(Double.parseDouble(split[3]));
                    })
                    .peek(product -> productRepository.save(product))
                    .count();
            System.out.println("Quantidade de produtos: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexRating() {
        Path path = Paths.get("C:\\Work\\Workspaces\\etc\\elastic-graph\\data\\sample_rating.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            long count = reader.lines()
                    .parallel()
                    .map(line -> {
                        String[] split = line.split("\\|");
                        UserPurchases user = new UserPurchases()
                                .setUser(split[1]);
                        ratingRepository.findById(user.getUser()).orElse(ratingRepository.save(user));
                        return user.addSku(split[5]);
                    })
                    .peek(rating -> {
                        UpdateQuery updateQuery = new UpdateQueryBuilder()
                                .withId(rating.getUser())
                                .withClass(UserPurchases.class)
                                .withUpdateRequest(new UpdateRequest()
                                        .index("user_purchases")
                                        .id(rating.getUser())
                                        .scriptedUpsert(true)
                                        .script(new Script(ScriptType.INLINE,
                                                "painless",
                                                "ctx._source.skus += params.sku",
                                                Collections.singletonMap("sku", rating.getSkus()))))
                                .build();
                        elasticsearchTemplate.update(updateQuery);
                    })
                    .count();
            System.out.println("Quantidade de produtos: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
