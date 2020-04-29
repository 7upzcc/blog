package z.coffee.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 该类作为t_user表的映射java类
 * 实现序列化接口
 */

@Entity
@Getter
@Setter
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id ;

    @NotBlank
    @Column(name = "username")
    private String username ;

    @NotBlank
    @Column(name = "password")
    private String password ;

    @NotNull
    @Column(name = "account_non_expired")
    private boolean accountNonExpired ;

    @NotNull
    @Column(name = "account_non_locked")
    private boolean accountNonLocked ;

    @NotNull
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired ;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled ;
}
