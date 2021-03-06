/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my PhD work.
 *  haowu@cs.nuim.ie
 *  JAN-2013 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
package atongmu.atg;

public final class StrUnv{
	public static final char[] UNV = {
		'a','b','c','d','e','f','g','h',
		'i','j','k','l','m','n','o','p',
		'q','r','s','t','u','v','w','x',
		'y','z','A','B','C','D','E','F',
		'G','H','I','J','K','L','M','N',
		'O','P','Q','R','S','T','U','V',
		'W','X','Y','Z','0','1','2','3',
		'4','5','6','7','8','9','0','\0'
	};

	public static int TCHAR=StrUnv.find('\0');
	public static int size(){return UNV.length;}
	public static String get(int i){return Character.toString(UNV[i]);}
	public static int find (char c){
		for (int i=0;i<UNV.length;i++)
			if (UNV[i]==c) return i;

		return -1;
	}
}
