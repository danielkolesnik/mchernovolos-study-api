package com.dfusiontech.server.model.dto.teacher;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.model.jpa.entity.Teachers;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.Optional;

/**
 * TeacherDTO
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-02
 */
@Getter
@Setter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor
public class TeacherDTO extends DTOBase<Teachers> {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 1)
	private String email;

	@ApiModelProperty(position = 2)
	private String fullName;

	@ApiModelProperty(position = 3)
	private Qualifications qualification;

	@ApiModelProperty(position = 4)
	private Boolean qualificationExpired;

	@ApiModelProperty(position = 5)
	private Boolean fired;

	@ApiModelProperty(position = 6)
	private Date createdAt;

	@ApiModelProperty(position = 7)
	private Date updatedAt;

	/**
	 * Entity based constructor
	 *
	 * @param teachers
	 */
	public TeacherDTO(Teachers teachers) {
		super(teachers);
	}

	@Override
	public void fromEntity(Teachers teachers) {
		super.fromEntity(teachers);

		qualification = Optional.ofNullable(teachers.getQualification()).orElse(null);
	}
}
