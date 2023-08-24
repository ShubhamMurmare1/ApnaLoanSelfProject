package org.bitlogic.repository;

import org.bitlogic.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<Ledger, Integer>
{

}
