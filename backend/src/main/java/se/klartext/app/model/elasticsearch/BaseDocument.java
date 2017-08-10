package se.klartext.app.model.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.app.lib.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public abstract class BaseDocument{

    private int id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public BaseDocument(int id, LocalDateTime createdAt){
        this.id = id;
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public Map<String,Object> getSource(){
        return new ObjectMapper().convertValue(this,Map.class);
    }

    @JsonIgnore
    public abstract String getDocumentType();
}
