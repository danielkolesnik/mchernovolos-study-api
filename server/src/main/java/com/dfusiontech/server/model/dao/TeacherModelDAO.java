package com.dfusiontech.server.model.dao;

import com.dfusiontech.server.model.data.BaseSort;
import com.dfusiontech.server.model.data.TeachersFilter;
import com.dfusiontech.server.model.jpa.entity.Teachers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Teacher DAO Model
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-05-10
 */
@Service
public class TeacherModelDAO implements PageableModelDAO<Teachers, TeachersFilter> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PagedResult<Teachers> getItemsPageable(TeachersFilter filter, Pageable pageable, BaseSort sort) {
		String nameFilter = Optional.ofNullable(filter.getName()).orElse("");
		List<Long> excludeIds = null;
		if(filter.getExcludeIds()!= null && filter.getExcludeIds().size() > 0) {
			excludeIds = filter.getExcludeIds();
		}
		String qualification = null;
		if (filter.getQualification() != null && filter.getQualification().length() > 0) {
			qualification = filter.getQualification();
		}

		// Define base hql data Query
		String hqlQuery = "SELECT t FROM Teachers t JOIN t.qualifications q ";

		// Define base count Query
		String hqlQueryCount = "SELECT count(t) FROM Teachers t JOIN t.qualifications q ";

		// Build Query String
		String whereString = " WHERE t.fired = false";
		if (StringUtils.isNotEmpty(nameFilter)) {
			whereString += " AND UPPER(t.fullName) LIKE (CONCAT(UPPER(:name), '%'))";
		}
		if (excludeIds != null) {
			whereString += " AND t.id NOT IN :excludeIds";
		}
		if (qualification != null) {
			whereString += " AND r.name = :qualification";
		}

		// Build Sort based on the mapping
		String searchQueryString = hqlQuery + whereString;
		Map<String, String> sortMapping = Map.ofEntries(
			Map.entry("id", "t.id"),
			Map.entry("firstName", "t.firstName"),
			Map.entry("lastName", "t.lastName"),
			Map.entry("email", "t.email"),
			Map.entry("qualificationExpired", "t.qualificationExpired")
		);
		if (sort != null) searchQueryString += sort.toOrderString(sortMapping);

		// Build Query data
		TypedQuery<Teachers> typedQuery = entityManager.createQuery(searchQueryString, Teachers.class);
		this.applySearchFilterValues(nameFilter, excludeIds, qualification, typedQuery);
		typedQuery.setMaxResults(pageable.getPageSize());
		typedQuery.setFirstResult((int) pageable.getOffset());
		List<Teachers> resultList = typedQuery.getResultList();

		// Calculate count query
		Query queryCount = entityManager.createQuery(hqlQueryCount + whereString);
		applySearchFilterValues(nameFilter, excludeIds, qualification, queryCount);
		Long resultsCount = (Long) queryCount.getSingleResult();

		return new PagedResult<Teachers>(resultList, resultsCount);
	}

	/**
	 * Apply query data
	 *
	 * @param nameFilter
	 * @param excludeIds
	 * @param qualification
	 * @param query
	 */
	private void applySearchFilterValues(String nameFilter, List<Long> excludeIds, String qualification, Query query) {
		if(StringUtils.isNotEmpty(nameFilter)) query.setParameter("name", nameFilter);
		if(excludeIds != null) query.setParameter("excludeIds", excludeIds);
		if(qualification != null) query.setParameter("qualification", qualification);
	}

}
