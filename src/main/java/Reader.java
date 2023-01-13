import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {

    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    final static LocalTime TARDY_LIMIT = LocalTime.parse("08:15:00");
    final static LocalTime ABSENT_LIMIT = LocalTime.parse("09:00:00");
    //TODO Enable reading a text file for this list instead of hardcoding it
    final List<String> MASTER_ATTENDANCE_LIST = new ArrayList<>() {{
        add("Sample Student");
        add("Sample Student");
        add("Sample Student");
        add("Sample Student");
        add("Sample Student");
        add("Sample Student");
        add("Absent Student");
    }};

    // Create BufferedReader to scan the text file & populate an ArrayList with those names
    public ArrayList<String> populateAttendanceList(File attendanceFile) throws IOException {
        ArrayList<String> fullAttendanceList = new ArrayList<>();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(attendanceFile))) {
            while ((line = br.readLine()) != null) {
                fullAttendanceList.add(line);
            }
        }
        return fullAttendanceList;
    }


    // Writes user-friendly string to display
    // TODO: Increase font size globally, auto wrap, auto center in screen
    public String resultWriter(File inputFile) throws IOException {
        Map<String, List<String>> resultMap = resultChecker(setup(inputFile));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : resultMap.entrySet()) {
            String groupName = entry.getKey();
            List<String> studentNames = entry.getValue();
            sb.append(groupName).append(" :  ");
            for (String name: studentNames) {
                sb.append(name).append(", ");
            } // Remove last comma & start new lines
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
    // Interprets CSV & populates map with attendee name/join time
    public Map<String, LocalTime> setup(File inputFile) throws IOException {
        Map<String, LocalTime> attendanceMap = new HashMap<>();
        // Split each line into array of strings
        String line = "";
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            // Ignore the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(splitBy);
//                LocalTime joinTime = LocalTime.parse(values[2], FORMATTER);
                LocalDateTime joinTimeRaw = LocalDateTime.parse(values[2], FORMATTER);
                LocalTime joinTime = joinTimeRaw.toLocalTime();
                attendanceMap.put(values[0], joinTime);
            }
        } catch (IOException e) {
            //TODO Change this to a proper error message
            e.printStackTrace();
        }
        return attendanceMap;
    }
    // Does logic & returns map with results
    public Map<String, List<String>> resultChecker(Map<String, LocalTime> attendanceMap) throws IOException {
        List<String> absentStudents = new ArrayList<>();
        List<String> tardyStudents = new ArrayList<>();
        List<String> presentStudents = new ArrayList<>();
        List<String> inputStudentList = new ArrayList<>(MASTER_ATTENDANCE_LIST);

        for (Map.Entry<String, LocalTime> entry : attendanceMap.entrySet()) {
            String studentName = entry.getKey();
            LocalTime joinTime = entry.getValue();
            if (joinTime.compareTo(ABSENT_LIMIT) > 0) {
                absentStudents.add(studentName);
                inputStudentList.remove(studentName);
            } else if (joinTime.compareTo(ABSENT_LIMIT) < 0 && joinTime.compareTo(TARDY_LIMIT) >= 0) {
                tardyStudents.add(studentName);
                inputStudentList.remove(studentName);
            } else {
                presentStudents.add(studentName);
                inputStudentList.remove(studentName);
            }
        };
        absentStudents.addAll(inputStudentList);

        Map<String, List<String>> result = new HashMap<>();
        result.put("Absent", absentStudents);
        result.put("Tardy", tardyStudents);
        result.put("Present", presentStudents);
        return result;
    }
}
