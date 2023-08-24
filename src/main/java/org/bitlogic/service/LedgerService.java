package org.bitlogic.service;

import java.util.List;

import org.bitlogic.model.Ledger;

public interface LedgerService {

public	Ledger addLedger(Ledger l);

public List<Ledger> getLedger();

public List<Ledger> createLedger(int customerId);

}
