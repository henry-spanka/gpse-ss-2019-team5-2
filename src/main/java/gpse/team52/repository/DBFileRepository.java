package gpse.team52.repository;


import gpse.team52.domain.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DBFile Repository.
 */
@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
