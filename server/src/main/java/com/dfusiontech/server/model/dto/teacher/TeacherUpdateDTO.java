package com.dfusiontech.server.model.dto.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TeacherUpdateDTO
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-10
 */
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class TeacherUpdateDTO extends TeacherCreateDTO {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 5)
	private boolean fired;

}
