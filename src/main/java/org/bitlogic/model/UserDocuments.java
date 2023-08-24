package org.bitlogic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class UserDocuments 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

private int userDocId;
@Lob
private byte userPhoto[];
@Lob
private byte userSignature[];
}
