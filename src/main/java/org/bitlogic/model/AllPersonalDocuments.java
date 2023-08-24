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

public class AllPersonalDocuments
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer documentID;
	@Lob
	private byte[] addressProof;
	@Lob
	private byte[] panCard;
	@Lob
	private byte[] incomeTax;
	@Lob
	private byte[] addharCard;
	@Lob
	private byte[] photo;
	@Lob
	private byte[] signature;
}
