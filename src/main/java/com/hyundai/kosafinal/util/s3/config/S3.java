package com.hyundai.kosafinal.util.s3.config;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3 {

  private String imageBucket;
  private String videoBucket;
  private String domain;

  public String getBucket(Bucket bucket) {
    return this.imageBucket;
  }

  public enum Bucket {
    IMAGE, VIDEO;

    public static Bucket of(String bucket) {
      return Bucket.valueOf(bucket.toUpperCase());
    }
  }

}

