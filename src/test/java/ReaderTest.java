import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ReaderTest{
    private Reader sut;
    private File testSubmittedReport;
    private File testClassFile;
    private File test123ClassList;
    private Map<String, LocalTime> testAttendanceMap;
    private List<String> testClassList;

    @Before
    public void setUp() {
        sut = new Reader();
        testSubmittedReport = new File("src/test/test-resources/Test-Submitted-Report.csv");
        testClassFile = new File("src/test/test-resources/Test-Full-Class-List.csv");
        test123ClassList = new File("src/test/test-resources/Test-123-Class-List.csv");

        testClassList = new ArrayList<>();
        testClassList.add("AbsentlyLate Student");
        testClassList.add("Absent Student");
        testClassList.add("Tardy Student");
        testClassList.add("Present Student");

        testAttendanceMap = new HashMap<>();
        testAttendanceMap.put("AbsentlyLate Student", LocalTime.of(9,0,0));
        testAttendanceMap.put("Tardy Student", LocalTime.of(8,30,0));
        testAttendanceMap.put("Present Student", LocalTime.of(8,0,0));
        testAttendanceMap.put("New StudentOnTime", LocalTime.of(8,0,0));
    }

    @Test
    public void populateAttendanceList_returns_list_123() throws IOException {
        ArrayList<String> result = new ArrayList<>(sut.populateAttendanceList(test123ClassList));
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");

        Assert.assertEquals(expected, result);
    }

    @Test
    public void givenTestClassFile_setupReturns_attendanceMapMatching_testAttendanceMap() throws IOException{
        Map<String, LocalTime> result = new HashMap<>(sut.readSubmittedReport(testSubmittedReport));
        Assert.assertEquals(testAttendanceMap, result);
    }


}