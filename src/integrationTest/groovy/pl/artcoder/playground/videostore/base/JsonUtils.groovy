package pl.artcoder.playground.videostore.base

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

trait JsonUtils {
    private final ObjectMapper objectMapper = new ObjectMapper()

    String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e)
        }
    }

    String asPageJsonString(final List<Object> objects) {
        String strContent = asJsonString(objects)
        """
                {
                    "content": $strContent
                }
        """
    }
}
