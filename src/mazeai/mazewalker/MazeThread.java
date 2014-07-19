package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014 at 12:13.
 */
public class MazeThread extends Thread {
    //    private Link link;
    private Vertex parent;
    private Vertex thisVertex;

    private static Graph graph;
    private static List<MazeThread> threads = new ArrayList<MazeThread>();

    private boolean spread;
    private List<MazeThread> children;

    public MazeThread(Vertex parent, Vertex thisVertex) {
//        this.link = link;
        this.parent = parent;
        this.thisVertex = thisVertex;
//        this.thisVertex.setThread(this);
        threads.add(this);
        spread = true;
        children = new ArrayList<MazeThread>();
    }

    public static void setGraph(Graph g) {
        graph = g;
    }

    public void stopFurtherSpreading() {
        spread = false;
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

        String calculatedPathToFinish = parent.getPathToVertex(indexThis) + parent.getPathToVertex(indexFinish);
        return calculatedPathToFinish;
    }

    // Called when recalculation required. When parent's path has changed
    public void reprocessChildren() {
        System.out.println("Relaunching children for a dead thread");

        // Clearing all previous children and preventing them from spreading
        for (MazeThread child : children) {
            child.stopFurtherSpreading();
        }
        children = new ArrayList<MazeThread>();

        // Launching new children, with proper parent path
        launchChildren();
    }


    private void launchChildren() {
        for (int i = 0; i < thisVertex.getVerticesAmount(); i++) {
            if ((thisVertex.getLink(i).getVertex() != parent)
                    && (thisVertex.getLink(i).getVertex() != graph.getFinish())) {

                MazeThread childThread = new MazeThread(thisVertex, thisVertex.getLink(i).getVertex());
                children.add(childThread);
                childThread.start();
                try {
                    childThread.join(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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

                // If there is a Thread operating further with old, wrong path, relaunch it!
                if (thisVertex.getThread() != null) {
                    thisVertex.getThread().stopFurtherSpreading(); // If we still can catch it.....
                }
            }

            // TERMINATE
            return; // This also prevents cycling

        } else {
            // Such path didn't exist. We are the first

            thisVertex.addLink(new Link(graph.getFinish(), calculatedPathToFinish));
        }

        // Mark that we've been here, so that later Threads can call us for relaunching our children
        // We do it here because before we needed to know if there was smbd else
        thisVertex.setThread(this);

        if (thisVertex.equals(graph.getStart())) {
            // TERMINATE
            return;
        }


        if (spread) {
            // If no termination then continue spreading
            launchChildren();
        }

        // TERMINATE
        return;
    }
}