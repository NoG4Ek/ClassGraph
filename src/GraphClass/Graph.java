package GraphClass;

import java.util.*;

public class Graph {

    Map<String, Map<String, Integer>> graph = new HashMap<>();
    String startVertex = "";

    public void clearGraph (){
        startVertex = "";
        graph.clear();
    }

    public boolean addVertex(String name){
        if (startVertex.equals("")) {
            startVertex = name;
            graph.put(name, new HashMap<>());
            return true;
        } else {
            System.out.println("Начальная вершина уже существует");
            return false;
        }
    }

    public boolean addVertex(String name, String frto, int size, boolean dur){
        if (graph.get(name) == null) {
            if (startVertex.equals("")) {
                System.out.println("Сначала создайте начальную вершину");
                return false;
            } else {
                if (dur) {
                    graph.put(name, new HashMap<>());
                    return addArc(name, frto, size);
                } else {
                    graph.put(name, new HashMap<>());
                    return addArc(frto, name, size);
                }
            }
        } else {
            System.out.println("Вершина уже существует");
          return false;
        }
    }

    public boolean addArc(String from, String to, int size){
        if (graph.get(from) != null && graph.get(to) != null) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            if (graph.get(from) != null) {
                map = graph.get(from);
            }
            map.put(to, size);
            graph.put(from, map);
            return true;
        } else {
            System.out.println("Вершина(-ны) не существуют");
            return false;
        }
    }

    public boolean delVertex(String name){
        if (graph.get(name) != null) {
            if (name.equals(startVertex))
                startVertex = "";
            graph.remove(name);

            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.keySet().toArray()[i]).remove(name);
                delSplited((String) graph.keySet().toArray()[i]);
            }
            return true;
        } else {
            System.out.println("Вершины не существует");
            return false;
        }
    }

    public boolean delArc(String from, String to){
        if (graph.get(from) == null || graph.get(to) == null) {
            System.out.println("Дуги/вершина(-ы) не сущестсвует(-ют)");
            return false;
        }
        else {
            graph.get(from).remove(to);
            delSplited(from);
            delSplited(to);
            return true;
        }
    }

    public boolean changeName(String oldName, String newName) {
        if (graph.get(newName) == null) {
            if (graph.get(oldName) != null) {
                graph.put(newName, graph.get(oldName));
                graph.remove(oldName);
                for (int i = 0; i < graph.size(); i++) {
                    if (graph.keySet().toArray()[i] != newName) {
                        if (graph.get(graph.keySet().toArray()[i]).get(oldName) != null) {
                            Map<String, Integer> map;
                            map = graph.get(graph.keySet().toArray()[i]);
                            map.put(newName, graph.get(graph.keySet().toArray()[i]).get(oldName));
                            map.remove(oldName);
                            graph.put((String) graph.keySet().toArray()[i], map);
                        }
                    }
                }
                return true;
            } else {
                System.out.println("Вершина не существует");
                return false;
            }
        } else {
            System.out.println("Вершина с таким именем уже существует");
            return false;
        }
    }

    public boolean changeArcSize(String from, String to, int newSize){
        if (graph.get(from) != null && graph.get(to) != null && graph.get(from).get(to) != null) {
            graph.get(from).put(to, newSize);
            return true;
        }
        else {
            System.out.println("Дуги/вершина(-ы) не сущестсвует(-ют)");
            return false;
        }
    }

    public Map<Object, Integer> getOutArcs(String from){
        if (graph.get(from) != null) {
            Map<Object, Integer> map = new HashMap<Object, Integer>();
            for (int i = 0; i < graph.get(from).size(); i++) {
                map.put(graph.get(from).keySet().toArray()[i], graph.get(from).get(graph.get(from).keySet().toArray()[i]));
            }

            if (map.keySet().size() == 0) {
                System.out.println("У вершины нет исходящих дуг");
                return null;
            }
            else
                return map;
        } else {
            throw new NullPointerException();
        }
    }

    public Map<Object, Integer> getInArcs(String to) {
        if (graph.get(to) != null) {
            Map<Object, Integer> map = new HashMap<Object, Integer>();
            for (int i = 0; i < graph.size(); i++) {
                if (!graph.keySet().toArray()[i].equals(to))
                    for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                        if (graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j].equals(to))
                            map.put(graph.keySet().toArray()[i], graph.get(graph.keySet().toArray()[i]).
                                    get(graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j]));
                    }
            }
            if (map.keySet().size() == 0) {
                System.out.println("У вершины нет входящих дуг");
                return null;
            }
            else
                return map;
        } else {
            throw new NullPointerException();
        }
    }

    private boolean delSplited(String checkable){
        if (checkable == startVertex)
            return false;
        Map<String, Integer> peakSt = new HashMap();
        for (int i = 0; i < graph.size(); i++){
            peakSt.put((String) graph.keySet().toArray()[i], 1);
        }
        peakSt.put(checkable, 2);
        Deque<String> deque = new LinkedList<String>();
        deque.addFirst(checkable);

        while ((peakSt.containsValue(1) || peakSt.containsValue(2)) && deque.size() != 0) {
            String current = deque.getFirst();
            if(peakSt.get(current) == 2) {
                for (int i = 0; i < graph.get(current).size(); i++) {
                    if (graph.get(current).keySet().toArray()[i] == startVertex)
                        return false;
                    if (peakSt.get(graph.get(current).keySet().toArray()[i]) == 1) {
                        deque.addLast((String) graph.get(current).keySet().toArray()[i]);
                        peakSt.put((String) graph.get(current).keySet().toArray()[i], 2);
                    }
                }
                for (int i = 0; i < graph.size(); i++) {
                    for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                        if (graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j].equals(current)) {
                            if (graph.keySet().toArray()[i] == startVertex)
                                return false;
                            if (peakSt.get(graph.keySet().toArray()[i]) == 1) {
                                deque.addLast((String) graph.keySet().toArray()[i]);
                                peakSt.put((String) graph.keySet().toArray()[i], 2);
                            }
                        }
                    }
                }
                peakSt.put(current, 3);
                deque.removeFirst();
            }
        }

        for (int i = 0; i < peakSt.size(); i++){
            if (peakSt.get(peakSt.keySet().toArray()[i]) == 3){
                delVertexAfterSplit((String) peakSt.keySet().toArray()[i]);
            }
        }

        return true;
    }

    private boolean delVertexAfterSplit(String name){
        if (graph.get(name) != null) {
            if (name.equals(startVertex))
                startVertex = "";
            graph.remove(name);

            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.keySet().toArray()[i]).remove(name);
            }
            return true;
        } else {
            System.out.println("Вершины не существует");
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph1 = (Graph) o;
        return Objects.equals(graph, graph1.graph) &&
                Objects.equals(startVertex, graph1.startVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, startVertex);
    }
}
