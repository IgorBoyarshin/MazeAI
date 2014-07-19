package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014.
 */
public class MazeWalkerVert {
    private List<MazeWalkerLink> links;

    public MazeWalkerVert() {
        links = new ArrayList<MazeWalkerLink>();
    }

    public void addVert(MazeWalkerVert vert, String path) {
        links.add(new MazeWalkerLink(vert, path));
    }

    public int getVertsAmount() {
        return links.size();
    }

    public MazeWalkerVert getVert(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index).getVert();
        } else {
            return null;
        }
    }

    public String getPathToVert(int index) {
        if ((index >= 0) && (index < links.size())) {
            return links.get(index).getPath();
        } else {
            return null;
        }
    }
}
