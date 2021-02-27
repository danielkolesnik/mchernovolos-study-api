package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.dto.qualification.QualificationListDTO;
import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.repository.jpa.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * QualificationController
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-13
 */
@RestController
@RequestMapping(
	value = QualificationController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Qualifications Management Controller"
)
public class QualificationController {

	static final String CONTROLLER_URI = "/api/qualifications";

	@Autowired
	private QualificationRepository qualificationRepository;

	/**
	 * Get Qualifications List
	 *
	 * @return Qualifications List
	 */
	@RequestMapping(method = RequestMethod.GET, value = "", name = "Get Qualifications List", produces = {MediaType.APPLICATION_JSON})
	public List<QualificationListDTO> getList() {
		List<Qualifications> items = qualificationRepository.findAll();

		List<QualificationListDTO> qualificationListDTOs = QualificationListDTO.fromEntitiesList(items, QualificationListDTO.class);

		return qualificationListDTOs;
	}
}
