package com.dfusiontech.server.model.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//import java.util.List;

/**
 * Teachers Filter
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-10
 */
@NoArgsConstructor
@Setter
@Getter
public class TeachersFilter extends NameFilter {
	@ApiModelProperty(allowableValues = "TEACHER, PHD, DOCTOR, PROFESSOR", position = 2)
	private String qualification;
}
