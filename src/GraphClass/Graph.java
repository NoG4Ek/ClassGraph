package GraphClass;

import java.util.*;

public class Graph {

    private class Arc {
        public Arc(String name, int size){
            this.name = name;
            this.size = size;
        }

        private String name;
        private int size;

        public void setName(String name){
            this.name = name;
        }
        public void setSize(int size){
            this.size = size;
        }
        public String getName(){
            return name;
        }
        public int getSize(){
            return  size;
        }
    }

    private Map<String, List<Arc>> graph = new HashMap();

    public void clearGraph (){
        graph.clear();
    }

    public void addVertex(String name){
        graph.put(name, null);
    }
    public void addArc(String from, String to, int size){
        List<Arc> list;
        if (graph.get(from) == null)
            list = new ArrayList();
        else
            list = graph.get(from);
        list.add(new Arc(to, size));
        graph.put(from, list);
    }
    public void delVertex(String name){
        graph.remove(name);
        for(int i = 0; i < graph.size(); i++){
            for(int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++){
                if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(name)){
                    graph.get(graph.keySet().toArray()[i]).remove(j);
                }
            }
        }
    }
    public void delArc(String from, String to, int size){
        for (int i = 0; i < graph.get(from).size(); i++){
            if (graph.get(from).get(i).getName().equals(to) && graph.get(from).get(i).getSize() == size){
                graph.get(from).remove(i);
            }
        }
    }
    public void changeName(String oldName, String newName){
        graph.put(newName, graph.get(oldName));
        graph.remove(oldName);
        for(int i = 0; i < graph.size(); i++){
            if (graph.get(graph.keySet().toArray()[i]) != null) {
                for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                    if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(oldName)) {
                        graph.get(graph.keySet().toArray()[i]).get(j).setName(newName);
                    }
                }
            }
        }
    }
    public void changeArcSize(String from, String to, int oldSize, int newSize){
        for (int i = 0; i < graph.get(from).size(); i++){
            if (graph.get(from).get(i).getName().equals(to) && graph.get(from).get(i).getSize() == oldSize){
                graph.get(from).get(i).setSize(newSize);
            }
        }
    }
    public String getOutArcs(String from){
        String s = "";
        for (int i = 0; i < graph.get(from).size(); i++){
            if (!s.equals("")){
                s += "\n";
            }
            s += i+1 + ". Направлена в вершину " + graph.get(from).get(i).getName() +
                    ", длина " + graph.get(from).get(i).getSize();
        }
        return s;
    }
    public String getInArcs(String to){
        String s = "";
        int ch = 0;
        for(int i = 0; i < graph.size(); i++){
            if (graph.get(graph.keySet().toArray()[i]) != null) {
                for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                    if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(to)) {
                        if (!s.equals("")){
                            s += "\n";
                        }
                        s += ch + 1 + ". Направлена из вершины " + graph.keySet().toArray()[i] +
                                ", длина " + graph.get(graph.keySet().toArray()[i]).get(j).getSize();
                        ch++;
                    }
                }
            }
        }
        if (s.equals(""))
            throw new NullPointerException();
        else
            return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph1 = (Graph) o;
        return Objects.equals(graph, graph1.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}
