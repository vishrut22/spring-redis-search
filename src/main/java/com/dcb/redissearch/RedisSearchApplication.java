package com.dcb.redissearch;

import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.document.repository.PostRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootApplication
//Important we used to provide repo only here we provided the model as well.
// It has helped to create capablity of creating indexes based on annotations.
@EnableRedisDocumentRepositories(basePackages = "com.dcb.redissearch.document.*")
public class RedisSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSearchApplication.class, args);
	}
	@Autowired
	PostRepository postRepository;

	@Bean
	CommandLineRunner loadTestData() {
		return args -> {
			postRepository.deleteAll();

			Post redis = Post.of("Hey this is Redis tutorial with search.", "https://redis.com", 20, new Date());
			redis.setTags(Set.of("redis", "search", "fuzzy"));

			Post redisjson = Post.of("Hey this is json  tutorial with redis capablities.", "https://redis.com", 50, new Date());
			redisjson.setTags(Set.of("redis", "json"));

			Post microsoft = Post.of("This is java tutotrial", "https://microsoft.com", 50, new Date());
			microsoft.setTags(Set.of("java"));

			postRepository.save(redis);
			postRepository.save(redisjson);
			postRepository.save(microsoft);



		};
	}

}
