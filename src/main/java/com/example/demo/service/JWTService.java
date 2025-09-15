package com.example.demo.service;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService 
{
    private String secretkey = "";
    public JWTService()
    {
    	try
    	{
    	KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
    	SecretKey skey = keygen.generateKey();
    	secretkey = Base64.getEncoder().encodeToString(skey.getEncoded());
    	}
    	catch(NoSuchAlgorithmException e)
    	{
    		throw new RuntimeException(e);
    	}
    }
   
	public String generatetoken(String username)
	{
		Map<String,Object> token_info = new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(token_info)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30))
				.and()
				.signWith(getkey())
				.compact();
	}
	
	private SecretKey getkey()
	{
		byte[] keybytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keybytes);
	}

	public String extractusername(String token) 
	{
        return Jwts.parser()
        		 .verifyWith(getkey())
        		 .build()
        		 .parseSignedClaims(token)
        		 .getPayload()
        		 .getSubject();
    }

    public boolean validateToken(String token, String username) 
    {
        return username.equals(extractusername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) 
    {
    	 Date expiration = Jwts.parser()
    	            .verifyWith(getkey())
    	            .build()
    	            .parseSignedClaims(token)
    	            .getPayload()
    	            .getExpiration();
    	    return expiration.before(new Date());
    	}
    }
