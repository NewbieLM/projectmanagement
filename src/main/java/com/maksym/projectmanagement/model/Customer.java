package com.maksym.projectmanagement.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "customer_projects",
            joinColumns = @JoinColumn(name = "customer_id"))
    @MapKeyJoinColumn(name = "project_id")
    @Column(name = "customer_budget")
    private Map<Project, Integer> projects;

    public Customer() {
    }

    public Customer(Integer id, String name, Map<Project, Integer> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    public Customer(Integer id, String name) {
        this(id, name, new HashMap<>());
    }

    public Customer(String name) {
        this(null, name, new HashMap<>());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Project, Integer> getProjects() {
        return projects;
    }

    public void setProjects(Map<Project, Integer> projects) {
        this.projects = projects;
    }

    public void addProject(Project project, Integer customerBudget) {
        projects.put(project, customerBudget);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    @Override
    public String toString() {
        String str = "id=" + id + ", name='" + name + "\n";
        if (Hibernate.isInitialized(projects) && projects.keySet().size() > 0) {
            str += projectsToString();
        } else {
            str += "=========================\n";
        }
        return str;
    }

    public String projectsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Projects:");
        stringBuilder.append("\n");
        for (Map.Entry<Project, Integer> entry : projects.entrySet()) {
            stringBuilder.append("Customer contribution: ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
