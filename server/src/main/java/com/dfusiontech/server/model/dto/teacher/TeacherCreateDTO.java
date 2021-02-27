package com.dfusiontech.server.model.dto.teacher;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.model.jpa.entity.Teachers;
import com.dfusiontech.server.repository.jpa.QualificationRepository;
import com.dfusiontech.server.util.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;

/**
 * TeacherCreateDTO
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-10
 */
@Getter
@Setter
@ToString(of = {"email"})
@EqualsAndHashCode(of = {"email"})
public class TeacherCreateDTO extends DTOBase<Teachers> {

	@ApiModelProperty(position = 1)
	private String firstName;

	@ApiModelProperty(position = 2)
	private String lastName;

	@ApiModelProperty(position = 3)
	private String email;

	/**
	 * Teacher qualification
	 */
	@ApiModelProperty(allowableValues = "TEACHER, PHD, DOCTOR, PROFESSOR", position = 4)
	private String qualificationName = "";

	/**
	 * Convert Teacher Details DTO to Entity
	 *
	 * @return Teachers Entity
	 */
	public Teachers toEntity(Teachers update) {
		Teachers result = super.toEntity(update);

		QualificationRepository qualificationRepository = BeanUtil.getBean(QualificationRepository.class);

		// Set Qualification from name
		Optional
			.ofNullable(qualificationName)
			.ifPresent(qualification -> result.setQualification(qualificationRepository.findOneByName(qualificationName)));

		// Set Create/Update Dates
		if (result.getCreatedAt() == null) {
			result.setCreatedAt(new Date());
		}
		result.setUpdatedAt(new Date());

		return result;
	}
}
