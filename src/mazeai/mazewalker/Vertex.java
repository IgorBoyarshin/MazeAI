package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014.
 */
public class Vertex {
    private List<Vertex> links;

    public Vertex() {
        links = new ArrayList<Vertex>();
    }

    public void addChild(Vertex vertex) {
        links.add(vertex);
    }

    public Vertex getChild(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index);
        } else {
            return null;
        }
    }

    public int getChildrenAmount() {
        return links.size();
    }
//
//    public String getPathToVertex(int index) {
//        if ((index >= 0) && (index < links.size())) {
//            return links.get(index).getPath();
//        } else {
//            return null;
//        }
//    }
//
//    public void setPathToVertex(int index, String path) {
//        if ((index >= 0) && (index < links.size())) {
//            links.get(index).setPath(path);
//        }
//    }
//
//    public Link getLink(int index) {
//        if ((index >= 0) && (index < links.size())) {
//            return links.get(index);
//        } else {
//            return null;
//        }
//    }
//
//    public void addLink(Link link) {
//        links.add(link);
//    }
}
