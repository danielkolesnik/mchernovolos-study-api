package com.dfusiontech.server.service;

import antlr.StringUtils;
import com.dfusiontech.server.model.dao.PagedResult;
import com.dfusiontech.server.model.dao.TeacherModelDAO;
import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.TeachersFilter;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.teacher.TeacherCreateDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherListDTO;
import com.dfusiontech.server.model.dto.teacher.TeacherUpdateDTO;
import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.model.jpa.entity.Teachers;
import com.dfusiontech.server.repository.jpa.QualificationRepository;
import com.dfusiontech.server.repository.jpa.TeacherRepository;
import com.dfusiontech.server.rest.exception.ApplicationExceptionCodes;
import com.dfusiontech.server.rest.exception.ConflictException;
import com.dfusiontech.server.rest.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * TeacherService
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-02
 */
@Service
public class TeacherService {

	@Autowired
	private QualificationRepository qualificationRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	TeacherModelDAO teacherModelDAO;

	/**
	 * Get Teachers List
	 *
	 * @return Teachers List
	 */
	public List<TeacherListDTO> getList() {
		List<Teachers> items;

		items = teacherRepository.findAllAndDeletedIsFalse();

		List<TeacherListDTO> teachersDTOList = DTOBase.fromEntitiesList(items, TeacherListDTO.class);

		return teachersDTOList;
	}

	/**
	 * Get Teachers List Filtered
	 *
	 * @return Teachers List
	 */
	public FilteredResponse<TeachersFilter, TeacherListDTO> getListFiltered(FilteredRequest<TeachersFilter> filteredRequest) {
		PagedResult<Teachers> pagedResult = teacherModelDAO
			.getItemsPageable(filteredRequest.getFilter(), filteredRequest.toPageRequest(), filteredRequest.getSort());

		// Convert to DTOs
		List<TeacherListDTO> teachersDTOList = DTOBase.fromEntitiesList(pagedResult.getItems(), TeacherListDTO.class);

		FilteredResponse<TeachersFilter, TeacherListDTO> filteredResponse = new FilteredResponse<>(filteredRequest);
		filteredResponse.setItems(teachersDTOList);
		filteredResponse.setTotal(pagedResult.getCount().intValue());

		return filteredResponse;
	}

	/**
	 * Get Teacher details
	 *
	 * @return Teacher Details
	 */
	public TeacherDTO getDetails(Long itemId) {
		Teachers itemDetails = getTeacher(itemId);
		TeacherDTO itemDTO = new TeacherDTO(itemDetails);

		return itemDTO;
	}

	/**
	 * Get Teacher details
	 *
	 * @return Teacher Details
	 */
	public Teachers getTeacher(Long itemId) {
		Teachers itemDetails;

		try {
			itemDetails = teacherRepository.findById(itemId).get();
		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException(MessageFormat.format("Teacher not found in the database [{0}]", itemId), ApplicationExceptionCodes.TEACHER_NOT_EXISTS);
		}

		return itemDetails;
	}

	/**
	 * Create new Teacher
	 *
	 * @return New Teacher
	 */
	public TeacherDTO create(TeacherCreateDTO newItemDTO) {
		// Verify teacher with such email not exists
		if (teacherRepository.findFirstByEmailAndIdIsNotIn(newItemDTO.getEmail(), Arrays.asList(0l)).isPresent()) {
			throw new ConflictException(
				MessageFormat.format("Teacher with this email already registered in the system [{0}]", newItemDTO.getEmail()),
				ApplicationExceptionCodes.TEACHER_WITH_EMAIL_ALREADY_EXISTS);
		}

		Teachers newItem = new Teachers();

		newItem.setCreatedAt(new Date());

		applyEntityChanges(newItemDTO, newItem);

		Teachers saveResult = teacherRepository.save(newItem);

		TeacherDTO result = new TeacherDTO(saveResult);

		return result;
	}

	/**
	 * Update Teacher
	 *
	 * @return Updated Teacher
	 */
	public TeacherDTO update(TeacherUpdateDTO itemDTO) {

		TeacherDTO result;

		try {

			// Verify teacher with such email exists
			if (teacherRepository.findFirstByEmailAndIdIsNotIn(itemDTO.getEmail(), Arrays.asList(itemDTO.getId())).isPresent()) {
				throw new ConflictException(
					MessageFormat.format("Teacher with this email already registered in the system [{0}]", itemDTO.getEmail()),
					ApplicationExceptionCodes.TEACHER_WITH_EMAIL_ALREADY_EXISTS
				);
			}

			// Get existing item from db
			Teachers existingItem = this.getTeacher(itemDTO.getId());

			// Update details
			Teachers updatedItem = existingItem;
			applyEntityChanges(itemDTO, updatedItem);

			// Save to the db
			Teachers saveResult = teacherRepository.save(updatedItem);

			result = new TeacherDTO(saveResult);

		} catch(NoSuchElementException exception) {
			throw new ItemNotFoundException(
				MessageFormat.format("Teacher not found in the system [{0}]", itemDTO.getId()),
				ApplicationExceptionCodes.TEACHER_NOT_EXISTS
			);
		}
		return result;
	}

	/**
	 * Apply entity changes and linkages
	 *
	 * @param itemDTO
	 * @param entity
	 */
	protected void applyEntityChanges(TeacherCreateDTO itemDTO, Teachers entity) {
		entity.setFirstName(itemDTO.getFirstName());
		entity.setLastName(itemDTO.getLastName());
		entity.setFirstName(itemDTO.getFirstName());
		entity.setLastName(itemDTO.getLastName());
		entity.setFullName(itemDTO.getFirstName() + ' ' + itemDTO.getLastName());
		System.out.println("\n\n\n"+itemDTO.getFirstName() + ' ' + itemDTO.getLastName() + "\n\n\n");
		entity.setEmail(itemDTO.getEmail());

		Optional.ofNullable(itemDTO.getQualificationName()).ifPresent(role -> {
			entity.setQualification(new Qualifications());
			entity.setQualification(qualificationRepository.findOneByName(itemDTO.getQualificationName()));
		});

		entity.setUpdatedAt(new Date());
	}

	/**
	 * Deletes Teacher
	 *
	 * @return ID of deleted Teacher
	 */
	public Long delete(TeacherUpdateDTO itemDTO) {
		Long result = delete(itemDTO.getId());

		return result;
	}

	/**
	 * Deletes Teacher
	 *
	 * @return ID of deleted Teacher
	 */
	public Long delete(Long itemId) {

		Long result;

		try {
			// Get Existing item from the db
			Teachers existingItem = getTeacher(itemId);

			existingItem.setDeleted(true);

			// Delete item details
			teacherRepository.save(existingItem);
			result = itemId;

		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException(
				MessageFormat.format("Teacher not found in system [{0}]", itemId),
				ApplicationExceptionCodes.TEACHER_NOT_EXISTS
			);
		}

		return result;
	}

















}
