package Matrix;

// A* algorithm for matrix;
// Input: matrix and pathes-massive; Output: void (making pathes in a massive);

import java.awt.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Vector;

public class Algorithm {
    private HashSet<Point> closeSet; // Исследованные вершины
    private PriorityQueue<Vertex> openSet;
    private Vertex[][] fromSet;
    private boolean isFirstStep;


    public Algorithm(Labyrinth labyrinth) {
        closeSet = new HashSet<>();
        openSet = new PriorityQueue<>(idComparator);
        fromSet = new Vertex[labyrinth.getSize()][labyrinth.getSize()]; // Кратчайшие пути для вершин
        openSet.add(new Vertex(labyrinth.getStart(),0,0));
        for (int i = 0; i < labyrinth.getSize(); i++) {
            for (int j = 0; j < labyrinth.getSize(); j++) {
                fromSet[i][j] = new Vertex(new Point(i,j),0,10000);
            }
        }
        stepFindA(labyrinth);
        isFirstStep = true;
    }

    public boolean openSetIsEmpty(){
        return openSet.isEmpty();
    }
    private void printOpenSetVertexes(PriorityQueue<Vertex> openSet, Labyrinth labyrinth) {
        Vertex now = openSet.peek();
        if (now != null && !now.name.equals(labyrinth.getStart()) && !now.name.equals(labyrinth.getFinish()))
            labyrinth.setCell(now.name, '4');
        Vertex[] arr = new Vertex[openSet.size()];
        arr = openSet.toArray(arr);
        for (Vertex vertex : arr) {
            Point point = vertex.name;
            if (now != null && !point.equals(labyrinth.getStart()) && !point.equals(labyrinth.getFinish()) && !now.equals(vertex))
                labyrinth.setCell(point, '3');
        }
    }

    public boolean stepFindA(Labyrinth labyrinth) {
        if(isFirstStep) {
            labyrinth.clearLab();
            closeSet.clear();
            openSet.clear();
            openSet.add(new Vertex(labyrinth.getStart(),0,0));
            isFirstStep = false;
            stepFindA(labyrinth);
        }
        printOpenSetVertexes(openSet, labyrinth);
        Vertex node = openSet.peek(); //Берем вершину из очереди
        if (node != null) {
            if (!closeSet.contains(node.name)) // Если еще не исследовали
            {
                if (node.name.equals(labyrinth.getFinish())) {
                    getWay(fromSet, labyrinth);
                    return true;
                }
                openSet.poll();
                closeSet.add(node.name);
                Vector<Vertex> neighbours = getNeighbours(node,labyrinth);
                for (Vertex temp : neighbours) {
                    if ((temp.way) <= fromSet[temp.name.x][temp.name.y].way) {
                        fromSet[temp.name.x][temp.name.y].name = node.name;
                        fromSet[temp.name.x][temp.name.y].way = temp.way;
                        openSet.add(temp);
                    }
                }
            }
            else {
                openSet.poll();
            }
        }
        return false;
    }

    public boolean FindA (Labyrinth labyrinth) {
        while(!openSet.isEmpty()) {
            if(stepFindA(labyrinth))
                return true;
        }
        return false;
    }

    private double Evr(Point a, Point finish) {
        return Math.sqrt(Math.pow(a.x-finish.x, 2)+Math.pow(a.y-finish.y, 2));
    }

    private Vector<Vertex> getNeighbours(Vertex vertex, Labyrinth labyrinth) //Получение соседей вершины
    {
        Vector<Vertex> neighbours = new Vector<>();
        if (labyrinth.checker(vertex.name.x - 1, vertex.name.y)) {
            Point point = new Point((vertex.name.x - 1), vertex.name.y);
            neighbours.add(new Vertex(point,vertex.way+Evr(point,labyrinth.getFinish()) + 1, vertex.way + 1));
        }
        if (labyrinth.checker(vertex.name.x + 1, vertex.name.y)) {
            Point point = new Point((vertex.name.x + 1), vertex.name.y);
            neighbours.add(new Vertex(point,vertex.way + Evr(point,labyrinth.getFinish()) + 1, vertex.way + 1));
        }
        if (labyrinth.checker(vertex.name.x, vertex.name.y - 1)) {
            Point point = new Point((vertex.name.x), vertex.name.y - 1);
            neighbours.add(new Vertex(point,vertex.way + Evr(point,labyrinth.getFinish()) + 1, vertex.way + 1));
        }
        if (labyrinth.checker(vertex.name.x, vertex.name.y + 1)) {
            Point point = new Point((vertex.name.x), vertex.name.y + 1);
            neighbours.add(new Vertex(point,vertex.way + Evr(point,labyrinth.getFinish()) + 1, vertex.way + 1));
        }
        return neighbours;
    }

    private void getWay(Vertex[][] fromSet, Labyrinth labyrinth) // Восстановление пути
    {
        Point curr = labyrinth.getFinish();
        curr = fromSet[curr.x][curr.y].name;
        while(curr != labyrinth.getStart()) {
            labyrinth.setCell(curr,'2');
            curr = fromSet[curr.x][curr.y].name;
        }
    }

    private static Comparator<Vertex> idComparator = (c1, c2) -> (int) (c1.priority - c2.priority);

    public class Vertex {
        Point name;
        double priority; // Приоритет
        double way; // Путь от начальной

        Vertex(Point name, double priority, double way) {
            this.name = name;
            this.priority = priority;
            this.way = way;
        }
    }
}