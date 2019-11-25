import org.jpl7.Query;
import org.jpl7.Variable;

public class Main {
    public static void main(String[] args) {
        Query q1 = new Query("consult('D:/Github/CPSC312-2019WT1-Project2/cpsc312 project2.pl')");
        System.out.println(q1.hasSolution());
        Variable S = new Variable("S");
        Query q = new Query("solveOnce([(2,1),(7,2),(4,3),(8,4),(9,5),(1,6),(3,7),(6,8),(5,9),(1,10),(3,11),(8,12),(5,13),(2,14),(6,15),(4,16),(9,17),(6,19),(5,20),(9,21),(4,22),(7,23),(3,24),(2,25),(8,26),(1,27),(3,28),(2,29),(1,30),(9,31),(6,32),(4,33),(7,34),(5,35),(8,36),(9,37),(8,38),(5,39),(1,40),(3,41),(7,42),(6,43),(4,44),(2,45),(7,46),(4,47),(6,48),(2,49),(8,50),(5,51),(9,52),(1,53),(3,54),(4,55),(6,56),(2,57),(7,58),(5,59),(8,60),(1,61),(3,62),(9,63),(5,64),(9,65),(3,66),(6,67),(1,68),(2,69),(8,70),(7,71),(4,72),(8,73),(1,74),(7,75),(3,76),(4,77),(9,78),(5,79)], S)");
        while ( q.hasMoreSolutions() ){
            java.util.Map solution = q.nextSolution();
            System.out.println( "S = " + solution.get("S").toString().substring(3));
        }
    }
}
