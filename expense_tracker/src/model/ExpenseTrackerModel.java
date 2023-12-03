package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseTrackerModel {

  // encapsulation - data integrity
  /**
   * A list of transactions to hold transaction objects
   */
  private List<Transaction> transactions;

  /**
   * List of indices which match the indices which match the filter
   */
  private List<Integer> matchedFilterIndices;

  /**
   * Set of listeners to send updates to
   */
  protected Set<ExpenseTrackerModelListener> listeners = new HashSet<>();
  // This is applying the Observer design pattern.
  // Specifically, this is the Observable class.

  /**
   * Initialize the list of transactions and matchedFilterIndices
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
  }

  /**
   * This method adds a transaction to the list of transactions
   * Calls state changed method to trigger the update for listeners.
   * 
   * @param t Transaction object
   * @throws IllegalArgumentException if transaction is null
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are
    // non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    stateChanged();
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  /**
   * This method removes a transaction from the list of transactions
   * Calls state changed method to trigger the update for listeners.
   * 
   * @param t Transaction object
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    stateChanged();
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  /**
   * This method returns the list of transactions
   * 
   * @return list of transactions
   */
  public List<Transaction> getTransactions() {
    // encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  /**
   * Sets the indices of matched filters for transactions.
   *
   * @param newMatchedFilterIndices A list of indices representing matched filters
   *                                for transactions.
   *                                Each index must be between 0 (inclusive) and
   *                                the number of transactions (exclusive).
   * @throws IllegalArgumentException If the provided list is null or contains
   *                                  invalid indices.
   */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
    // Perform input validation
    if (newMatchedFilterIndices == null) {
      throw new IllegalArgumentException("The matched filter indices list must be non-null.");
    }
    for (Integer matchedFilterIndex : newMatchedFilterIndices) {
      if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
        throw new IllegalArgumentException(
            "Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
      }
    }
    // For encapsulation, copy in the input list
    this.matchedFilterIndices.clear();
    this.matchedFilterIndices.addAll(newMatchedFilterIndices);
    stateChanged();
  }

  /**
   * Retrieves a copy of the list of indices representing matched filters for
   * transactions.
   *
   * @return A new list containing the indices of matched filters.
   *         Modifying the returned list will not affect the internal state of the
   *         object.
   */
  public List<Integer> getMatchedFilterIndices() {
    // For encapsulation, copy out the output list
    List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
    copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
    return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */
  public boolean register(ExpenseTrackerModelListener listener) {
    // For the Observable class, this is one of the methods.
    //
    if (listener != null && !listeners.contains(listener)) {
      listeners.add(listener);
      return true;

    }
    return false;
  }

  /**
   * Unregisters a listener from receiving updates from the ExpenseTrackerModel.
   *
   * @param listener The ExpenseTrackerModelListener to be unregistered. Must not
   *                 be null.
   * @return {@code true} if the listener was successfully unregistered;
   *         {@code false} otherwise.
   *         Returns {@code false} if the provided listener is null or was not
   *         previously registered.
   */
  public boolean unregister(ExpenseTrackerModelListener listener) {
    if (listener != null && listeners.contains(listener)) {
      listeners.remove(listener);
      return true;

    }
    return false;
  }

  /**
   * @return number of listeners
   */
  public int numberOfListeners() {
    // For testing, this is one of the methods.
    return listeners.size();
  }

  /**
   * Checks if the provided ExpenseTrackerModelListener is currently registered as
   * a listener.
   *
   * @param listener The ExpenseTrackerModelListener to check for registration.
   *                 Must not be null.
   * @return {@code true} if the listener is currently registered; {@code false}
   *         otherwise.
   *         Returns {@code false} if the provided listener is null or not
   *         registered.
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
    // For testing, this is one of the methods.
    return listeners.contains(listener);
  }

  /**
   * Notifies all registered listeners that the state of the ExpenseTrackerModel
   * has changed.
   * This method is part of the Observable pattern and triggers an update for each
   * listener.
   */
  protected void stateChanged() {
    // For the Observable class, this is one of the methods.
    for (ExpenseTrackerModelListener listener : listeners) {
      listener.update(this);
    }
  }

}
