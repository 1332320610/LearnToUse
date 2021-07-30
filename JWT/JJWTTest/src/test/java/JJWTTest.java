import icu.hututublog.jjwttest.utils.JJWTUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

public class JJWTTest {

    public String createJWT() {
        String jwt = JJWTUtil.createJWT("1", "111", "admin", JJWTUtil.DAY_TTL);
        System.out.println(jwt);
        return jwt;
    }

    @Test
    public void parseJWT() {
        Claims claims = JJWTUtil.parseJWT(createJWT());
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("IssuedAt: " + claims.getIssuedAt());
        System.out.println("Expiration: " + claims.getExpiration());
        System.out.println(claims);
    }
}
