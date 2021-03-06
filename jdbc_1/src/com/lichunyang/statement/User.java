package com.lichunyang.statement;

import java.util.Objects;

public class User {

	private String user;
	private String password;

	public User() {
	}

	public User(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [user=" + user + ", password=" + password + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user1 = (User) o;
		return Objects.equals(user, user1.user) && Objects.equals(password, user1.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, password);
	}
}
