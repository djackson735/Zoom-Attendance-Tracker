import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReaderTest{
    private Reader sut;
    private File testFile;
    private ArrayList<String> expectedList;

    @Before
    public void setUp() {
        sut = new Reader();
        testFile = new File("src/test/test-resources/Test-File.txt");
        expectedList = new ArrayList<>();
        expectedList.add("1");
        expectedList.add("2");
        expectedList.add("3");
    }

    @Test
    public void populateAttendanceList_returns_list_123() throws IOException {
        ArrayList<String> result = new ArrayList<>(sut.populateAttendanceList(testFile));
        assertEquals(expectedList, result);
    }

}