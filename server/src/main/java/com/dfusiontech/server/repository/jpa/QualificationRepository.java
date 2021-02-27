package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.Qualifications;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * QualificationRepository
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-04-26
 */
@Repository
public interface QualificationRepository extends CoreRepository<Qualifications, Long> {
//	@Query(value = "SELECT q FROM qualifications q")

	Qualifications findOneByName(String name);
}
