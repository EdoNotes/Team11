/************************************************************************** 
 * Copyright (©) Zerli System 2017-2018 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Edo Notes <Edoono24@gmail.com>
 * 			  Tomer Arzuan <Tomerarzu@gmail.com>
 * 			  Matan Sabag <19matan@gmail.com>
 * 			  Ido Kalir <idotehila@gmail.com>
 * 			  Elinor Faddoul<elinor.faddoul@gmail.com
 **************************************************************************/
package Entities;

/**
 * @author IdoKalir
 *This Class presents Product Details That Stored In Our System
 */
public class Product 
{

	/***************************************************************************/
	/**					
	 * 							<Instance Variables>
	 * p_id-Product Id
	 * p_Name-Product Name
	 * p_type-Product type
	 */
		public String p_id;
		public String p_Name;
		public String p_type;
	/***************************************************************************/
	
		
	/**************************************************************************/
	/**							<Methods>			
	 */
		/**
		 *Empty Product Constructor
		 */
		public Product()
		{
			p_id = "";
			p_Name = "";
			p_type = "";
		}
		/**
		 * Constructor
		 * @param id- Product ID
		 * @param Name-Product Name
		 * @param type- Product Type
		 */
		public Product(String id,String Name ,String type)
		{
			p_id = id;
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
	/***************************************************************************/
}


