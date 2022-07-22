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
import java.util.Date;
import java.util.Set;

public final class Post$ {
  public static Field id;

  public static Field url;

  public static Field content;

  public static Field tags;

  public static Field mostViewed;

  public static Field dateCreated;

  public static NonIndexedTextField<Post, String> ID;

  public static NonIndexedTextField<Post, String> URL;

  public static TextField<Post, String> CONTENT;

  public static TagField<Post, Set<String>> TAGS;

  public static NumericField<Post, Integer> MOST_VIEWED;

  public static NumericField<Post, Date> DATE_CREATED;

  static {
    try {
      id = Post.class.getDeclaredField("id");
      url = Post.class.getDeclaredField("url");
      content = Post.class.getDeclaredField("content");
      tags = Post.class.getDeclaredField("tags");
      mostViewed = Post.class.getDeclaredField("mostViewed");
      dateCreated = Post.class.getDeclaredField("dateCreated");
      ID = new NonIndexedTextField<Post, String>(id,false);
      URL = new NonIndexedTextField<Post, String>(url,false);
      CONTENT = new TextField<Post, String>(content,true);
      TAGS = new TagField<Post, Set<String>>(tags,true);
      MOST_VIEWED = new NumericField<Post, Integer>(mostViewed,true);
      DATE_CREATED = new NumericField<Post, Date>(dateCreated,true);
    } catch(NoSuchFieldException | SecurityException e) {
      System.err.println(e.getMessage());
    }
  }
}
