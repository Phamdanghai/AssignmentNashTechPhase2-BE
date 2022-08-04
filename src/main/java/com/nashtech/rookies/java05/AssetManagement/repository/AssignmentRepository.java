package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	List<Assignment> findByUserAndStatus(User user, boolean status);

	@Query(value = "select a.* from assignments a where a.asset_id = :id", nativeQuery = true)
	List<Assignment> findAssignmentByAsset(String id);

	@Query(value = "select a.* "
			+ "from assignments a "
			+ "join users c on c.id =a.user_id "
			+ "join assets b on b.id =a.asset_id "
			+ "join users u on a.creator_id = u.id "
			+ "join information i on i.user_id =u.id "
			+ "where i.location = :location", nativeQuery = true)
	List<Assignment> getAllAssignmentByLocation(@Param("location") String location);
	
	@Query(value = "select a.*"
			+ "from  assignments a "
			+ "join assets a2 on a2.id = a.asset_id "
			+ "join users ub on ub.id =a.creator_id "
			+ "join users ut on ut.id =a.user_id "
			+ "join information i on i.user_id = ub.id "
			+ "where (a.asset_id  like %:content% or a2.name like %:content% or ut.username like %:content%) and i.location  = :location", nativeQuery = true)
	List<Assignment> searchUser(@Param("content") String content, @Param("location") String location);

	
	
}
