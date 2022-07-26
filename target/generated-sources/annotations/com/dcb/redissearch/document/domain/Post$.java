package com.dcb.redissearch.document.domain;

import com.redis.om.spring.metamodel.indexed.NumericField;
import com.redis.om.spring.metamodel.indexed.TagField;
import com.redis.om.spring.metamodel.indexed.TextField;
import com.redis.om.spring.metamodel.nonindexed.NonIndexedTextField;
import java.lang.Integer;
import java.lang.NoSuchFieldException;
import java.lang.SecurityException;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.Set;

public final class Post$ {
  public static Field mostViewed;

  public static Field content;

  public static Field tags;

  public static Field id;

  public static Field title;

  public static NumericField<Post, Integer> MOST_VIEWED;

  public static TextField<Post, String> CONTENT;

  public static TagField<Post, Set<String>> TAGS;

  public static NonIndexedTextField<Post, String> ID;

  public static NonIndexedTextField<Post, String> TITLE;

  static {
    try {
      mostViewed = Post.class.getDeclaredField("mostViewed");
      content = Post.class.getDeclaredField("content");
      tags = Post.class.getDeclaredField("tags");
      id = Post.class.getDeclaredField("id");
      title = Post.class.getDeclaredField("title");
      MOST_VIEWED = new NumericField<Post, Integer>(mostViewed,true);
      CONTENT = new TextField<Post, String>(content,true);
      TAGS = new TagField<Post, Set<String>>(tags,true);
      ID = new NonIndexedTextField<Post, String>(id,false);
      TITLE = new NonIndexedTextField<Post, String>(title,false);
    } catch(NoSuchFieldException | SecurityException e) {
      System.err.println(e.getMessage());
    }
  }
}
