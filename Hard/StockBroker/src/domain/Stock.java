package Hard.StockBroker.src.domain;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.interfaces.Observable;
import Hard.StockBroker.src.domain.interfaces.Observer;

public class Stock implements Observable<Stock> {
    private final Set<Observer<Stock>> observers;
    private String description;
    private int availableQty;
    private final String id;
    private double price;
    private String name;

    public Stock(String id, String name) {
        this.observers = ConcurrentHashMap.newKeySet();
        this.name = name;
        this.id = id;
    }

    @Override
    public void subscribe(Observer<Stock> o) {
        observers.add(o);
    }

    @Override
    public void unsubscribe(Observer<Stock> o) {
        observers.remove(o);
    }

    @Override
    public void publish() {
        for (Observer<Stock> o : observers) {
            o.onNotify(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        publish();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stock other = (Stock) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Stock [id=" + id + ", name=" + name + ", description=" + description + ", availableQty=" + availableQty
                + ", price=" + price + "]";
    }

}
