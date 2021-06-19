package com.junianto.apps.test1;

public class TanggaApps {

	private static String getBlock(int b, int n) {
		String blok="#";
		String s="";
		String ret="";
		for(int x=0;x<b+1;x++) {
			ret=ret+blok;
		}
		
		for(int x=0;x<(n-b);x++) {
			s=s+" ";
		}
	
		return s+ret;
	}
	
	public static void main(String[] args) {
		int n = 6;
		for(int x=0;x<n;x++) {
			System.out.println(getBlock(x,n));
		}
	}

}
