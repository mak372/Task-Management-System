package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class messages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	
	private Date CreatedAt = new Date();
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private board boardz;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private task author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public board getBoardz() {
		return boardz;
	}

	public void setBoardz(board boardz) {
		this.boardz = boardz;
	}

	public task getAuthor() {
		return author;
	}

	public void setAuthor(task author) {
		this.author = author;
	}
	
}
