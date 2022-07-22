package com.dcb.redissearch.document;

import com.dcb.redissearch.document.domain.Post;
import com.redis.om.spring.metamodel.indexed.GeoField;
import com.redis.om.spring.metamodel.indexed.NumericField;
import com.redis.om.spring.metamodel.indexed.TagField;
import com.redis.om.spring.metamodel.indexed.TextField;
import com.redis.om.spring.metamodel.nonindexed.NonIndexedBooleanField;
import com.redis.om.spring.metamodel.nonindexed.NonIndexedTextField;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.NoSuchFieldException;
import java.lang.SecurityException;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.Set;
import org.springframework.data.geo.Point;

public final class Post$ {
  public static Field tags;

  public static Field numberOfEmployees;

  public static Field yearFounded;

  public static Field url;

  public static Field publiclyListed;

  public static Field location;

  public static Field id;

  public static Field name;

  public static TagField<Post, Set<String>> TAGS;

  public static NumericField<Post, Integer> NUMBER_OF_EMPLOYEES;

  public static NumericField<Post, Integer> YEAR_FOUNDED;

  public static NonIndexedTextField<Post, String> URL;

  public static NonIndexedBooleanField<Post, Boolean> PUBLICLY_LISTED;

  public static GeoField<Post, Point> LOCATION;

  public static NonIndexedTextField<Post, String> ID;

  public static TextField<Post, String> NAME;

  static {
    try {
      tags = Post.class.getDeclaredField("tags");
      numberOfEmployees = Post.class.getDeclaredField("numberOfEmployees");
      yearFounded = Post.class.getDeclaredField("yearFounded");
      url = Post.class.getDeclaredField("url");
      publiclyListed = Post.class.getDeclaredField("publiclyListed");
      location = Post.class.getDeclaredField("location");
      id = Post.class.getDeclaredField("id");
      name = Post.class.getDeclaredField("name");
      TAGS = new TagField<Post, Set<String>>(tags,true);
      NUMBER_OF_EMPLOYEES = new NumericField<Post, Integer>(numberOfEmployees,true);
      YEAR_FOUNDED = new NumericField<Post, Integer>(yearFounded,true);
      URL = new NonIndexedTextField<Post, String>(url,false);
      PUBLICLY_LISTED = new NonIndexedBooleanField<Post, Boolean>(publiclyListed,false);
      LOCATION = new GeoField<Post, Point>(location,true);
      ID = new NonIndexedTextField<Post, String>(id,false);
      NAME = new TextField<Post, String>(name,true);
    } catch(NoSuchFieldException | SecurityException e) {
      System.err.println(e.getMessage());
    }
  }
}
