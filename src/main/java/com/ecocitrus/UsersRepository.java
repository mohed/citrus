package com.ecocitrus;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */
public interface UsersRepository extends CrudRepository<Users, Long> {

    List<Invoice> findByUserID(Long id);

    Users findByUsername(String username);

}
