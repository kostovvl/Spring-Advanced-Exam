package springadvanced.exam.stats.service;
import org.springframework.stereotype.Service;

@Service
public class InterceptionService {

    private int visitors;

    public InterceptionService() {
        this.visitors = 0;
    }

    public void addVisitation() {
        this.visitors++;
    }

    public int getVisitors() {
        return visitors;
    }




}
