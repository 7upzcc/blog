package z.coffee.blog.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户的角色表
 */
@Table(name = "t_role")
@Setter
@Getter
@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id ;

    @NotNull
    @Column(name = "user_id")
    private Long userId ;

    @NotNull
    @Column(name = "user_role")
    private String userRole ;

    @NotNull
    @Column(name = "is_active" , columnDefinition = "bit(1) default 0")
    private Boolean isActive ;

}
