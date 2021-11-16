package finalexam;
import GenCol.*;

public class job extends entity
{   
	int _option;
	char _plane;
	int _age;
	char _meal;
	
	public job(String name, int option, char plane, int age, char meal)
	{  
		super(name);
		_option = option;
		_plane = plane;
		_age = age;
		_meal = meal;
	}
	
}
