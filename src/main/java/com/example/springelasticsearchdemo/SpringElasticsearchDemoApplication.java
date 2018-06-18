package com.example.springelasticsearchdemo;

import com.example.springelasticsearchdemo.domain.Product;
import com.example.springelasticsearchdemo.domain.Rating;
import com.example.springelasticsearchdemo.repository.ProductRepository;
import com.example.springelasticsearchdemo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class SpringElasticsearchDemoApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingRepository ratingRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringElasticsearchDemoApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        this.indexProducts();
        this.indexRating();
    }

    public void indexProducts() {
        Path path = Paths.get("C:\\Work\\Workspaces\\etc\\elastic-graph\\data\\sample_products.txt");
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
                        return new Rating()
                                .setTimestamp(Long.valueOf(split[0]))
                                .setUser(split[1])
                                .setCustomer(split[2])
                                .setCampaign(split[3])
                                .setEventType(split[4])
                                .setSku(split[5]);
                    })
                    .peek(rating -> ratingRepository.save(rating))
                    .count();
            System.out.println("Quantidade de produtos: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
