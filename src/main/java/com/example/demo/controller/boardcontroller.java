package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.demo.entity.*;
import com.example.demo.repository.boardrepository;
import com.example.demo.repository.userrepository;

import org.springframework.http.HttpStatus;
import java.util.Optional; 

@RestController
@RequestMapping("/boards")
public class boardcontroller {
	@Autowired
	boardrepository boardrepo;
	
	@Autowired
	userrepository userrepo;
	
	@PostMapping
	public ResponseEntity<board> createboard(@RequestBody board boards)
	{
		board savedboard = boardrepo.save(boards);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedboard);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<board> updateboard(@PathVariable Long id,@RequestBody board updatedboard)
	{
		Optional<board> updateboard = boardrepo.findById(id);
		if(updateboard.isPresent())
		{
			board existingboard = updateboard.get();
			existingboard.setOwner(updatedboard.getOwner());
			existingboard.setTitle(updatedboard.getTitle());
			return ResponseEntity.ok(boardrepo.save(existingboard));
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
		}
	}
	
	@PutMapping("/{id}/addmembers/{userid}")
	public ResponseEntity<board> addmembers(@PathVariable Long id,@PathVariable int userid)
	{
		Optional<board> boards = boardrepo.findById(id);
		if(boards.isPresent())
		{
			board existingboard = boards.get();
			Optional<task> member = userrepo.findById(userid);
			if(member.isPresent())
			{
				//TODO
				existingboard.getMembers().add(member.get());
				board addedmember = boardrepo.save(existingboard);
				return ResponseEntity.ok(addedmember);
			}
			else
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
			}
			
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteboard(@PathVariable Long id)
	{
		if(boardrepo.existsById(id))
		{
			boardrepo.deleteById(id);
			return ResponseEntity.ok("Board deleted");
		}
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
		}
		
	}
}
