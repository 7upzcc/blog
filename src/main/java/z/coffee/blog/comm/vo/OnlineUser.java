package z.coffee.blog.comm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 记录在线用户的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

    private String userName;

    private String nickName;

    private String job;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;


}
