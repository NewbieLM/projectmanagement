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
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
