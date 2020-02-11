package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.CustomerController;
import com.maksym.projectmanagement.controller.ProjectController;
import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maksym.projectmanagement.util.Util.*;

public class CustomerView {
    private CustomerController customerController;
    private ProjectController projectController;

    private List<String> actions;

    public CustomerView() {
        this.actions = initActions();
    }

    public void customerMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions);
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    addNewCustomer();
                    break;
                case 2:
                    showAllCustomers();
                    break;
                case 3:
                    Integer customerId = readNumberFromConsole("Enter the customer ID:");
                    updateCustomer(customerId);
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:

                    break;
            }

        }
    }

    private void addNewCustomer() {
        String name = readFromConsole("Enter customer name:").trim();
        Customer customer = new Customer(name);
        customer = customerController.save(customer);
        if (customer == null) {
            writeToConsole("FAILED, TRY AGAIN");
        } else {
            writeToConsole("SUCCESS");
            updateCustomer(customer.getId());
        }
    }

    private void showAllCustomers() {
        List<Customer> customers = customerController.getAll();
        if (customers == null || customers.isEmpty()) {
            writeToConsole("NO SAVED CUSTOMERS");
        } else {
            writeToConsole(customers);
        }
    }

    private void updateCustomer(Integer customerId) {
        Integer elementId = -1;
        while (elementId != 4) {
            Customer customer = customerController.get(customerId);
            if (customer == null) {
                writeToConsole("NO CUSTOMER WITH ID: " + customerId);
                return;
            } else {
                writeToConsole(customer);
            }

            writeToConsole("1. Change customer name");
            writeToConsole("2. Add project");
            writeToConsole("3. Delete project");
            writeToConsole("4. Back");
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            boolean updated = false;
            switch (elementId) {
                case 1:
                    String newName = readFromConsole("Enter customer new name");
                    customer.setName(newName);
                    updated = customerController.update(customer);
                    break;
                case 2:
                    List<Project> projects = projectController.getAll();
                    writeToConsole(projects.stream().filter(t -> !customer.getProjects().keySet().contains(t)).collect(Collectors.toList()));
                    Integer newTeamId = readNumberFromConsole("To add a new protect to customer, enter project ID");
                    Integer customerBudget = readNumberFromConsole("Enter customer budget size");
                    updated = customerController.addProject(customer.getId(), customerBudget, newTeamId);
                    break;
                case 3:
                    writeToConsole(customer.getProjects().size() > 0 ? customer.projectsToString() : "NO PROJECT YET");
                    Integer projectId = readNumberFromConsole("To delete project, enter project ID");
                    updated = customerController.deleteProject(customer.getId(), projectId);
                    break;
                case 4:
                    return;
            }
            writeToConsole(updated ? "SUCCESS" : "FAILED");
        }

    }


    private void deleteCustomer() {
        Integer customerId = readNumberFromConsole("Enter the customer ID to delete:");
        writeToConsole(customerController.delete(customerId) ? "SUCCESS" : "FAILED");
    }

    private List<String> initActions() {
        return new ArrayList<String>() {{
            add("1. Add new customer");
            add("2. Show all customers");
            add("3. Update customer");
            add("4. Delete customer");
            add("5. Back to main menu");
        }};
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }
}
