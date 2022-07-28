package com.dcb.redissearch;

import com.dcb.redissearch.document.domain.Post;
import com.dcb.redissearch.document.repository.PostRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.FieldName;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootApplication
//Important we used to provide repo only here we provided the model as well.
// It has helped to create capablity of creating indexes based on annotations.
public class RedisSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSearchApplication.class, args);
	}
	@Autowired
	PostRepository postRepository;

	@Value("classpath:data_dump.json")
	Resource resourceFile;

	@Autowired
	UnifiedJedis unifiedJedis;

	@Bean
	CommandLineRunner loadTestData() {
		return args -> {
			postRepository.deleteAll();
			unifiedJedis.ftDropIndex("post-idx");

			String data = new String(resourceFile.getInputStream().readAllBytes());
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Post[] posts = objectMapper.readValue(data,  Post[].class);
			Arrays.stream(posts).forEach(postRepository::save);

			Schema schema = new Schema()
					.addField(new Schema.Field(FieldName.of("$.content").as("content") , Schema.FieldType.TEXT , true, false))
					.addField(new Schema.TextField(FieldName.of("$.title").as("title")))
					.addField(new Schema.Field(FieldName.of("$.tags[*]").as("tags"), Schema.FieldType.TAG))
						.addField(new Schema.Field(FieldName.of("$.views").as("views"), Schema.FieldType.NUMERIC, false , true));


			IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON)
					.setPrefixes(new String[]{"post:"});

			unifiedJedis.ftCreate("post-idx", IndexOptions.defaultOptions().setDefinition(rule), schema);


		};
	}

}
