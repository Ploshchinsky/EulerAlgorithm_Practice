package ploton;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Node {
    private final Character data;
    private List<String> edges;
    private int outputEdges, inputEdges, grade;

    public Node(char data, List<String> edges) {
        this.data = data;
        this.edges = edges;
    }
}
