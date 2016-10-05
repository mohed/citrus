package com.ecocitrus;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findById(Long id);

}
