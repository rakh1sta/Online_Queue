package uz.sh.online_queue.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTUtils {
    public static Integer expiry = 100 * 500_000;
    public static String secret = "UYTREDCV#@!$#@%$#MEWSCBR%$#%34543dxvfgbv35%^%$";

    public static Date getExpiry() {
        return new Date(System.currentTimeMillis() + expiry);
    }

    public static Date getExpiryForRefreshToken() {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }
}
