package Entities;

/**
 * @author IdoKalir
 *
 */
public class Product 
{
		public String p_id;
		public String p_Name;
		public String p_type;
		public Product()
		/**
		 * 
		 */
		{
			p_id = "";
			p_Name = "";
			p_type = "";
		}
		/**
		 * 
		 * @param i
		 * @param Name
		 * @param type
		 */
		public Product(String i,String Name ,String type)
		{
			p_id = i;
			p_Name = Name;
			p_type = type;
		}
		public String get_p_id()
		{
			return this.p_id;
		}
		public String get_p_Name()
		{
			return this.p_Name;
		}
		public String get_p_type()
		{
			return this.p_type;
		}

		public void set_p_id(String s)
		{
			this.p_id = s;
		}

		public void set_p_Name(String s)
		{
			this.p_Name = s;
		}

		public void set_p_type(String s)
		{
			this.p_type = s;
		}
}


