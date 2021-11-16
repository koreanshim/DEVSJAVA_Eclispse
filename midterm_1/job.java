package midterm_1;
import GenCol.*;

public class job extends entity
{   
	int _num;
	
	public job(String name, int num)
	{  
		super(name);  
		
		_num = num; //보내고 싶은 자료 추가
	}
	
}
