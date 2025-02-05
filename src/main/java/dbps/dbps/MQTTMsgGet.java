package dbps.dbps;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@JsonPropertyOrder({"MSG_TYPE", "MSG_VER", "MSG_ID", "MOID"})
public class MQTTMsgGet {
    @JsonProperty("MSG_TYPE")
    private String MSG_TYPE;

    @JsonProperty("MSG_VER")
    private String MSG_VER;

    @JsonProperty("MSG_ID")
    private String MSG_ID;

    @JsonProperty("MOID")
    private List<String> MOID;
}
