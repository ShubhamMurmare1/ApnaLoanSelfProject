package org.bitlogic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmailSender 
{
	private String formEmail;
	private String toEmail;
	private String subject;
	private String textMessage; 
}
