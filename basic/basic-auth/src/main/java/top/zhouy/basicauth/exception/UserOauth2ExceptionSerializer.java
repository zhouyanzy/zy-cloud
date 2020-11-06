package top.zhouy.basicauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @description: 自定义异常
 * @author: zhouy
 * @create: 2020-11-05 11:59:00
 */
public class UserOauth2ExceptionSerializer extends StdSerializer<UserOauth2Exception> {

    protected UserOauth2ExceptionSerializer() {
        super(UserOauth2Exception.class);
    }
    @Override
    public void serialize(UserOauth2Exception e, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("status", e.getHttpErrorCode());
        String message = e.getMessage();
        if (message != null) {
            message = HtmlUtils.htmlEscape(message);
        }
        generator.writeStringField("message", message);
        if (e.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                generator.writeStringField(key, add);
            }
        }
        generator.writeEndObject();
    }
}
