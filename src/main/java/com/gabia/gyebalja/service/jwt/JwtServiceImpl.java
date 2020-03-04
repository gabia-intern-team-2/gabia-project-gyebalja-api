package com.gabia.gyebalja.service.jwt;

import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.exception.UnauthorizedException;
import com.gabia.gyebalja.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {
    @Autowired
    UserRepository userRepository;
    private static final String SALT = "scretString";

    //jwt 토큰 생성
    @Override
    public <T> String create(T data) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .claim("user", data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();

        return jwt;
    }

    // 비밀 키 생성
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return key;
    }

    // jwt 토큰 복호
    @Override
    public Map<String, Object> get(String jwt) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException();
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get("user");

        return value;
    }

    // 유효한 토큰인지 검증
    @Override
    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException();
        }
    }

    @Override
    public boolean isRegister(String jwt) {
        Map<String, Object> originData = this.get(jwt);
        Optional<User> findUser = userRepository.findUserByGabiaUserNo(Long.parseLong(originData.get("no").toString()));
        boolean flag = false;

        if(findUser.isPresent())
            flag = true;

        return flag;
    }
}
