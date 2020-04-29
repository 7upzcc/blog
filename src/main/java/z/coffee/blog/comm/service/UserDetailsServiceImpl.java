package z.coffee.blog.comm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import z.coffee.blog.comm.vo.JwtUser;
import z.coffee.blog.model.User;
import z.coffee.blog.repository.RoleRepository;
import z.coffee.blog.repository.UserRepository;
import z.coffee.blog.service.RoleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RoleService roleService ;

    /**
     * 使用用户名查找用户的信息
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username) ;
        if(user != null){
            List roles = new ArrayList() ;
            roles = roleService.getUserAuthorityList(user.getId()) ;
            return new JwtUser(
                    10L,
                    user.getUsername(),
                    user.getUsername(),
                    "1",
                    "$2a$10$n0KfuF4JQxPknRdROtXDE.FTesJjsKTTeZjWM2GDXoqKiBJ/Na5aW",
                    "",
                    "7upzcc@163.com",
                    "13898895659",
                    "null",
                    "null",
                    roles,
                    true,
                    null,
                    null
            );
        }
        else{
            return null ;
        }

    }

}
