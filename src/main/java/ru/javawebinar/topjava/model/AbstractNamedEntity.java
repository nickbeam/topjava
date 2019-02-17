package ru.javawebinar.topjava.model;

public abstract class AbstractNamedEntity extends AbstractBaseEntity implements Comparable<AbstractNamedEntity> {

    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int compareTo(AbstractNamedEntity o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), id, name);
    }
}