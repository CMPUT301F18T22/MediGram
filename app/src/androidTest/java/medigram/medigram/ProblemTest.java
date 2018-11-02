package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class ProblemTest extends ActivityInstrumentationTestCase2 {
    public ProblemTest(){
        super(Problem.class);
    }

    /*
     * Tests for Setters
     */
    public void testSetTitle(){
        String title = "titleful";
        Problem problem = new Problem(title, "null", new Date(), "32");

        problem.setProblemTitle("nottitleful");

        assertEquals(problem.getProblemTitle(), "nottitleful");
    }

    public void testSetDescription(){
        String description = "Lorem ipsum dolor sit amet";
        Problem problem = new Problem("null", description, new Date(), "32");

        problem.setDescription("shorter");

        assertEquals(problem.getDescription(), "shorter");
    }

    public void testSetBodyLocation(){
        String bodylocation = "10";
        Problem problem = new Problem("null", "Lorem ipsum dolor sit amet", new Date(), bodylocation);

        problem.setBodyLocation("33");

        assertEquals(problem.getBodyLocation(), "33");
    }

    /*
    * Tests for Getters
    */
    public void testGetTitle(){
        String title = "titleful";
        Problem problem = new Problem(title, "null", new Date(), "32");

        assertEquals(problem.getProblemTitle(), title);
    }

    public void testGetDescription(){
        String description = "Lorem ipsum dolor sit amet";
        Problem problem = new Problem("null", description, new Date(), "32");

        assertEquals(problem.getDescription(), description);
    }

    public void testGetDateStarted(){
        Date date = new Date();
        Problem problem = new Problem("null", "Lorem ipsum dolor sit amet", date, "32");

        assertEquals(problem.getDateStarted().toString(), date.toString());
    }

    public void testGetBodyLocation(){
        String bodylocation = "10";
        Problem problem = new Problem("null", "Lorem ipsum dolor sit amet", new Date(), bodylocation);

        assertEquals(problem.getBodyLocation(), bodylocation);
    }

    /* Stub for testing future implementation */
    public void testGetBodyLocationPhotos(){}
}
