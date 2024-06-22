package com.example.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Students")
public class Student {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;

	  @Column(name = "fname")
	  private String fname;

	  @Column(name = "lname")
	  private String lname;

	  @Column(name = "email")
	  private String email;
	  
	  public Student() {

	  }

	  public Student(String fname, String lname, String email) {
	    this.fname = fname;
	    this.lname = lname;
	    this.email = email;
	  }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	  
	  
}
