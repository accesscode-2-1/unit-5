package nyc.c4q.jrod.current.java;

import java.text.NumberFormat;
import java.util.*;

/*
Count how many incomplete homeworks exits in the dataset? (score = 0)
What percentage of the homeworks has been completed?
What homework has the highest class average?
What homework has the most incompletes?
What homework has the lowest class average?
What is the overall high score?
What percentage of the total homeworks have the highest score?
Output the (percent (10%, 20% … 90%), count) pairs for all the homeworks to create a histogram.
 */

public class Grades {
  private static final NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.getDefault());

  public static void main(String[] args) {
    Map<String, List<Integer>> grades = generateTestGrades();

    System.out.println("# of incomplete hws: " + countIncompleteHomeworks(grades));
    System.out.println("% of completed hws: " + percentFormatter.format(calculateCompletedPercentage(grades)));
    System.out.println("hw with highest class average: " + findHomeworkWithHighestClassAverage(grades));
    System.out.println("hw with most incompletes: " + findHomeworkWithMostIncompletes(grades));
    System.out.println("hw with lowest class average: " + findHomeworkWithLowestClassAverage(grades));
    System.out.println("overall high score: " + findHighestScore(grades));
    System.out.println("% of hws with highest score: " + percentFormatter.format(calculateHighScorePercentage(grades)));
    System.out.println("histogram: " + calculateHistogram(grades));
  }

  private static int countIncompleteHomeworks(Map<String, List<Integer>> grades) {
    int count = 0;
    for (List<Integer> studentGrades : grades.values()) {
      for (int grade : studentGrades) {
        if (grade == 0) count++;
      }
    }
    return count;
  }

  private static double calculateCompletedPercentage(Map<String, List<Integer>> grades) {
    int completed = 0;
    int total = 0;
    for (List<Integer> studentGrades : grades.values()) {
      for (int grade : studentGrades) {
        total++;
        if (grade != 0) {
          completed++;
        }
      }
    }
    return ((double) completed) / total;
  }

  private static int findHomeworkWithHighestClassAverage(Map<String, List<Integer>> grades) {
    int highSum = 0;
    int highIndex = 0;

    assertEachStudentHasSameNumberOfGrades(grades);
    int numHomeworks = grades.values().iterator().next().size();

    for (int homeworkIndex = 0; homeworkIndex < numHomeworks; homeworkIndex++) {
      int sum = 0;
      for (String student : grades.keySet()) {
        List<Integer> studentGrades = grades.get(student);
        int grade = studentGrades.get(homeworkIndex);
        sum += grade;
      }
      if (sum > highSum) {
        highSum = sum;
        highIndex = homeworkIndex;
      }
    }

    return highIndex;
  }

  private static int findHomeworkWithMostIncompletes(Map<String, List<Integer>> grades) {
    int highSum = 0;
    int highIndex = 0;

    assertEachStudentHasSameNumberOfGrades(grades);
    int numHomeworks = grades.values().iterator().next().size();

    for (int homeworkIndex = 0; homeworkIndex < numHomeworks; homeworkIndex++) {
      int numIncompletes = 0;
      for (String student : grades.keySet()) {
        List<Integer> studentGrades = grades.get(student);
        int grade = studentGrades.get(homeworkIndex);
        if (grade == 0) {
          numIncompletes++;
        }
      }
      if (numIncompletes > highSum) {
        highSum = numIncompletes;
        highIndex = homeworkIndex;
      }
    }

    return highIndex;
  }

  private static int findHomeworkWithLowestClassAverage(Map<String, List<Integer>> grades) {
    int lowSum = 0;
    int lowIndex = 0;

    assertEachStudentHasSameNumberOfGrades(grades);
    int numHomeworks = grades.values().iterator().next().size();

    for (int homeworkIndex = 0; homeworkIndex < numHomeworks; homeworkIndex++) {
      int sum = 0;
      for (String student : grades.keySet()) {
        List<Integer> studentGrades = grades.get(student);
        int grade = studentGrades.get(homeworkIndex);
        sum += grade;
      }
      if (sum < lowSum) {
        lowSum = sum;
        lowIndex = homeworkIndex;
      }
    }

    return lowIndex;
  }

  private static int findHighestScore(Map<String, List<Integer>> grades) {
    int highScore = 0;
    for (List<Integer> studentGrades : grades.values()) {
      for (int grade : studentGrades) {
        if (grade > highScore) {
          highScore = grade;
        }
      }
    }
    return highScore;
  }

  private static void assertEachStudentHasSameNumberOfGrades(Map<String, List<Integer>> grades) {
    int count = -1;
    for (String student : grades.keySet()) {
      List<Integer> studentGrades = grades.get(student);
      if (count == -1) {
        count = studentGrades.size();
      } else if (count != studentGrades.size()) {
        throw new IllegalArgumentException("Each student must have the same number of homeworks");
      }
    }
  }

  private static double calculateHighScorePercentage(Map<String, List<Integer>> grades) {
    int highScore = findHighestScore(grades);
    int total = 0;
    int count = 0;

    for (List<Integer> studentGrades : grades.values()) {
      for (int grade : studentGrades) {
        total++;
        if (grade == highScore) {
          count++;
        }
      }
    }
    return ((double) count) / total;
  }

  private static Map<Integer, Integer> calculateHistogram(Map<String, List<Integer>> grades) {
    // key = 10,20,.....,90
    // grade of 10 to 19 == 10%
    // grade of 20 to 29 == 20%
    // etc..
    Map<Integer, Integer> histogram = new HashMap<>();
    for (List<Integer> studentGrades : grades.values()) {
      for (int grade : studentGrades) {
        int interval = (grade / 10) * 10;

        // out of range, ignore
        if (interval < 10 && interval > 90) {
          continue;
        }

        Integer count = histogram.get(interval);
        if (count == null) {
          count = 0;
        }
        histogram.put(interval, ++count);
      }
    }

    return histogram;
  }

  private static Map<String, List<Integer>> generateTestGrades() {
    Map<String, List<Integer>> grades = new HashMap<>();
    grades.put("Alice", Arrays.asList(80, 90, 89, 92, 99));
    grades.put("Bob", Arrays.asList(90, 90, 0, 91, 72));
    grades.put("Charlyn", Arrays.asList(90, 81, 63, 88, 0));
    grades.put("Daisy", Arrays.asList(60, 90, 71, 89, 20));
    grades.put("Edgar", Arrays.asList(70, 99, 75, 83, 40));
    return grades;
  }
}
