package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

@Entity
public class task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	@OneToMany(mappedBy="owner",cascade=CascadeType.ALL)
	private List<board> boards;
	// Made change here
	@ManyToMany(mappedBy="members")
	private List<board> boardsmemberof;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<messages> messages;
	
	public List<board> getBoardsmemberof() {
		return boardsmemberof;
	}
	public void setBoardsmemberof(List<board> boardsmemberof) {
		this.boardsmemberof = boardsmemberof;
	}
	public List<board> getBoards() {
		return boards;
	}
	public void setBoards(List<board> boards) {
		this.boards = boards;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
