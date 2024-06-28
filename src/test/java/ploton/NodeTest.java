package ploton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NodeTest {
    static char data;
    static List<String> edges;

    @BeforeAll
    public static void paramInit() {
        data = 'A';
        edges = new ArrayList<>();
        edges.add("Anapa");
        edges.add("Adler");
    }

    @Test
    public void NodeConstructorTest()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Node expected;
        Node actual;

        //init actual
        char actualChar = 'A';

        List<String> actualList = new ArrayList<>();
        actualList.add("Anapa");
        actualList.add("Adler");

        actual = new Node(actualChar, actualList);

        //init expected with reflection

        Class nodeClass = Node.class;
        Constructor<Node> nodeConstructor = nodeClass.getConstructor(char.class, List.class);
        expected = nodeConstructor.newInstance(data, edges);

        System.out.println("Actual Node: " + actual);
        System.out.println("Expected Node: " + expected);
        Assertions.assertEquals(expected,actual);
    }

}
