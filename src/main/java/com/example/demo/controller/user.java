package com.example.demo.controller;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.*;
import com.example.demo.repository.userrepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional; 
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.example.demo.service.JWTService;
import java.util.List;

@RestController
public class user {
	@Autowired
	userrepository repo;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Autowired
	public BCryptPasswordEncoder encoder;
	
	@Autowired
	JWTService jwtservice;
	
	@PostMapping("/adduser")
	public ResponseEntity<String> createuser(@RequestBody task user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Created");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody task logindetails)
	{
		try
		{
			Authentication auth = authmanager.authenticate(new UsernamePasswordAuthenticationToken(logindetails.getUsername(),
															logindetails.getPassword()));
		
		String token = jwtservice.generatetoken(logindetails.getUsername());
		return ResponseEntity.ok(token);
		}
		catch(AuthenticationException e)
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}
	
	
	
	@GetMapping("/users")
	public ResponseEntity<List<task>> getAllUsers() 
	{
	    List<task> users = repo.findAll();
	    return ResponseEntity.ok(users);
	}

	@PutMapping("/edituser/{id}")
	public task updateuser(@PathVariable int id,@RequestBody task updateduser)
	{
		Optional<task> updateuser= repo.findById(id);
		if(updateuser.isPresent())
		{
			task existinguser = updateuser.get();
			existinguser.setPassword(updateduser.getPassword());
			existinguser.setUsername(updateduser.getUsername());
			return repo.save(existinguser);
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}
	
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<String> delete_user(@PathVariable int id) 
	{
		if (repo.existsById(id))
		{
			repo.deleteById(id);
			return ResponseEntity.ok("User Deleted");
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
		}
	}
}
