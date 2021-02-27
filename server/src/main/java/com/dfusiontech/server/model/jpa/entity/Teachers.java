package com.dfusiontech.server.model.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Teachers
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-04-25
 */
@Entity
@Table(name = "teachers")
@AllArgsConstructor
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class Teachers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "full_name", nullable = true)
	private String fullName;

	@Column(name = "first_name", nullable = true)
	private String firstName;

	@Column(name = "last_name", nullable = true)
	private String lastName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name = "qualified_at")
	private Date qualifiedAt;

	@Column(name = "qualification_expired")
	private Boolean qualificationExpired;

	@Column(name = "fired")
	private Boolean fired;

	@Temporal(TemporalType.DATE)
	@Column(name = "requalification_at")
	private Date requalificationAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "is_deleted")
	private Boolean deleted;

	@ManyToOne(optional = false, cascade = {CascadeType.DETACH})
	@JoinColumn(name = "qualification_id")
	private Qualifications qualification;

	/**
	 * Default constructor
	 */
	public Teachers() {
		fired = false;
		qualificationExpired = false;
		deleted = false;

//		Date requal = new Date();
//		Calendar c = Calendar.getInstancse();
//		c.setTime(requal);
//		c.set(Calendar.YEAR, c.get(Calendar.YEAR)+4);
//		requal = c.getTime();


	}
}
