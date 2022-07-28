package com.dcb.redissearch.document.repository;


import com.dcb.redissearch.model.CategoryStats;
import com.dcb.redissearch.model.Page;
import com.dcb.redissearch.document.domain.Post;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.*;
import redis.clients.jedis.search.aggr.AggregationBuilder;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.search.aggr.Reducers;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
public class PostRepository{

    @Autowired
    private UnifiedJedis client;

    private static final  int PAGE_SIZE = 5;


    public Post save(Post p){
        if(p.getPostId() == null) {
            p.setPostId(UUID.randomUUID().toString());
        }
        Gson gson = new Gson();
        client.jsonSet("post:"+p.getPostId(), gson.toJson(p));
        client.sadd("post", p.getPostId());
        return p;
    }

    public void deleteAll(){
        Set<String> postKeys = client.smembers("post");
        if(!postKeys.isEmpty()) {
            postKeys.stream().map(key -> "post:"+key).forEach(client::jsonDel);
        }
        client.del("post");
    }

    public Page search(String content, Set<String> tag , int pageNumber){
        long totalResult = 0;
        StringBuilder queryBuilder = new StringBuilder();
        if(content != null && !content.isBlank()) {
            queryBuilder.append("@content:"+content);
        }
        if(tag!= null  && !tag.isEmpty()){
            queryBuilder.append(" @tags:{"+tag.stream().collect(Collectors.joining(","))+"}");
        }
        String queryCriteria = queryBuilder.toString();
        Query query = null;

        if(queryCriteria.isBlank()){
            query = new Query();
        } else{
            query = new Query(queryBuilder.toString());
         }
        query.limit(PAGE_SIZE*(pageNumber-1), PAGE_SIZE);
        SearchResult searchResult = client.ftSearch("post-idx",query);
        totalResult = searchResult.getTotalResults();
        int numberOfPage = (int) Math.ceil(((float) totalResult)/PAGE_SIZE);

        List<Document> documentList = searchResult.getDocuments();
        List<Post> postList = documentList.stream()
               .map(this::convertDocumentToPost)
               .collect(Collectors.toList());
        return Page.builder()
                .currentPage(pageNumber)
                .totalPage(numberOfPage)
                .posts(postList)
                .total(totalResult).build();
    }

    public Post convertDocumentToPost(Document document){
        Gson gson = new Gson();
        String jsonDoc = document.getProperties().iterator().next().getValue().toString();
        return gson.fromJson(jsonDoc, Post.class);
    }

    public int getTotalPageCount(){
        return 0;
    }

    public List<CategoryStats> getCategoryWiseTotalPost() {
        AggregationBuilder aggregationBuilder = new AggregationBuilder();
        aggregationBuilder.groupBy("$tags" , Reducers.count().as("NO_OF_POST"),
                Reducers.avg("@views").as("AVERAGE_VIEWS"));
        AggregationResult aggregationResult  = client.ftAggregate("post-idx", aggregationBuilder);
        List<CategoryStats> categoryStatsList = new ArrayList<>();
        LongStream.range(0 , aggregationResult.totalResults)
                .mapToObj(results -> aggregationResult.getRow((int)results))
                .forEach(row -> {
                    categoryStatsList.add(CategoryStats.builder()
                            .totalPosts(row.getLong("NO_OF_POST"))
                            .averageViews(new DecimalFormat("#.##").format(row.getDouble("AVERAGE_VIEWS")))
                            .tag(row.getString("tags"))
                            .build());
                });
        return categoryStatsList;
    }
}
