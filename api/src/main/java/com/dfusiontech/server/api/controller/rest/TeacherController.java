package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.dto.teacher.TeacherCreateDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherListDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherUpdateDTO;
import com.dfusiontech.server.service.TeacherService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * TeacherController
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-13
 */
@RestController
@RequestMapping(
	value = TeacherController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Teachers Management Controller"
)
public class TeacherController {

	static final String CONTROLLER_URI = "/api/teacher";

	@Autowired
	private TeacherService teacherService;

	/**
	 * Get Teachers List
	 *
	 * @return Teachers List
	 */
	@RequestMapping(method = RequestMethod.GET, value = "", name = "Get Teachers List")
	public List<TeacherListDTO> getList() {
		List<TeacherListDTO> result = teacherService.getList();

		return result;
	}

	/**
	 * Get Teacher Details
	 *
	 * @return Teacher Details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{itemId}", name = "Get Teacher details")
	public TeacherDTO getDetails(@PathVariable("itemId") @NotNull @Size(min = 1) Long itemId) {
		TeacherDTO itemDTO = teacherService.getDetails(itemId);

		return itemDTO;
	}

	/**
	 * Create new Teacher
	 *
	 * @return New Teacher
	 */
	@RequestMapping(method = RequestMethod.POST, value = "", name = "Create new Teacher", consumes = {MediaType.APPLICATION_JSON})
	public TeacherDTO create(@ApiParam(value = "Teacher Details", required = true) @RequestBody TeacherCreateDTO newItemDTO) {
		TeacherDTO result = teacherService.create(newItemDTO);

		return result;
	}

	/**
	 * Update Teacher
	 *
	 * @return New Teacher
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "", name = "Update existing Teacher", consumes = {MediaType.APPLICATION_JSON})
	public TeacherDTO update(@ApiParam(value = "Teacher update Details", required = true) @RequestBody TeacherUpdateDTO itemDTO) {
		TeacherDTO result = teacherService.update(itemDTO);

		return result;
	}

	/**
	 * Deletes Teacher
	 *
	 * @return ID of removed Teacher
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "", name = "Delete existing Teacher", consumes = {MediaType.APPLICATION_JSON})
	public Long delete(@ApiParam(value = "Simple Teacher Details", required = true) @RequestBody TeacherUpdateDTO itemDTO) {
		Long result = teacherService.delete(itemDTO);

		return result;
	}
}
