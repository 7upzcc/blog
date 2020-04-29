package z.coffee.blog.comm.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * AuthUser类。该类用来接受登录页面的登录请求
 */
@Getter
@Setter
public class AuthUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

    @Override
    public String toString() {
        return "{username=" + username  + ", password= ******}";
    }
}
