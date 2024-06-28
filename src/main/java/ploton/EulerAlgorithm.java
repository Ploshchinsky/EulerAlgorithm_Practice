package ploton;

import java.util.*;

public class EulerAlgorithm {
    private Map<Character, Node> nodeMap;
    private String[] sequence;
    private GraphType type;

    public EulerAlgorithm(String[] sequence) {
        this.sequence = sequence;
        nodeMap = getNodeMapFromSeq();
        this.type = typeInit();
    }

    public List<String> getEulerPath() {
        List<String> edgesPath = new ArrayList<>();
        List<Node> nodePath = new ArrayList<>();

        Stack<Node> nodeStack = new Stack<>();
        Stack<String> edgeStack = new Stack<>();

        //Find the Start Node and put it in the Stack
        Node startNode = getStartNode();
        nodeStack.push(startNode);
        while (!nodeStack.empty()) {
            //Get all edges from Start Node
            List<String> currentList = startNode.getEdges();
            if (!currentList.isEmpty()) {
                //Take any Edge and remove it from the List => next adding it to the Edge Stack
                String currentString = currentList.get(0);
                currentList.remove(0);
                startNode.setEdges(currentList);
                edgeStack.push(currentString);
                //Find the next Node by the last letter of the current Edge
                char lastLetter = getLastLetter(currentString);
                startNode = nodeMap.get(lastLetter);
                //Added new Node to Node Stack
                nodeStack.push(startNode);
            }
            nodePath.add(nodeStack.pop());
            if (!edgeStack.empty()) edgesPath.add(edgeStack.pop());
        }
        return edgesPath;
    }

    private GraphType typeInit() {
        List<Node> oddNodes = nodeMap.values().stream().filter(node -> node.getGrade() % 2 != 0).toList();
        List<Node> evenNodes = nodeMap.values().stream().filter(node -> node.getGrade() % 2 == 0).toList();

        if (evenNodes.size() == nodeMap.values().size()) {
            return GraphType.EULER_CYCLE;
        } else if (oddNodes.size() == 2) {
            for (Node node : oddNodes) {
                int outputs = oddNodes.get(0).getOutputEdges();
                int inputs = oddNodes.get(0).getInputEdges();
                if (outputs > inputs && (Math.abs(outputs - inputs) == 1)) {
                    return GraphType.EULER_PATH;
                }
            }
        }
        return GraphType.OTHER;
    }

    private Node getStartNode() {
        if (type == GraphType.EULER_PATH) {
            List<Node> pretends = nodeMap.values().stream().filter(n -> n.getGrade() % 2 != 0).toList();
            for (Node node : pretends) {
                if (node.getOutputEdges() > node.getInputEdges()) {
                    return node;
                }
            }
        }
        return nodeMap.values().stream().findAny().get();
    }

    private Map<Character, Node> getNodeMapFromSeq() {
        Map<Character, Node> map = new HashMap<>();
        for (String s : sequence) {
            char firstLetter = getFirstLetter(s);
            char lastLetter = getLastLetter(s);
            Node node = nodeInit(firstLetter, sequence);
            map.putIfAbsent(firstLetter, node);

            node = nodeInit(lastLetter, sequence);
            map.putIfAbsent(lastLetter, node);
        }
        return map;
    }

    private Node nodeInit(char data, String[] sequence) {
        Node node;
        int inputsCount, outputsCount, grade;
        List<String> edges;

        edges = new ArrayList<>(Arrays.stream(sequence)
                .filter(s -> s.toUpperCase().charAt(0) == data).toList());
        outputsCount = edges == null ? 0 : edges.size();
        inputsCount = (int) Arrays.stream(sequence)
                .filter(s -> s.toUpperCase().charAt(s.length() - 1) == data)
                .count();
        grade = inputsCount + outputsCount;
        node = new Node(data, edges, outputsCount, inputsCount, grade);
        return node;
    }

    private char getLastLetter(String word) {
        return word.toUpperCase().charAt(word.length() - 1);
    }

    private char getFirstLetter(String word) {
        return word.toUpperCase().charAt(0);
    }
}
