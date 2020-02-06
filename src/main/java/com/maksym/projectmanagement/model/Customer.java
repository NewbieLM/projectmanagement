package com.maksym.projectmanagement.model;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private Integer id;
    private String name;
    private Map<Project, Integer> projects;

    public Customer(Integer id, String name, Map<Project, Integer> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    public Customer(Integer id, String name) {
        this(id, name, new HashMap<>());
    }

    public Customer(String name) {
        this(0, name, new HashMap<>());
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

    @Override
    public String toString() {
        String str = "id=" + id + ", name='" + name + "\n";
        if (projects.keySet().size() > 0) {
            str += projectsToString();
        } else {
            str += "=========================";
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
