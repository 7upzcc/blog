package z.coffee.blog.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import z.coffee.blog.comm.annotation.AnonymousAccess;
import z.coffee.blog.utils.RedisUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    public RedisUtils redisUtils;

    public AuthController(RedisUtils redisUtils){
        this.redisUtils =  redisUtils ;
    }

    @RequestMapping("/getCode")
    @AnonymousAccess
    public String test(){
        String uuid = "999" ;
        String code = "999999" ;
        Map<String,String> codeData = new HashMap() ;
        codeData.put("uuid",uuid) ;
        codeData.put("code",code) ;
        redisUtils.set(uuid,code);
        return JSONUtil.toJsonStr(codeData) ;
    }
}
