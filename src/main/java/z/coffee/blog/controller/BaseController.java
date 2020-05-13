package z.coffee.blog.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import z.coffee.blog.comm.annotation.AnonymousAccess;
import z.coffee.blog.comm.auth.SecurityProperties;
import z.coffee.blog.comm.auth.TokenProvider;
import z.coffee.blog.comm.service.OnlineUserService;
import z.coffee.blog.comm.vo.AuthUser;
import z.coffee.blog.comm.vo.JwtUser;
import z.coffee.blog.exception.BadRequestException;
import z.coffee.blog.utils.RedisUtils;
import z.coffee.blog.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BaseController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login:false}")
    private Boolean singleLogin;

    public RedisUtils redisUtils;
    public AuthenticationManagerBuilder authenticationManagerBuilder;
    public TokenProvider tokenProvider ;
    public OnlineUserService onlineUserService ;
    public SecurityProperties properties ;

    public BaseController(RedisUtils redisUtils ,
                          AuthenticationManagerBuilder authenticationManagerBuilder ,
                          TokenProvider tokenProvider ,
                          OnlineUserService onlineUserService ,
                          SecurityProperties properties){
        this.redisUtils = redisUtils ;
        this.authenticationManagerBuilder = authenticationManagerBuilder ;
        this.tokenProvider = tokenProvider ;
        this.onlineUserService = onlineUserService ;
        this.properties = properties ;
    }

    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request){
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        byte[] result = rsa.encrypt("admin",KeyType.PrivateKey) ;
        //测试阶段暂时去掉加密设定
        //String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
        String password = authUser.getPassword() ;
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token 与 用户信息
        Map<String,Object> authInfo = new HashMap<String,Object>(2){{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUser);
        }};
        if(singleLogin){
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(),token);
        }
        return ResponseEntity.ok(authInfo);
    }

    @RequestMapping("/test")
    @AnonymousAccess
    public void test(){
        System.out.println("init ");
        redisUtils.set("111","111111");
        String result =  (String)redisUtils.get("111") ;
        System.out.println(result);
    }



}
