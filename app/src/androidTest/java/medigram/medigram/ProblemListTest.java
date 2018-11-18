package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class ProblemListTest extends ActivityInstrumentationTestCase2 {
    public ProblemListTest(){
        super(ProblemList.class);
    }

    public void testAddProblem(){
        ProblemList problemList = new ProblemList();
        Date date = new Date();
        Problem problem = new Problem("title", "description", date, "butt");

        problemList.addProblem(problem);

        assertTrue(problemList.problemExist(problem));
    }

    public void testRemoveProblem(){
        ProblemList problemList = new ProblemList();
        Date date = new Date();
        Problem problem = new Problem("title", "description", date, "butt");

        problemList.addProblem(problem);
        problemList.removeProblem(problem);

        assertFalse(problemList.problemExist(problem));
    }

    public void testProblemExist(){
        ProblemList problemList = new ProblemList();
        Date date = new Date();
        Problem problem = new Problem("title", "description", date, "butt");

        problemList.addProblem(problem);

        assertTrue(problemList.problemExist(problem));
    }

    public void testGetIndex(){
        ProblemList problemList = new ProblemList();
        Date date = new Date();
        Problem problem = new Problem("title", "description", date, "butt");

        problemList.addProblem(problem);
        assertEquals(problemList.getIndex(problem), 0);
    }

    public void testGetSize(){
        ProblemList problemList = new ProblemList();
        Date date = new Date();
        Problem problem = new Problem("title", "description", date, "butt");

        problemList.addProblem(problem);
        assertEquals(problemList.getSize(),1);
    }


}
