package z.coffee.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import z.coffee.blog.model.Role;
import z.coffee.blog.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository ;

    public List getUserAuthorityList(Long userId){
        List<SimpleGrantedAuthority> authorities = new ArrayList() ;
        List<Role> roleList = roleRepository.findByUserId(userId) ;
        roleList.stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getUserRole())) ;
        });
        return authorities ;
    }
}
