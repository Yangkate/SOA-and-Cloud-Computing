/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.jyu.it.ties456.week38.Main;

import fi.jyu.it.ties456.week38.services.course.CourseIS;
import fi.jyu.it.ties456.week38.services.course.CourseISService;
import fi.jyu.it.ties456.week38.services.course.NoSuchTeacherException_Exception;
import fi.jyu.it.ties456.week38.services.teacher.TeacherRegistry;
import fi.jyu.it.ties456.week38.services.teacher.TeacherRegistryService;
import fi.jyu.it.ties456.week38.services.teacher.TeacherType;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws NoSuchTeacherException_Exception{
        
        TeacherRegistryService connect = new TeacherRegistryService();
        TeacherRegistry teacher = connect.getTeacherRegistryPort();
        CourseISService connectCourse = new CourseISService();
        CourseIS course = connectCourse.getCourseISPort();
        int i; 
        Scanner inputchoice = new Scanner(System.in);
        Scanner cin= new Scanner(System.in);
        
      do
        {
        System.out.println("0: Quit the application");
        System.out.println("1: Search the teacher Info");
        System.out.println("2: Create the Course");
        i = inputchoice.nextInt();
        switch(i)
        {
            case 0:
                System.out.println("quit the application");
                break;
            case 1:
                System.out.println("Input search info of teacher：");
                String searchString = cin.nextLine();
                JSONObject result = searchTeacher(teacher,searchString);
                if(result == null)
                	System.out.println("No person found");
                else 
                	System.out.println(result);
                break;
            case 2:
                createCourse(teacher, course);
                break;
           default: 
        	   System.err.println("Not a valid choice");
                
        }
         }while(i!=0);
      }
    
    public static JSONObject searchTeacher(TeacherRegistry teacherReg, String query){  
    	JSONObject toReturn = null;
    	if(teacherReg.searchForPerson(query).size() != 0){
    		toReturn = new JSONObject();
    		JSONArray response = new JSONArray();
	    	for (int k = 0; k < teacherReg.searchForPerson(query).size(); k++) {
	    		TeacherType person = teacherReg.searchForPerson(query).get(k);
	    					JSONObject teacher = new JSONObject();
	    					teacher.put("teacher", new JSONObject()
		                    		.put("id", person.getID())
		                    		.put("email", person.getEmail())
		                    		.put("firstname", person.getFirstname())
		                    		.put("surname", person.getSurname())
		                    		);	           
	    					response.put(teacher);
	        }  toReturn.put("response", response);
    	}
    	return toReturn;
    }
    
    public static void createCourse(TeacherRegistry teacher, CourseIS course) throws NoSuchTeacherException_Exception{
         Scanner inputID=new Scanner(System.in);
         Scanner cinName=new Scanner(System.in);
         Scanner cinID=new Scanner(System.in);
         Scanner cinCredit=new Scanner(System.in);
         Scanner cinDP=new Scanner(System.in);
    	System.out.println("Course Name");
        String cName = cinName.nextLine();
        System.out.println("Search Teacher ID");
        String SID = cinID.nextLine();
        System.out.println(teacher.searchForPerson(SID).get(0).getID());
        System.out.println("Input Teacher ID");
        String ID = inputID.nextLine();
        System.out.println("Course Credit");
        int credit = 0;
		try {
			credit = cinCredit.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("Course credit must be an int");
			main(null);
		}
        System.out.println("Course Description");
        String description = cinDP.nextLine();
        System.out.println(course.createCourse(cName, ID, credit, description));
    }


}

        
       
                
    
    


