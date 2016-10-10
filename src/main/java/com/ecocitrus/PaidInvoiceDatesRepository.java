package com.ecocitrus;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-10-07.
 */
public interface PaidInvoiceDatesRepository extends CrudRepository<Paidinvoicedates, Long> {
    List<Paidinvoicedates> findByInvoiceIdOrderByDuedateDesc(Long id);

//    Paidinvoicedates findByPaiddateAndInvoiceid(Date date, Long id);

}
