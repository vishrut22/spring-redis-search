# spring-redis-search
Installing Redis Stack : docker pull redis/redis-stack
https://redis.com/blog/introducing-redis-om-spring/
redis-cli MONITOR

https://redis.io/docs/stack/get-started/tutorials/stack-spring/

FT.SEARCH
FT._LIST
FT.CREATE "PostIdx" "ON" "JSON" "PREFIX" "1" "test" "SCHEMA" "$.content" "AS" "content" "TEXT" "SORTABLE" "$.tags[*]" "AS" "tags" "TAG" "SEPARATOR" "," "$.mostViewed" "AS" "mostViewed" "NUMERIC" "$.dateCreated" "AS" "dateCreated" "NUMERIC"
Index name
Scehma
Sortable
tags
JSON.SET myDoc $ '{"title": "foo", "content": "bar"}'

https://redis.com/blog/getting-started-with-redisearch-2-0/?_ga=2.175240352.321931497.1658454596-366174195.1658283338
INDEX CREATION ON REDISHASH
FT.CREATE idx:movie ON hash PREFIX 1 "movie:" SCHEMA title TEXT SORTABLE release_year NUMERIC SORTABLE rating NUMERIC SORTABLE genre TAG SORTABLE

"FT.SEARCH" "com.dcb.redissearch.document.domain.PostIdx" "(( @content:redi) @tags:{json})"

https://redis.io/docs/stack/search/
