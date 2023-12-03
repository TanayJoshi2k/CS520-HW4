package model.Filter;

import java.util.List;

import model.Transaction;

/**
 * The TransactionFilter supports filtering the transaction list.
 *
 * NOTE) The Strategy design pattern is being applied. This is the Strategy
 * interface.
 */
public interface TransactionFilter {

  /**
   * Filters a list of transactions based on the implemented strategy.
   *
   * @param transactions The list of transactions to be filtered.
   * @return A new list containing transactions that satisfy the filtering
   *         strategy.
   */
  public List<Transaction> filter(List<Transaction> transactions);

}
