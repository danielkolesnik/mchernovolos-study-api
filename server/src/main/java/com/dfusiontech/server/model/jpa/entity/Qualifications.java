package com.dfusiontech.server.model.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Qualifications
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-04-26
 */
@Entity
@Table(name = "qualifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id", "name"})
public class Qualifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

}
