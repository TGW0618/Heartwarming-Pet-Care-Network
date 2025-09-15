package com.tgwei.demopet.demos.web.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 创建支持多种日期格式的反序列化器
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String text = parser.getText().trim();
                if (text == null || text.isEmpty() || "null".equals(text)) {
                    return null;
                }

                // 定义支持的日期格式
                DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                    DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                    DateTimeFormatter.ofPattern("yyyy-M-d")
                };

                // 尝试每种格式
                for (DateTimeFormatter formatter : formatters) {
                    try {
                        // 对于只包含日期的部分，添加默认时间
                        if (!text.contains(":")) {
                            text = text + " 00:00:00";
                        }
                        return LocalDateTime.parse(text, formatter);
                    } catch (DateTimeParseException e) {
                        // 继续尝试下一个格式
                        continue;
                    }
                }

                // 如果所有格式都失败，使用默认处理方式
                return super.deserialize(parser, context);
            }
        };

        // 设置序列化和反序列化格式
        DateTimeFormatter serializerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(serializerFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, deserializer);

        mapper.registerModule(javaTimeModule);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return mapper;
    }
}
