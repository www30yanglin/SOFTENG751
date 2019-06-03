package com.queues2;

public class User implements Comparable<User>{
	private String name;
	private int age;

	public User(String name,int age){
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/***
	 * reorder：
	 * *the youngest one is put in the tail of queue,ascending order
	 */
	@Override
	public int compareTo(User o) {
		return this.age -o.age;
	}
}
