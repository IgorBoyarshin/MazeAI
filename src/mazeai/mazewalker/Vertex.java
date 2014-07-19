package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014.
 */
public class Vertex {
    private List<Link> links;

    public Vertex() {
        links = new ArrayList<Link>();
    }

    public void addLink(Vertex vertex, String path) {
        links.add(new Link(vertex, path));
    }

    public int getVerticesAmount() {
        return links.size();
    }

    public Vertex getVertex(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index).getVertex();
        } else {
            return null;
        }
    }

    public String getPathToVertex(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index).getPath();
        } else {
            return null;
        }
    }

    public void setPathToVertex(int index, String path) {
        if ((index >= 0) && (index < links.size())) {
            links.get(index).setPath(path);
        }
    }

    public Link getLink(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index);
        } else {
            return null;
        }
    }

    public void addLink(Link link) {
        links.add(link);
    }
}
