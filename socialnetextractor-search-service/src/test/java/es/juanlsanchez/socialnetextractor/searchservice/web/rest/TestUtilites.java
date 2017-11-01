package es.juanlsanchez.socialnetextractor.searchservice.web.rest;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtilites {

  public static final String FIRST_CONTENT_SELECTOR = "$.content[0].";

  public static final String CONTENT_SELECTOR = "$.";

  public static final MediaType CONTENT_TYPE_JSON =
      new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }

}
