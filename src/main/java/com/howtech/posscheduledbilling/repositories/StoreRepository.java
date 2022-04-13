package com.howtech.posscheduledbilling.repositories;

import java.util.List;
import java.util.Optional;

import com.howtech.posscheduledbilling.models.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Damond Howard
 * @apiNote this is a repository interface that handles our connection to the
 *          database
 *
 */

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<List<Store>> findByOwnerName(String ownerName);

}