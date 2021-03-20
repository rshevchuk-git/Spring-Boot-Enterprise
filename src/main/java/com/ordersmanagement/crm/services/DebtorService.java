package com.ordersmanagement.crm.services;

import com.ordersmanagement.crm.models.pojos.Debtor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@AllArgsConstructor
public class DebtorService {

    @Qualifier("ordersEntityManager")
    private EntityManager emf;

    public List<Debtor> getDebtorList() {
        TypedQuery<Debtor> query = emf.createQuery("SELECT " +
                "new com.ordersmanagement.crm.models.pojos.Debtor(c.customerName, MAX(o.date), MAX(o.payDate), (SUM(o.finalSum) - SUM(o.paySum)))\n" +
                "FROM Customer c\n" +
                "JOIN Order o on c.id = o.customer.id\n" +
                "GROUP BY c.customerName ", Debtor.class);
        List<Debtor> debtorList = query.getResultList();
        debtorList.sort((o1, o2) -> (int) (o2.getDebt() - o1.getDebt()));
        return debtorList;
    }
}
