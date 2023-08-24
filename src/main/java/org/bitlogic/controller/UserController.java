package org.bitlogic.controller;

import java.io.IOException;


import org.bitlogic.model.User;
import org.bitlogic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController 
{
	@Autowired
	UserService us;

	//Post API => http://localhost:1997/createUser

	@PostMapping(value="/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> createUser(@RequestParam (value="userPhoto") MultipartFile file1,
			@RequestParam(value="userSignature")MultipartFile file2,
			@RequestParam(value="data")String json) throws IOException
	
	{
		ObjectMapper om=new ObjectMapper();
		User u=om.readValue(json, User.class);
		u.getUserDocs().setUserPhoto(file1.getBytes());
		u.getUserDocs().setUserSignature(file2.getBytes());
		
		return new ResponseEntity<User>( us.createUser(u), HttpStatus.CREATED);
		
	}
}
