package mazeai.mazewalker;

/**
 * Created by Igor on 19.07.2014 at 12:13.
 */
public class MazeThread extends Thread {
    //    private Link link;
    private Vertex parent;
    private Vertex thisVertex;

    private static Graph graph;

    public MazeThread(Vertex parent, Vertex thisVertex) {
//        this.link = link;
        this.parent = parent;
        this.thisVertex = thisVertex;
    }

    public static void setGraph(Graph g) {
        graph = g;
    }

    private String calcPathToFinish() {
        int index = 0;
        int indexThis = -1;
        int indexFinish = -1;
        while ((indexFinish == -1) || (indexThis == -1)) {
            if (index >= parent.getVerticesAmount()) {
                System.out.println("PARENT DOESN'T CONTAIN FINISH OR THIS_VERTEX");
                return null;
            }

            Vertex v = parent.getVertex(index);
            if (v.equals(graph.getFinish())) {
                indexFinish = index;
            } else if (v.equals(thisVertex)) {
                indexThis = index;
            }

            index++;
        }
        // Found needed Vertices in Parent

        String calculatedPathToFinish = parent.getPathToVertex(indexFinish) + parent.getPathToVertex(indexThis);
        return calculatedPathToFinish;
    }

    /*
        Thread created.
        Calc my path to Finish by adding parent's path with my path to parent.
        Check table for existing path from me to Finish. If such exists,
        then {
            if (mine is shorter) then replace path in table with mine
            else do nothing.

            TERMINATE;
        } else {
            create such path, assigning it's value to mine calculated previously path.
        }

        If I'm operating with Start vertex then TERMINATE.
        Now we can produce children
    */
    @Override
    public void run() {

        String calculatedPathToFinish = calcPathToFinish();

        // Shall check now whether path to Finish for this Vertex already existed
        int index = 0;
        Vertex f = thisVertex.getVertex(index);
        while ((index < thisVertex.getVerticesAmount()-1) && (!f.equals(graph.getFinish()))) {
            index++;
            f = thisVertex.getVertex(index);
        }
        if (f.equals(graph.getFinish())) {
            // We found it! Such path already existed

            if (thisVertex.getPathToVertex(index).length() > calculatedPathToFinish.length()) {
                // Our path is shorter

                thisVertex.setPathToVertex(index, calculatedPathToFinish);
            }

            // TODO: check for shortest, as described at the bottom.
            // TERMINATE
            return;

        } else {
            // Such path didn't exist. We are the first

            thisVertex.addLink(new Link(graph.getFinish(), calculatedPathToFinish));
        }

        if (thisVertex.equals(graph.getStart())) {
            // TERMINATE
            return;
        }

        // TODO: why do I need to wait for children??

        // If no termination then continue spreading
        for (int i = 0; i < thisVertex.getVerticesAmount(); i++) {
            if (thisVertex.getLink(i).getVertex() != parent) {
                MazeThread childThread = new MazeThread(thisVertex, thisVertex.getLink(i).getVertex());
                childThread.start();
                try {
                    childThread.join(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/*
    TODO:
    What if I came, assigned new shorter path, but the previous Thread has already gone with old, wrong path.
    Shortest path is now not guaranteed!!!
 */