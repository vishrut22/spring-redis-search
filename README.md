# spring-redis-search

Installing Redis Stack : docker pull redis/redis-stack

https://redis.com/blog/introducing-redis-om-spring/

redis-cli MONITOR

https://redis.io/docs/stack/get-started/tutorials/stack-spring/

## FT.SEARCH
### FT._LIST
### FT.CREATE "PostIdx" "ON" "JSON" "PREFIX" "1" "test" "SCHEMA" "$.content" "AS" "content" "TEXT" "SORTABLE" "$.tags[*]" "AS" "tags" "TAG" "SEPARATOR" "," "$.mostViewed" "AS" "mostViewed" "NUMERIC" "$.dateCreated" "AS" "dateCreated" "NUMERIC"

## Index name
## Scehma
## Sortable
## tags
## JSON.SET myDoc $ '{"title": "foo", "content": "bar"}'

## https://redis.com/blog/getting-started-with-redisearch-2-0/?_ga=2.175240352.321931497.1658454596-366174195.1658283338
## INDEX CREATION ON REDISHASH
## FT.CREATE idx:movie ON hash PREFIX 1 "movie:" SCHEMA title TEXT SORTABLE release_year NUMERIC SORTABLE rating NUMERIC SORTABLE genre TAG SORTABLE

## "FT.SEARCH" "com.dcb.redissearch.document.domain.PostIdx" "(( @content:redi) @tags:{json})"

## https://redis.io/docs/stack/search/

### https://github.com/redis/redis-om-spring

### https://developer.redis.com/howtos/moviesdatabase/create/
### https://redis.io/docs/stack/get-started/tutorials/stack-spring/

### FT.AGGREGATE "post-idx" "*" "GROUPBY" "1" "@tags" "REDUCE" "COUNT" "0" "AS" "NO_OF_POST" "sortby" "2" "@NO_OF_POST" "DESC"
### FT.CREATE" "com.dcb.redissearch.document.domain.PostIdx" "ON" "JSON" "PREFIX" "1" "test" "SCHEMA" "$.title" "AS" "title" "TEXT" "SORTABLE" "$.content" "AS" "content" "TEXT" "$.tags[*]" "AS" "tags" "TAG" "SEPARATOR" "," "$.mostViewed" "AS" "mostViewed" "NUMERIC"

### FT.AGGREGATE "post-idx" "*" "GROUPBY" "1" "$tags" "REDUCE" "COUNT" "0" "AS" "NO_OF_POST"
####  "FT.AGGREGATE" "post-idx" "*" "GROUPBY" "1" "$tags" "REDUCE" "COUNT" "0" "AS" "NO_OF_POST" "REDUCE" "AVG" "1" "@views" "AS" "AVERAGE_VIEWS"
### https://redis.io/commands/ft.sugadd/
### https://redis.io/commands/ft.sugget/
### https://redis.io/commands/ft.aggregate/



# Redis search using Spring Boot and Jedis

## Installing Redisearch and RedisJson

docker pull redis/redis-stack

## Commands :
### FT.create -
This command is used to create index

Example of Index creation on hash : FT.CREATE idx:post ON hash PREFIX 1 "post:" SCHEMA content TEXT SORTABLE views NUMERIC SORTABLE tags TAG

Example of Index creation on JSON : FT.CREATE "post-idx" "ON" "JSON" "PREFIX" "1" "post:" "SCHEMA" "$.content" "AS" "content" "TEXT" "$.title" "AS" "title" "TEXT" "$.tags[*]" "AS" "tags" "TAG" "$.views" "AS" "views" "NUMERIC" "NOINDEX"

Note : Whenever we are dealing with RedisJson the attributes in index needs to be accessed with $.

Refer : https://redis.io/commands/ft.create/


### FT.search -
This command has capablity to search through index.It provides several capablity like Fuzzy search , text search , tag search.
Search can happen either on Set or Json.

Example of search command : "FT.SEARCH" "post-idx" "@content:tha @tags:{a}" "LIMIT" "0" "20"
In this example we are passing filter of content and 


