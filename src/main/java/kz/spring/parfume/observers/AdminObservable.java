package kz.spring.parfume.observers;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminObservable {
    private List<AdminObserver> observers = new ArrayList<>();

    public void addObserver(AdminObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (AdminObserver observer : observers) {
            observer.update();
        }
    }
}
