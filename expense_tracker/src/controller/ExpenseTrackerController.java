package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.ExpenseTrackerModelListener;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController implements ExpenseTrackerModelListener {

  /**
   * The data model
   */
  private ExpenseTrackerModel model;

  /**
   * The UI of the app
   */
  private ExpenseTrackerView view;
  /**
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  /**
   * Constructor to initialize the model and the view
   * 
   * @param model - The transaction model
   * @param view  - The view
   * 
   */
  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    // For the MVC architecture pattern, the Observer design pattern is being
    // used to update the View after manipulating the Model.
    this.model.register(this);
  }

  /**
   * This method sets the filter based on user selection
   * 
   * @param filter - Sets the filter to either amount or category filter.
   */
  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  /**
   * This method checks if a transaction is valid.
   * If valid, it adds a transaction to the model and the view, refreshes the view
   * 
   * @param amount   - amount field of a transaction
   * @param category - category field of a transaction
   * @return boolean - whether the transaction was added or not
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    return true;
  }

  /**
   * This method applies the selected filter on the list of transactions - either
   * filters by amount or category and also highlights the transactions which pass
   * the filter check.
   * If no filter is applied, it displays message dialog with the said error.
   */
  public void applyFilter() {
    // null check for filter
    if (filter != null) {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      model.setMatchedFilterIndices(rowIndexes);
    } else {
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();
    }

  }

  // for undoing any selected transaction
  /**
   * Undoes a transaction based on the specified row index in the model's
   * transactions list.
   *
   * @param rowIndex The index of the transaction to be undone.
   * @return {@code true} if the undo operation was successful; {@code false}
   *         otherwise.
   *         Returns {@code true} when the specified row index is valid, and the
   *         transaction
   *         was successfully removed from the model. Returns {@code false} when
   *         the specified
   *         row index is out of bounds or the model does not contain a
   *         transaction at that index.
   */
  public boolean undoTransaction(int rowIndex) {
    if (rowIndex >= 0 && rowIndex < model.getTransactions().size()) {
      Transaction removedTransaction = model.getTransactions().get(rowIndex);
      model.removeTransaction(removedTransaction);
      // The undo was allowed.
      return true;
    }

    // The undo was disallowed.
    return false;
  }

  @Override
  public void update(ExpenseTrackerModel model) {
    view.update(model);
  }
}
