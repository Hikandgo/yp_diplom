package praktikum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

}
