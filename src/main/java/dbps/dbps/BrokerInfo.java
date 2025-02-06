package dbps.dbps;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"MAC", "api_url", "broker_ip", "broker_port", "client_id", "user", "otp"})
public class BrokerInfo {
    @JsonProperty("MAC")
    private String MAC;

    @JsonProperty("api_url")
    private String api_url;

    @JsonProperty("broker_ip")
    private String broker_ip;

    @JsonProperty("broker_port")
    private int broker_port;

    @JsonProperty("client_id")
    private String client_id;

    @JsonProperty("user")
    private String user;

    @JsonProperty("otp")
    private String otp;

    public BrokerInfo(String MAC, String api_url, String broker_ip, int broker_port, String client_id, String user, String otp) {
        this.MAC = MAC;
        this.api_url = api_url;
        this.broker_ip = broker_ip;
        this.broker_port = broker_port;
        this.client_id = client_id;
        this.user = user;
        this.otp = otp;
    }
}