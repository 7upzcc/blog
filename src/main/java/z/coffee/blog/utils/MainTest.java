package z.coffee.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainTest {
    public static void main(String[] args){
        String pass = new BCryptPasswordEncoder().encode("admin") ;
        System.out.println("=========");
        System.out.println(pass);
        System.out.println("=========");

    }
}
