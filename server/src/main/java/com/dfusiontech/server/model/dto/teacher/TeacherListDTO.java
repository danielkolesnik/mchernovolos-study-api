package com.dfusiontech.server.model.dto.teacher;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.model.jpa.entity.Teachers;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TeacherListDTO
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-02
 */
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class TeacherListDTO extends DTOBase<Teachers> {
	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 1)
	private String fullName;

	@ApiModelProperty(position = 2)
	private String firstName;

	@ApiModelProperty(position = 3)
	private String lastName;

	@ApiModelProperty(position = 4)
	private String email;

	@ApiModelProperty(position = 5)
	private Boolean fired;

	@ApiModelProperty(position = 6)
	private Boolean qualificationExpired;

	@ApiModelProperty(position = 7)
	private Qualifications qualification;

}
