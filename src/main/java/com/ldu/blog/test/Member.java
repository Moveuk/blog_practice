package com.ldu.blog.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
}
