package utils;

import java.lang.reflect.Constructor;

public class RelectionTest {

	public RelectionTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
        String sFieldType= "Integer";
		Class clazz;
		try {
			  if(!sFieldType.contains("."))
		            sFieldType="java.lang."+sFieldType;


		             clazz = Class.forName(sFieldType);


		            Constructor<?> ctor = clazz.getConstructor(java.lang.String.class);
		              Object fieldValue="999";
					Object object = ctor.newInstance(new Object[] { fieldValue });

			
			System.out.println(object);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


     
		// TODO Auto-generated method stub

	}

}
