package ploton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EulerAlgorithmTest {
    public static String[] seq1 = {"Kishinev", "Vyatsk", "Kirov", "Dubrov", "Volgograd", "Ulyanovsk"};
    public static String[] seq2 = {"Adler", "Rybinsk", "Kurgan", "Naryan-Mar", "Rom", "Murmansk", "Konstantinopl"};

    private Map<Character, Node> defaultMapInit() {
        Map<Character, Node> map = new HashMap<>();
        map.put('A', new Node('A', List.of("Adler"), 1, 0, 1));
        map.put('R', new Node('R', List.of("Rybinsk", "Rom"), 2, 2, 4));
        map.put('K', new Node('K', List.of("Kurgan", "Konstantinopl"), 2, 2, 4));
        map.put('N', new Node('N', List.of("Naryan-Mar"), 1, 1, 2));
        map.put('M', new Node('M', List.of("Murmansk"), 1, 1, 2));
        map.put('L', new Node('L', List.of(), 0, 1, 1));
        return map;
    }

    @Test
    public void eulerAlgorithm_getNodeMapFromSeq()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<Character, Node> expected, actual;
        //reflection
        Class<EulerAlgorithm> eulerAlgorithmClass = EulerAlgorithm.class;
        Constructor<EulerAlgorithm> constructor =
                eulerAlgorithmClass.getConstructor(String[].class);
        EulerAlgorithm eulerAlgorithm = constructor.newInstance((Object) seq2);
        Method getNodeMapFromSeq = eulerAlgorithmClass.getDeclaredMethod("getNodeMapFromSeq");
        getNodeMapFromSeq.setAccessible(true);

        //init data
        actual = (Map<Character, Node>) getNodeMapFromSeq.invoke(eulerAlgorithm);
        expected = defaultMapInit();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void eulerAlgorithm_nodeInit()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Node expected = new Node('K', List.of("Kurgan", "Konstantinopl"), 2, 2, 4);
        Node actual;

        //reflection
        Class<EulerAlgorithm> eulerAlgorithmClass = EulerAlgorithm.class;
        Constructor<EulerAlgorithm> constructor = eulerAlgorithmClass.getConstructor(String[].class);
        Method nodeInit = eulerAlgorithmClass.getDeclaredMethod("nodeInit", char.class, String[].class);
        nodeInit.setAccessible(true);
        EulerAlgorithm reflection = constructor.newInstance((Object) seq2);
        actual = (Node) nodeInit.invoke(reflection, 'K', seq2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void eulerAlgorithm_typeInit()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GraphType expected = GraphType.EULER_PATH;
        GraphType actual;

        Class<EulerAlgorithm> eulerAlgorithmClass = EulerAlgorithm.class;
        Constructor<EulerAlgorithm> constructor = eulerAlgorithmClass.getConstructor(String[].class);
        EulerAlgorithm reflection = constructor.newInstance((Object) seq2);
        Method typeInit = eulerAlgorithmClass.getDeclaredMethod("typeInit");
        typeInit.setAccessible(true);

        actual = (GraphType) typeInit.invoke(reflection);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void eulerAlgorithm_getEulerPath() {
        List<String> expected, actual;
        EulerAlgorithm eulerAlgorithm = new EulerAlgorithm(seq2);
        actual = eulerAlgorithm.getEulerPath();

        expected = new ArrayList<>();
        expected.add("Adler");
        expected.add("Rybinsk");
        expected.add("Kurgan");
        expected.add("Naryan-Mar");
        expected.add("Rom");
        expected.add("Murmansk");
        expected.add("Konstantinopl");

        Assertions.assertEquals(expected,actual);
    }
}
