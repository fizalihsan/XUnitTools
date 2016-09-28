package com.fizal.xunit.dbunit.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fmohamed on 9/20/2016.
 */
public class Table implements Comparable<Table> {
    private String name;
    private List<String> dependsOn = new LinkedList<>();
    private int order = 1;

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }

    public Table dependsOn(String dependsOn) {
        this.dependsOn.add(dependsOn);
        return this;
    }

    public int getOrder() {
        return order;
    }

    public Table setOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    public int compareTo(Table t) {
        return this.getOrder() - t.getOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return name != null ? name.equals(table.name) : table.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", dependsOn=" + dependsOn +
                ", order=" + order +
                '}';
    }
}