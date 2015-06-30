package models;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

    public Boolean Initialized;
    public String Start;
    public String Stop;
    public Integer ExactMatches;
    public Integer StartMatches;
    public Integer EndMatches;
    public List<Area> StartArea;
    public List<Area> DestinationAreas;

    public SearchResponse() {
        Initialized = false;
        Start = "";
        Stop = "";
        ExactMatches = 0;
        StartMatches = 0;
        EndMatches = 0;
        StartArea = new ArrayList<Area>();
        DestinationAreas = new ArrayList<Area>();
    }

    @Transactional(readOnly = true)
    public void GetSearchResults(String startAreaSubLoc, String startAreaLoc, String endAreaSubLoc, String endAreaLoc) {

        Initialized = true;
        String constraint1 = "";
        String constraint2 = "";
        String area;

        if (startAreaSubLoc.equals("")){
            constraint1 = "startAreaLoc = '" + startAreaLoc + "'";
            this.Start = startAreaLoc;
        }else{
            constraint1 = "(startAreaLoc = '" + startAreaLoc + "') AND (startAreaSubLoc = '" + startAreaSubLoc + "')";
            this.Start = startAreaSubLoc + ", " + startAreaLoc;
        }

        String query = "SELECT s FROM Search s WHERE " + constraint1 ;
        TypedQuery<Search> query_result = JPA.em().createQuery(query, Search.class);
        List<Search> search1 = query_result.getResultList();
        this.StartMatches = search1.size();

        for (Search s : search1){
            if (s.endAreaSubLoc.equals("")){
                area = s.endAreaLoc;
            }else{
                area = s.endAreaSubLoc + ", " + s.endAreaLoc;
            }
            this.areaExists(this.StartArea, area);
        }

        if (endAreaSubLoc.equals("")){
            constraint2 = "endAreaLoc = '" + endAreaLoc + "'";
            this.Stop = endAreaLoc;
        }else{
            constraint2 = "(endAreaLoc = '" + endAreaLoc + "') AND (endAreaSubLoc = '" + endAreaSubLoc + "')";
            this.Stop = endAreaSubLoc + ", " + endAreaLoc;
        }

        query = "SELECT s FROM Search s WHERE " + constraint2 ;
        query_result = JPA.em().createQuery(query, Search.class);
        List<Search> search2 = query_result.getResultList();
        this.EndMatches = search2.size();

        for (Search s : search2){
            if (s.startAreaSubLoc.equals("")){
                area = s.startAreaLoc;
            }else{
                area = s.startAreaSubLoc + ", " + s.startAreaLoc;
            }
            this.areaExists(this.DestinationAreas, area);
        }

        query = "SELECT s FROM Search s WHERE (" + constraint1 + ") AND (" + constraint2 + ")";
        query_result = JPA.em().createQuery(query, Search.class);
        List<Search> search3 = query_result.getResultList();
        this.ExactMatches = search3.size();
    }

    public static class Area{
        public String area;
        public Integer occurances;
    }

    private static void areaExists(List<Area> mylist, String area){

        if (!mylist.isEmpty()){
            for (Area a : mylist){
                if (a.area.equals(area)){
                    a.occurances++;
                    return;
                }
            }
        }

        Area newArea = new Area();
        newArea.area = area;
        newArea.occurances = 1;
        mylist.add(newArea);
    }
}
