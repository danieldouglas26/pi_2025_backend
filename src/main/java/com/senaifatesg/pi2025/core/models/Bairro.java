package com.senaifatesg.pi2025.core.models;

import com.senaifatesg.pi2025.core.models.interfaces.IVertice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bairro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bairro implements IVertice {
  @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
	
	private String nome;

	@Override
	public Long verticeId() {
		return this.id;
	}
}

