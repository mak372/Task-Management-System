package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@ManyToOne()
	@JoinColumn(name="owner_id")
	private task owner;
	
	@ManyToMany()
	@JoinTable( name = "board_members",
	        joinColumns = @JoinColumn(name = "board_id"),
	        inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<task> members;
	
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<messages> messages;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<task> getMembers() {
		return members;
	}
	public void setMembers(List<task> members) {
		this.members = members;
	}
	public task getOwner() {
		return owner;
	}
	public void setOwner(task owner) {
		this.owner = owner;
	}
}
