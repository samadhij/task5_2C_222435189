package sit707_week5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {

	private static WeatherController wController;
	private static int nHours;
    private static double[] hourlyTemperatures;

    // Executed once before any test method in the class
    @BeforeClass
    public static void setUp() {
    	// Initialize WeatherController instance for all the tests
        wController = WeatherController.getInstance();
        
        // Retrieve number of hours
        nHours = wController.getTotalHours();
        
        // Store hourly temperatures locally
        hourlyTemperatures = new double[nHours];
        for (int i = 0; i < nHours; i++) {
            hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);
        }
    }
    
    // Executed once after all test methods in the class
    @AfterClass
    public static void tearDown() {
    	// Close WeatherController instance
        wController.close();
    }
    
	@Test
	public void testStudentIdentity() {
		String studentId = "s222435189";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Samadhi Jayasinghe";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	// Test method for checking minimum temperature
	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		System.out.println("Temperature min: " + wController.getTemperatureMinFromCache());
		double minTemperature = 1000;
		// Calculate minimum temperature from locally stored hourly temperatures
		for (double temperature : hourlyTemperatures) {
            if (temperature < minTemperature) {
                minTemperature = temperature;
            }
        }
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
			
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		System.out.println("Temperature max: " + wController.getTemperatureMaxFromCache());
		double maxTemperature = -1;
		// Calculate maximum temperature from locally stored hourly temperatures
        for (double temperature : hourlyTemperatures) {
            if (temperature > maxTemperature) {
                maxTemperature = temperature;
            }
        }
		
		// Should be equal to the max value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
		
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");
		System.out.println("Temperature avg: " + wController.getTemperatureAverageFromCache());
		double sumTemp = 0;
        // Calculate sum of temperatures from locally stored hourly temperatures
        for (double temperature : hourlyTemperatures) {
            sumTemp += temperature;
        }
		double averageTemp = sumTemp / nHours;
		
		// Should be equal to the average value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
	}
	
	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
		System.out.println("+++ testTemperaturePersist +++");
		
		// Persist temperature data with hour 10 and temperature 19.5
		String persistTime = wController.persistTemperature(10, 19.5);
		
		// Get the current time and format it using SimpleDateFormat
		String now = new SimpleDateFormat("H:m:s").format(new Date());
		
		System.out.println("Persist time: " + persistTime + ", now: " + now);
		
	    // Assert that the time difference between persisted time and current time is within tolerance
	    // Allow a tolerance of 2 seconds
		Assert.assertTrue("Time difference is more than 2 seconds.",Math.abs(parseTime(persistTime) - parseTime(now)) <= 2000);

	}
	
	// Helper method to parse time string to milliseconds since epoch
	private long parseTime(String time) {
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
	        Date date = sdf.parse(time);
	        return date.getTime();
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
}
