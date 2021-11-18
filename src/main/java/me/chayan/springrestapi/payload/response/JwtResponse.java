package me.chayan.springrestapi.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chayan.springrestapi.models.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private boolean status;
	private User user;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_at")
	private long expiresAt;
}
