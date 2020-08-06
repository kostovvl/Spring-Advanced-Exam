package springadvanced.exam.stats.service;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InterceptionService {

    private int indexPageVisits;
    private int homePageVisits;
    private int allClicks;


    public InterceptionService() {
    }

    public void addIndexPageVisit() {
        this.indexPageVisits++;
    }

    public void addHomePageVisit() {
        homePageVisits++;
    }

    public void addClick() {
        this.allClicks++;
    }

    public int getIndexPageVisits() {
        return indexPageVisits;
    }

    public int getHomePageVisits() {
        return homePageVisits;
    }

    public int getAllClicks() {
        return allClicks;
    }

    public Map<String, Integer> getAllStats() {
        Map<String, Integer> result = new HashMap<>();
        result.put("Index", getIndexPageVisits());
        result.put("Home", getHomePageVisits());
        result.put("All", getAllClicks());
        return result;
    }



}
