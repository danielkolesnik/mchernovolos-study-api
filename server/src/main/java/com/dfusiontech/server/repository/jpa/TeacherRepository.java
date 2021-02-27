package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.Teachers;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * TeacherRepository
 *
 * @author Daniel A. Kolesnik <daneelkolesnik@gmail.com>
 * @version 1.0.0
 * @since 2019-04-26
 */
@Repository
public interface TeacherRepository extends CoreRepository<Teachers, Long> {

	Optional<Teachers> findById(Long id);

	@Query("SELECT t FROM Teachers t WHERE t.deleted = false")
	List<Teachers> findAllAndDeletedIsFalse();

	Teachers findByEmail(String email);

	Optional<Teachers> findFirstByEmail(String email);

	List<Teachers> findByEmailIsLike(String email);

	Optional<Teachers> findFirstByEmailAndIdIsNotIn(String email, Collection<Long> excludeIds);

}
