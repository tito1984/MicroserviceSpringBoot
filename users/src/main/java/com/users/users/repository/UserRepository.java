package com.users.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.user.commons.usercommons.entity.User;



@RepositoryRestResource(path="users")
public interface UserRepository extends PagingAndSortingRepository<User,Long>, CrudRepository<User,Long> {

    @RestResource(path="username")
    public User findByUsername(@Param("username") String username);

    @Query("select u from User u where u.username=?1")
    public User getUserByPersonalizedQuery(String username);

    @RestResource(path = "/{id}")
    public Optional<User> findById(Long id);

    @RestResource(path = "/{id}")
    public void deleteById(Long id);
    
}
